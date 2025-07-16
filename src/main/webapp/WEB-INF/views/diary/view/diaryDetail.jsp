<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>일기 상세</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    body {
      background: #f6f8fa;
      font-family: 'Segoe UI', Arial, sans-serif;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 500px;
      margin: 40px auto;
      background: #fff;
      border-radius: 16px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.08);
      padding: 32px 24px 24px 24px;
      box-sizing: border-box;
    }
    .diary-detail {
      display: flex;
      flex-direction: column;
      gap: 12px;
    }
    .diary-emoji {
      font-size: 2.5rem;
      text-align: center;
      margin-bottom: 8px;
    }
    .diary-title {
      font-size: 1.3rem;
      font-weight: 700;
      color: #3730a3;
      text-align: center;
      margin-bottom: 4px;
    }
    .diary-date {
      text-align: center;
      color: #64748b;
      font-size: 1rem;
      margin-bottom: 8px;
    }
    .diary-content {
      font-size: 1.1rem;
      color: #22223b;
      background: #f9fafb;
      border-radius: 8px;
      padding: 18px 12px;
      min-height: 80px;
      margin-bottom: 8px;
      word-break: break-all;
    }
    .diary-emotion {
      text-align: right;
      color: #6366f1;
      font-size: 1rem;
      margin-bottom: 8px;
    }
    .back-btn {
      display: block;
      width: 100%;
      background: linear-gradient(90deg, #7c3aed 60%, #6366f1 100%);
      color: #fff;
      border: none;
      border-radius: 7px;
      padding: 13px 0;
      font-size: 1.1rem;
      font-weight: 600;
      cursor: pointer;
      margin-top: 10px;
      box-shadow: 0 2px 8px rgba(124,58,237,0.08);
      transition: background 0.2s;
      text-align: center;
      text-decoration: none;
    }
    .back-btn:hover {
      background: linear-gradient(90deg, #6366f1 60%, #7c3aed 100%);
    }
    .message, .error-message {
      text-align: center;
      margin: 18px 0 0 0;
      font-size: 1.05rem;
      min-height: 24px;
    }
    .error-message {
      color: #e11d48;
    }
    @media (max-width: 600px) {
      .container { max-width: 98vw; padding: 10px 2vw; }
      .diary-content { padding: 12px 6px; }
      .diary-title { font-size: 1.1rem; }
    }
  </style>
</head>
<body>
  <div class="container">
    <div id="detailMessage" class="message">일기 정보를 불러오는 중...</div>
    <div id="diaryDetail" class="diary-detail" style="display:none;"></div>
    <button class="back-btn" onclick="window.history.back()">목록으로</button>
  </div>
  <script>
    // diaryId를 쿼리스트링 또는 /diary/detail/{id}에서 추출
    function getDiaryIdFromUrl() {
      // 1. /diary/detail/{id} 패턴
      var match = window.location.pathname.match(/\/diary\/detail\/(\d+)/);
      if (match) return match[1];
      // 2. ?id=123 패턴
      var params = new URLSearchParams(window.location.search);
      if (params.has('id')) return params.get('id');
      return null;
    }

    $(document).ready(function () {
      var diaryId = getDiaryIdFromUrl();
      if (!diaryId) {
        $('#detailMessage').attr('class','error-message').text('잘못된 접근입니다.');
        return;
      }
      loadDiaryDetail(diaryId);
    });

    function loadDiaryDetail(diaryId) {
      $.ajax({
        url: '/api/diaries/' + diaryId,
        method: 'GET',
        success: function (response) {
          renderDiaryDetail(response.data);
        },
        error: function (xhr, status, error) {
          $('#diaryDetail').hide();
          $('#detailMessage').attr('class','error-message').html(
            '일기 정보를 불러올 수 없습니다.<br>오류: ' + xhr.status + ' - ' + error
          );
        }
      });
    }

    function renderDiaryDetail(diary) {
      if (!diary) {
        $('#diaryDetail').hide();
        $('#detailMessage').attr('class','error-message').text('일기 정보가 없습니다.');
        return;
      }
      var html = '';
      html += '<div class="diary-emoji">' + (diary.emoji || '') + '</div>';
      html += '<div class="diary-title">' + (diary.title ? diary.title : '제목 없음') + '</div>';
      html += '<div class="diary-date">' + (diary.createdAt ? diary.createdAt.substring(0, 10) : (diary.diaryDate || '날짜 없음')) + '</div>';
      html += '<div class="diary-content">' + (diary.content ? diary.content : '내용 없음') + '</div>';
      html += '<div class="diary-emotion">감정: ' + (diary.emotionName || '없음') + '</div>';
      $('#diaryDetail').html(html).show();
      $('#detailMessage').attr('class','message').text('');
    }
  </script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>일기 등록</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    html {
      box-sizing: border-box;
    }
    *, *:before, *:after {
      box-sizing: inherit;
    }
    body {
      background: #f6f8fa;
      font-family: 'Segoe UI', Arial, sans-serif;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 420px;
      margin: 40px auto;
      background: #fff;
      border-radius: 16px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.08);
      padding: 32px 28px 24px 28px;
      box-sizing: border-box;
    }
    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 28px;
      font-weight: 700;
      letter-spacing: 1px;
      font-size: 2rem;
    }
    .form-group {
      margin-bottom: 22px;
      position: relative;
    }
    label {
      display: block;
      margin-bottom: 7px;
      color: #555;
      font-weight: 500;
      font-size: 1rem;
    }
    select, input[type="date"], textarea {
      width: 100%;
      padding: 10px 12px;
      border: 1.5px solid #d1d5db;
      border-radius: 7px;
      font-size: 1rem;
      background: #f9fafb;
      transition: border 0.2s;
      outline: none;
      min-height: 44px;
      margin: 0;
      box-sizing: border-box;
      display: block;
    }
    select:focus, input:focus, textarea:focus {
      border: 1.5px solid #7c3aed;
      background: #fff;
    }
    textarea {
      resize: vertical;
      min-height: 80px;
      font-family: inherit;
    }
    .emotion-row {
      display: flex;
      align-items: center;
      gap: 16px;
    }
    .emotion-row select {
      flex: 1 1 0;
      min-width: 0;
    }
    .emotion-preview {
      font-size: 2.2rem;
      width: 48px;
      text-align: center;
      transition: transform 0.2s;
      margin-top: 0;
      flex-shrink: 0;
    }
    .checkbox-group {
      display: flex;
      align-items: center;
      gap: 10px;
      margin: 0;
      min-height: 44px;
    }
    .checkbox-group input[type="checkbox"] {
      width: 20px;
      height: 20px;
      margin: 0;
      accent-color: #7c3aed;
      flex-shrink: 0;
    }
    .checkbox-group label {
      display: inline-block;
      margin: 0;
      font-weight: 500;
      color: #555;
      font-size: 1rem;
      line-height: 1;
      white-space: nowrap;
    }
    button {
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
    }
    button:hover {
      background: linear-gradient(90deg, #6366f1 60%, #7c3aed 100%);
    }
    .message {
      margin-top: 18px;
      text-align: center;
      font-size: 15px;
      min-height: 24px;
    }
    @media (max-width: 600px) {
      .container {
        padding: 12px 2vw;
        max-width: 98vw;
      }
      h1 {
        font-size: 1.2rem;
      }
      .emotion-row {
        gap: 8px;
      }
      .emotion-preview {
        font-size: 1.5rem;
        width: 32px;
      }
      select, input[type="date"], textarea, button {
        font-size: 0.97rem;
        min-height: 38px;
      }
      .checkbox-group {
        min-height: 38px;
        gap: 7px;
      }
    }
  </style>
</head>
<body>
  <div class="container">
    <h1>일기 등록</h1>
    <form id="diary-form" autocomplete="off">
      <div class="form-group">
        <label for="emotionId">감정</label>
        <div class="emotion-row">
          <select id="emotionId" name="emotionId" required>
            <option value="">감정을 선택하세요</option>
            <option value="1" data-emoji="😊">😊 기쁨</option>
            <option value="2" data-emoji="😢">😢 슬픔</option>
            <option value="3" data-emoji="😡">😡 분노</option>
            <option value="4" data-emoji="😲">😲 놀람</option>
            <option value="5" data-emoji="😨">😨 불안</option>
            <!-- 필요시 감정 추가 -->
          </select>
          <span class="emotion-preview" id="emotion-preview"></span>
        </div>
      </div>
      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="5" required placeholder="오늘의 감정을 기록해보세요"></textarea>
      </div>
      <div class="form-group">
        <label for="diaryDate">날짜</label>
        <input type="date" id="diaryDate" name="diaryDate" required>
      </div>
      <div class="form-group checkbox-group">
        <input type="checkbox" id="isPublic" name="isPublic" checked>
        <label for="isPublic" style="margin:0;">공개 여부</label>
      </div>
      <button type="submit">등록</button>
    </form>
    <div class="message" id="message"></div>
  </div>
  <script>
    // 감정 선택 시 이모지 미리보기
    $('#emotionId').on('change', function () {
      const emoji = $(this).find('option:selected').data('emoji') || '';
      $('#emotion-preview').text(emoji);
    });

    // 오늘 날짜 기본값 + 쿼리 파라미터 date 우선 적용 + 수정 모드 지원
    $(function () {
      function getQueryParam(name) {
        const url = new URL(window.location.href);
        return url.searchParams.get(name);
      }
      const paramDate = getQueryParam('date');
      const paramId = getQueryParam('id');
      const today = new Date().toISOString().split('T')[0];

      if (paramId) {
        // 수정 모드: 기존 데이터 불러오기
        $.ajax({
          url: '/api/diaries/' + paramId,
          method: 'GET',
          success: function(response) {
            const d = response.data;
            $('#emotionId').val(d.emotionId);
            $('#content').val(d.content);
            $('#diaryDate').val(d.diaryDate);
            $('#isPublic').prop('checked', d.isPublic);
            $('#emotion-preview').text(d.emoji || '');
          }
        });
      } else {
        // 등록 모드: 날짜 세팅
        $('#diaryDate').val(paramDate || today);
      }
    });

    // 폼 제출
    $('#diary-form').on('submit', function (e) {
      e.preventDefault();

      const userId = 1; // 실제로는 로그인 정보에서 받아와야 함
      const emotionId = $('#emotionId').val();
      const content = $('#content').val();
      const diaryDate = $('#diaryDate').val();
      const isPublic = $('#isPublic').is(':checked');
      const paramId = (new URL(window.location.href)).searchParams.get('id');

      if (!emotionId) {
        $('#message').html('<span style="color:#e11d48;">감정을 선택해주세요.</span>');
        return;
      }

      const method = paramId ? 'POST' : 'POST';
      const url = paramId ? ('/api/diaries/update/' + paramId) : '/api/diaries';
      const data = {
        userId: userId,
        emotionId: emotionId,
        content: content,
        diaryDate: diaryDate,
        isPublic: isPublic
      };

      $.ajax({
        url: url,
        method: method,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
          $('#message').html('<span style="color:#059669;">' + response.message + '</span>');
          setTimeout(function() {
            window.location.href = '/diary/calendar';
          }, 1000);
        },
        error: function (xhr) {
          let msg = '등록에 실패했습니다.';
          if (xhr.responseJSON && xhr.responseJSON.message) {
            msg = xhr.responseJSON.message;
          }
          $('#message').html('<span style="color:#e11d48;">' + msg + '</span>');
        }
      });
    });
  </script>
</body>
</html>
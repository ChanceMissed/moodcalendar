<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>달력으로 일기 쓰기</title>
  <link href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css' rel='stylesheet' />
  <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    body { background: #f6f8fa; font-family: 'Segoe UI', Arial, sans-serif; margin: 0; }
    .container { max-width: 700px; margin: 40px auto; background: #fff; border-radius: 16px; box-shadow: 0 4px 24px rgba(0,0,0,0.08); padding: 32px 24px 24px 24px; }
    #calendar { background: #fff; border-radius: 12px; box-shadow: 0 1px 4px rgba(124,58,237,0.06); padding: 10px; }
    .fc-toolbar-title { font-size: 1.5rem; color: #3730a3; }
    .diary-preview { margin-top: 24px; background: #f9fafb; border-radius: 10px; padding: 18px 14px; min-height: 80px; box-shadow: 0 1px 4px rgba(124,58,237,0.06); }
    .diary-preview .date { color: #6366f1; font-weight: 600; }
    .diary-preview .content { margin-top: 10px; font-size: 1.1rem; color: #22223b; }
    .diary-preview .emotion { margin-top: 6px; color: #6366f1; }
    .write-btn { margin-top: 16px; background: linear-gradient(90deg, #7c3aed 60%, #6366f1 100%); color: #fff; border: none; border-radius: 7px; padding: 10px 0; font-size: 1.1rem; font-weight: 600; width: 100%; cursor: pointer; }
    .write-btn:hover { background: linear-gradient(90deg, #6366f1 60%, #7c3aed 100%); }
    @media (max-width: 700px) { .container { max-width: 98vw; padding: 10px 2vw; } }
  </style>
</head>
<body>
  <div class="container">
    <h2 style="text-align:center; color:#333; margin-bottom:18px;">달력으로 일기 쓰기</h2>
    <div id='calendar'></div>
    <div id="diaryPreview" class="diary-preview" style="display:none;"></div>
    <button id="writeBtn" class="write-btn" style="display:none;">이 날짜에 일기 쓰기</button>
  </div>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      var calendarEl = document.getElementById('calendar');
      var userId = $('body').data('user-id') || 1;
      var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        headerToolbar: { left: 'prev,next today', center: 'title', right: '' },
        selectable: true,
        dateClick: function(info) {
          loadDiaryByDate(info.dateStr);
        },
        events: function(fetchInfo, successCallback, failureCallback) {
          $.ajax({
            url: '/api/diaries/user/' + userId,
            method: 'GET',
            success: function(response) {
              var events = [];
              (response.data || []).forEach(function(diary) {
                events.push({
                  title: diary.emoji || '📝',
                  start: diary.diaryDate,
                  allDay: true,
                  color: '#a5b4fc'
                });
              });
              successCallback(events);
            },
            error: failureCallback
          });
        }
      });
      calendar.render();

      window.loadDiaryByDate = function(dateStr) {
        $('#diaryPreview').hide();
        $('#writeBtn').hide();
        $.ajax({
          url: '/api/diaries/search?userId=' + userId + '&fromDate=' + dateStr + '&toDate=' + dateStr,
          method: 'GET',
          success: function(response) {
            var diaries = response.data || [];
            if (diaries.length > 0) {
              var html = '<div class="date">' + dateStr + '</div>';
              diaries.forEach(function(diary) {
                html += '<div class="diary-card" style="margin:10px 0; padding:10px; border-radius:8px; background:#f9fafb; box-shadow:0 1px 4px #a5b4fc33;">'
                     +   '<div><span style="font-size:1.5rem;">' + (diary.emoji || '') + '</span> '
                     +   '<b>' + (diary.title || '제목 없음') + '</b></div>'
                     +   '<div style="color:#6366f1;">감정: ' + (diary.emotionName || '없음') + '</div>'
                     +   '<div style="margin:4px 0;">' + (diary.content ? diary.content.substring(0, 30) + '...' : '내용 없음') + '</div>'
                     +   '<button onclick="window.location.href=\'/diary/detail/' + diary.id + '\'" style="margin-right:8px;">상세보기</button>'
                     +   '<button onclick="window.location.href=\'/diary/write?id=' + diary.id + '\'" style="margin-right:8px;">수정</button>'
                     +   '<button onclick="deleteDiary(' + diary.id + ', \'' + dateStr + '\')" style="background:#e11d48;color:#fff;border:none;padding:6px 12px;border-radius:5px;cursor:pointer;">삭제</button>'
                     + '</div>';
              });
              $('#diaryPreview').html(html).show();
              $('#writeBtn').text('이 날짜에 새 일기 쓰기').show().off('click').on('click', function() {
                window.location.href = '/diary/write?date=' + dateStr;
              });
            } else {
              $('#diaryPreview').html('<div class="date">' + dateStr + '</div><div class="content">아직 일기가 없습니다.</div>').show();
              $('#writeBtn').text('이 날짜에 일기 쓰기').show().off('click').on('click', function() {
                window.location.href = '/diary/write?date=' + dateStr;
              });
            }
          }
        });
      };

      window.deleteDiary = function(id, dateStr) {
        if (!confirm('정말 삭제하시겠습니까?')) return;
        $.ajax({
          url: '/api/diaries/delete/' + id,
          method: 'POST',
          success: function(response) {
            alert('삭제되었습니다.');
            loadDiaryByDate(dateStr); // 해당 날짜 일기 목록 새로고침
            calendar.refetchEvents(); // 달력 이벤트 새로고침
          },
          error: function(xhr) {
            alert('삭제에 실패했습니다.');
          }
        });
      };
    });
  </script>
</body>
</html>
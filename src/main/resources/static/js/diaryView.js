$(document).ready(function () {
  const userId = $('body').data('user-id'); // userId를 body에서 가져옴

  if (!userId) {
    alert("로그인 후 이용해주세요.");
    location.href = "/users/login";
    return;
  }

  console.log("로그인된 사용자 ID:", userId);
  $('#diaryList').html('<p>일기 목록을 불러오는 중...</p>');
  loadUserDiaries(userId);
});

function loadUserDiaries(userId) {
  console.log("API 요청 시작: /api/diaries/user/" + userId);

  $.ajax({
    url: `/api/diaries/user/${userId}`,
    method: 'GET',
    success: function (response) {
      console.log("일기 목록 조회 성공:", response);
      displayDiaries(response.data);
    },
    error: function (xhr, status, error) {
      console.error('일기 목록 조회 실패:', error);
      $('#diaryList').html(`
        <p style="color:red">일기 목록을 불러올 수 없습니다.</p>
        <p>오류: ${xhr.status} - ${error}</p>
      `);
    }
  });
}

function displayDiaries(diaries) {
  const diaryList = $('#diaryList');
  diaryList.empty();

  if (!diaries || diaries.length === 0) {
    diaryList.html('<p>작성된 일기가 없습니다.</p>');
    return;
  }

  diaries.forEach((diary, index) => {
    diaryList.append(`
      <div class="diary-item" style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
        <h3>${diary.title ? diary.title : '제목 없음'}</h3>
        <p>${diary.content ? diary.content : '내용 없음'}</p>
        <small>작성일: ${diary.createdAt ? diary.createdAt.substring(0, 10) : '날짜 없음'}</small>
        <div>감정: ${diary.emotionName || '없음'} ${diary.emoji || ''}</div>
      </div>
    `);
  });
}

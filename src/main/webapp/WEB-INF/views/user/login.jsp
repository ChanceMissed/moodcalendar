<!-- login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
      <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
      <script>
        $(document).ready(function() {
          alert("로그인 페이지가 잘 불러와졌어!");

          // 이벤트 핸들러를 $(document).ready() 안으로 이동
          $('#loginForm').on('submit', function(e) {
            e.preventDefault();

            const data = {
              email: $('input[name="email"]').val(),
              password: $('input[name="password"]').val()
            };

            $.ajax({
              url: '/api/auth/login',
              method: 'POST',
              contentType: 'application/json',
              data: JSON.stringify(data),
              success: function(response) {
                  $('#result').html(`<p>${response.message || '로그인 성공!'}</p>`);
                  location.href = '/diary/view'; //로그인 후 이동할 페이지
              },
              error: function(xhr) {
                  const errorMsg = xhr.responseJSON?.message || '로그인 실패!';
                  $('#result').html(`<p style="color:red">${errorMsg}</p>`);
              }
            });
          });
           const userId = ${sessionScope.userId != null ? sessionScope.userId : 0};
           console.log("로그인한 userId:", userId);
        });
      </script>
</head>
<body>
  <h2>로그인</h2>

  <form id="loginForm">
    <div>
      <label for="email">이메일: </label>
      <input type="email" id="email" name="email" required> <br>
      <label for="password">비밀번호: </label>
      <input type="password" id=password name="password" required>

      <button type="submit">로그인</button>
    <div>
  </form>

  <div id="result"></div>
</body>
</html>

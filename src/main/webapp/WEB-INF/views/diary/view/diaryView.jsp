<!-- diaryView.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>나의 일기 목록</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    body {
      background: #f6f8fa;
      font-family: 'Segoe UI', Arial, sans-serif;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 600px;
      margin: 40px auto;
      background: #fff;
      border-radius: 16px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.08);
      padding: 32px 24px 24px 24px;
      box-sizing: border-box;
    }
    h2 {
      text-align: center;
      color: #333;
      margin-bottom: 24px;
      font-weight: 700;
      letter-spacing: 1px;
    }
    .welcome {
      text-align: right;
      color: #6366f1;
      font-size: 1rem;
      margin-bottom: 10px;
    }
    .diary-list {
      margin-top: 10px;
    }
    .diary-item {
      background: #f9fafb;
      border-radius: 10px;
      box-shadow: 0 1px 4px rgba(124,58,237,0.06);
      padding: 18px 16px 14px 16px;
      margin-bottom: 18px;
      border: 1.5px solid #e5e7eb;
      transition: box-shadow 0.2s, border 0.2s;
      display: flex;
      flex-direction: column;
      gap: 6px;
    }
    .diary-item:hover {
      box-shadow: 0 4px 16px rgba(124,58,237,0.10);
      border: 1.5px solid #a5b4fc;
    }
    .diary-header {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 2px;
    }
    .diary-emoji {
      font-size: 2rem;
      margin-right: 2px;
      flex-shrink: 0;
    }
    .diary-title {
      font-size: 1.1rem;
      font-weight: 600;
      color: #3730a3;
      margin: 0;
      flex: 1 1 0;
      word-break: break-all;
    }
    .diary-date {
      font-size: 0.97rem;
      color: #64748b;
      margin-left: auto;
      font-weight: 400;
    }
    .diary-content {
      margin: 6px 0 0 0;
      color: #22223b;
      font-size: 1rem;
      word-break: break-all;
    }
    .diary-emotion {
      font-size: 0.97rem;
      color: #6366f1;
      margin-top: 2px;
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
    @media (max-width: 700px) {
      .container { max-width: 98vw; padding: 10px 2vw; }
      .diary-item { padding: 12px 7px; }
      h2 { font-size: 1.1rem; }
    }
  </style>
</head>
<body data-user-id="${sessionScope.userId}">
  <div class="container">
    <h2>나의 일기 목록</h2>
    <c:if test="${sessionScope.userId != null}">
      <div class="welcome">환영합니다! 사용자 ID: ${sessionScope.userId}</div>
    </c:if>
    <div id="diaryList" class="diary-list">
      <!-- 일기 목록이 여기에 표시됩니다 -->
    </div>
    <div id="listMessage" class="message"></div>
  </div>
  <script src="/js/diaryView.js"></script>
</body>
</html>
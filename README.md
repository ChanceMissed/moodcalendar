# 📘 MoodCalendar - 감정 기록 캘린더

description:~~~

## 📌 주요기능

### Phase 1 - 개인 감정 일기장
- [X] 회원 가입 / 로그인
- [X] 캘린더 UI 에서 날짜 클릭 -> 일기 작성 + 감정 추가
- [x] 감정 선택 (😊 😢 😡 😐 😴 등)
- [x] 일기 작성 / 수정 / 삭제 / 조회
- [x] 나만의 일기 목록 보기

### Phase 2 - SNS 기능 (예정)
- [ ] 일기 공개 / 비공개 설정
- [ ] 공개 일기 피드
- [ ] 감정 리액션 (공감, 위로, 응원)
- [ ] 댓글 기능
- [ ] 팔로우 / 팔로워

### Phase 3 - 확장 기능 (예정)
- [ ] 일기 공유 (카카오톡, URL 등)
- [ ] 해시태그 기능
- [ ] 감정 통계 대시보드
- [ ] 사진 첨부
- [ ] 알림 기능 (FCM or 카카오 연동)

### Phase 4 - AI 분석 (예정)
- [ ] 감정 분석 자동화
- [ ] 감정 키워드 추출
- [ ] 감정 흐름 시각화
- [ ] 유사 일기 추천

---

## 기술 스택
| 구분           | 기술                                          |
|--------------|---------------------------------------------|
| **Backend**  | Spring Book 3.x, MyBatis, Spring Validation |
| **Frontend** | jQuery, Ajax, FullCalender.js, Bootstrap5   |
| **Database** | H2(개발), MySQL (운영 예정)                       |
| **기타**       | Spring DevTools, Lombok                     |
|              |                                             

---

## 📂 프로젝트 구조

```plaintext
src/main/java/com/moodcalendar
├── controller         # REST API
├── service            # 비즈니스 로직
├── mapper             # MyBatis 매퍼 인터페이스
├── domain             # 도메인 모델
├── dto                # Request/Response DTO
├── config             # 설정 파일
└── MoodCalendarApplication.java

src/main/resources
├── mapper/            # MyBatis XML 쿼리
├── static/            # 정적 리소스
├── templates/         # HTML 템플릿 (JSP or Thymeleaf)
└── application.yml
# 📘 MoodCalendar - 감정 기록 캘린더

description: 캘린더에 그날 감정을 기록하면서 일기를
쓸거야 (음악(spotify), SNS, 감정좋아요, 공감, 공유 등등)

---

## ✅ TODO (FIX List)

- [x] Diary 조회 시 nickname 반환되도록 Mapper 수정
- [x] DiaryResponseDto에 nickname 추가
- [x] DB Diary 테이블에 title 컬럼 추가
- [x] Diary 등록 시 title 값도 입력되도록 처리
- [x] Diary 검색 등 등 title 값도 입력되도록 처리
- [ ] Emoji API 사용으로 변경

### 📌 참고 사항

- Emotion 정보는 이미 연동 완료
- Diary 등록/조회에 title 필드 적용 필요

### 🧪 예상 테스트 시나리오

- 신규 등록 시 title 필드가 빠졌을 경우 에러 발생해야 함
- 조회 시 nickname 및 title 제대로 나오는지 확인

### 🕐 예상 작업 시간

-

---

## 📌 주요기능

### Phase 1 - 개인 감정 일기장

- [ ] 회원 가입 / 로그인 - Security 전환
- [x] 캘린더 UI 에서 날짜 클릭 -> 일기 작성 + 감정 추가
- [x] 감정 선택 (😊 😢 😡 😐 😴 등) - api 로 받아서 재 구현할거임
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
- [ ] 음악 연동 (Spotify)
- [ ] 알림 기능 (FCM or 카카오 연동)

### Phase 4 - AI 분석 (예정)

- [ ] 감정 분석 자동화
- [ ] 감정 키워드 추출
- [ ] 감정 흐름 시각화
- [ ] 유사 일기 추천

---

## 기술 스택

| 구분         | 기술                                        |
| ------------ | ------------------------------------------- |
| **Backend**  | Spring Book 3.x, MyBatis, Spring Validation |
| **Frontend** | jQuery, Ajax, FullCalender.js, Bootstrap5   |
| **Database** | H2(개발), MySQL (운영 예정)                 |
| **기타**     | Spring DevTools, Lombok                     |
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

```

```
+-----------+         +-----------+        +-------------+
|   User    |1       N|   Diary   |N      1|  Emotion    |
+-----------+---------+-----------+--------+-------------+
| id (PK)   |         | id (PK)   |        | id (PK)     |
| email     |         | user_id(FK)|       | user_id(FK, nullable) |
| password  |         | emotion_id(FK)     | emoji       |
| nickname  |         | content   |        | name        |
| created_at|         | diary_date|        | color       |
+-----------+         | is_public |        | created_at  |
| created_at|        +-------------+
| updated_at|
+-----------+

+----------------+       +--------------+        +--------------+
|   Reaction     |N     1|    Diary     |1      N|    User      |
+----------------+-------+--------------+--------+--------------+
| id (PK)        |       | id (PK)      |        | id (PK)      |
| diary_id (FK)  |       | ...          |        | ...          |
| user_id (FK)   |       +--------------+        +--------------+
| type           |  (좋아요, 공감 등)
| created_at     |
+----------------+

+--------------+        +--------------+        +--------------+
|   Comment    |N      1|    Diary     |1      N|    User      |
+--------------+--------+--------------+--------+--------------+
| id (PK)      |        | id (PK)      |        | id (PK)      |
| diary_id(FK) |        | ...          |        | ...          |
| user_id(FK)  |        +--------------+        +--------------+
| content      |
| created_at   |
+--------------+

+--------------+        +--------------+
|   Follow     |        |    User      |
+--------------+        +--------------+
| follower_id  |--------| id (PK)      |
| followee_id  |--------| id (PK)      |
+--------------+        +--------------+
```

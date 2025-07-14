-- 👤 사용자(User) 2명
INSERT INTO users (email, password, nickname, created_at, updated_at)
VALUES ('test1@example.com', '1234', '기록러1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (email, password, nickname, created_at, updated_at)
VALUES ('test2@example.com', '5678', '감정러2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 😊 감정 목록 (기본 감정)

-- 😊 Emotion (기본 감정)
INSERT INTO emotion (user_id, emoji, name, color, created_at)
VALUES (NULL, '😊', '기쁨', '#FFD700', CURRENT_TIMESTAMP);

INSERT INTO emotion (user_id, emoji, name, color, created_at)
VALUES (NULL, '😢', '슬픔', '#87CEFA', CURRENT_TIMESTAMP);

INSERT INTO emotion (user_id, emoji, name, color, created_at)
VALUES (NULL, '😡', '분노', '#FF6347', CURRENT_TIMESTAMP);

INSERT INTO emotion (user_id, emoji, name, color, created_at)
VALUES (NULL, '😐', '무표정', '#B0BEC5', CURRENT_TIMESTAMP);

INSERT INTO emotion (user_id, emoji, name, color, created_at)
VALUES (NULL, '😴', '피곤', '#D3D3D3', CURRENT_TIMESTAMP);

INSERT INTO emotion (user_id, emoji, name, color, created_at)
VALUES (NULL, '🤔', '고민', '#999999', CURRENT_TIMESTAMP);

-- 📓 일기
INSERT INTO diary (user_id, emotion_id, content, diary_date, is_public, created_at, updated_at)
VALUES (1, 1, '오늘 날씨가 좋아서 기분이 좋았어요!', '2025-07-04', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO diary (user_id, emotion_id, content, diary_date, is_public, created_at, updated_at)
VALUES (2, 2, '힘든 하루였지만 그래도 기록하자!', '2025-07-03', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Public 일기
INSERT INTO diary (user_id, emotion_id, content, diary_date, is_public, created_at, updated_at)
VALUES (2, 2, '기쁜 하루였지만 그래도 기록하자!', '2025-07-07', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- ✅ 외래키 다시 켜기
SET REFERENTIAL_INTEGRITY TRUE;
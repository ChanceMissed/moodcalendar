CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255),
  password VARCHAR(255),
  nickname VARCHAR(255),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE emotion (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  emoji VARCHAR(10),
  name VARCHAR(50),
  color VARCHAR(7),
  created_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE diary (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  emotion_id BIGINT,
  content CLOB,
  diary_date DATE,
  is_public BOOLEAN,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (emotion_id) REFERENCES emotion(id)
);

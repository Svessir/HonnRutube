CREATE TABLE Account
(
  userId int Identity (1, 1) PRIMARY KEY NOT NULL,
  username VARCHAR(32),
  password VARCHAR(32),
  CONSTRAINT account_unique UNIQUE (username)
)
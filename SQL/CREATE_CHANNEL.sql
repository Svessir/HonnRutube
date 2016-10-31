CREATE TABLE Channel
(
  channelId INT Identity (1, 1) PRIMARY KEY NOT NULL,
  channelName VARCHAR(32) UNIQUE,
)
CREATE TABLE Video
(
  videoId INT Identity (1, 1) PRIMARY KEY NOT NULL,
  title VARCHAR(100),
  description VARCHAR(MAX),
  url VARCHAR(MAX),
  viewCount INT,
  numberOfLikes INT
)
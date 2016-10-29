CREATE TABLE Favorites
(
  userId int IDENTITY NOT NULL,
  videoId int IDENTITY NOT NULL,
  CONSTRAINT pk_favorites PRIMARY KEY (userId, videoId)
)
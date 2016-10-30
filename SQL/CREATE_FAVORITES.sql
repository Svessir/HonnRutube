CREATE TABLE Favorites
(
  userId int NOT NULL,
  videoId int NOT NULL,
  PRIMARY KEY (userId, videoId),
    CONSTRAINT fk_favorites FOREIGN KEY (userId) REFERENCES UserProfile(userId)
)
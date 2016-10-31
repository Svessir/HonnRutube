CREATE TABLE Friends
(
  userId int NOT NULL,
  friendId int NOT NULL,
  PRIMARY KEY (userId, friendId),
  CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES UserProfile(userId) ON DELETE CASCADE,
  CONSTRAINT fk_friend FOREIGN KEY (friendId) REFERENCES UserProfile(userId) ON DELETE CASCADE
)
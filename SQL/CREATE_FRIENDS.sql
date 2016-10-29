CREATE TABLE Friends
(
  userId int IDENTITY NOT NULL,
  friendId int IDENTITY NOT NULL,
  CONSTRAINT pk_friends PRIMARY KEY (userId, friendId)
)
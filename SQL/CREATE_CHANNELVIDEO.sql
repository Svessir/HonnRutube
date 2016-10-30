CREATE TABLE ChannelVideo
(
  channelId INT FOREIGN KEY REFERENCES Channel(channelId) ON DELETE CASCADE ,
  videoId INT FOREIGN KEY REFERENCES Video(videoId) ON DELETE CASCADE,
  CONSTRAINT pk_ChannelVideo PRIMARY KEY (channelId,videoId),
)
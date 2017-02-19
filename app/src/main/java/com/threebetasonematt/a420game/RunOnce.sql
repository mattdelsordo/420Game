DROP TABLE IF EXISTS User;

CREATE TABLE User (
    userID varchar(10) NOT NULL PRIMARY KEY,
    winCount int NOT NULL,
    highScore decimal(10000, 2) NOT NULL, -- ###.##
)

DROP TABLE IF EXISTS Game;

CREATE TABLE Game (
    gameID varchar(17) NOT NULL PRIMARY KEY,
    userID varchar(10) FOREIGN KEY REFERENCES User(userID),
    startingAltitude decimal(1000000, 2) NOT NULL,
    endingAltitude decimal(1000000, 2) NOT NULL,
    score decimal(10000, 2) NOT NULL,
    winStatus binary(1) NOT NULL,
    PRIMARY KEY ('gameID')
)

DROP TABLE IF EXISTS 'Leaderboard'

CREATE TABLE 'Leaderboard' (
    userID varchar(10) FOREIGN KEY REFERENCES User(userID)
    highScore decimal(10000, 2) FOREIGN KEY REFERENCES User(highScore)
)


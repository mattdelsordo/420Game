DROP TABLE IF EXISTS User;

CREATE TABLE User (
    userID varchar(10) NOT NULL PRIMARY KEY,
    winCount int NOT NULL,
    highScore decimal(6, 2) NOT NULL -- ###.##
);

DROP TABLE IF EXISTS Game;

CREATE TABLE Game (
    gameID varchar(17) NOT NULL PRIMARY KEY,
    userID varchar(10) REFERENCES User(userID),
    startingAltitude decimal(8, 2) NOT NULL,
    endingAltitude decimal(8, 2) NOT NULL,
    score decimal(6, 2) NOT NULL,
    winStatus binary(1) NOT NULL
);

DROP TABLE IF EXISTS Leader;

CREATE TABLE Leader (
    userID varchar(10) REFERENCES User(userID),
    highScore decimal(6, 2) REFERENCES User(highScore)
);


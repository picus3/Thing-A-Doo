DROP DATABASE ThingADoo;
    CREATE DATABASE ThingADoo;
USE ThingADoo;

CREATE TABLE Users(
	userID INT(11) primary key auto_increment,
    username VARCHAR(100) not null,
    pword VARCHAR(100) not null,
    fname VARCHAR(100) not null,
    lname VARCHAR(100) not null,
    photofile VARCHAR(100) ,
    serilizedfile VARCHAR(100) ,
    admin BOOL 




);


CREATE TABLE Groups(
	groupId INT(11) primary key auto_increment,
    groupname VARCHAR(100) not null,
    members VARCHAR(300) ,
    serilizedfile VARCHAR(60) 

);



CREATE TABLE Activity(
	activityID INT(11) primary key auto_increment,
    activityname VARCHAR(50) not null,
     location INT (6) not null,
    categories varchar(9) not null,
    keywordsfile TEXT not null,
    creator INT(11) REFERENCES Users (userID),
    serilizedfile VARCHAR(60) ,
    photofile VARCHAR(50) 
);


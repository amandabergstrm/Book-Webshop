CREATE DATABASE DB_Webbshop;

USE DB_Webbshop;

-- DROP DATABASE DB_Webbshop; DROP USER 'client'@'localhost';

CREATE TABLE T_User (
	authority		VARCHAR(100)	NOT NULL,
    name		VARCHAR(100)	NOT NULL,
    email	VARCHAR(30)		PRIMARY KEY, 
    password	VARCHAR(30)		NOT NULL
);

CREATE TABLE T_Book (
	isbn			VARCHAR(13)		PRIMARY KEY,
	title			VARCHAR(50)		NOT NULL,	
    genre			VARCHAR(50)		NOT NULL,
    author		VARCHAR(100)	NOT NULL,
	CHECK (LENGTH(isbn) = 10 OR LENGTH(isbn) = 13),
    -- CHECK (rating > 0 AND rating < 6)
    nrOfCopies INT NOT NULL
);

CREATE USER IF NOT EXISTS 'client'@'localhost' IDENTIFIED BY 'client';

GRANT ALL PRIVILEGES ON Webbshop.* TO 'client'@'localhost';

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies)  
VALUES("9781451690316", "Fahrenheit 451", "SciFi", "Betty", 3);

INSERT INTO T_User (authority, name, email, password) 
VALUES("Admin", "Betty", "poriazov@kth.se", "123");


SELECT *
FROM T_Book;

SELECT *
FROM T_User;

/*
SELECT T_Book.*, T_Author.*User
FROM T_AuthorAndBook
JOIN T_Author ON T_Author.authorId = T_AuthorAndBook.author
JOIN T_Book ON T_Book.bookId = T_AuthorAndBook.book
WHERE T_Book.rating LIKE 4; */
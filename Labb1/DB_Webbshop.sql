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
	itemId	INT				PRIMARY KEY AUTO_INCREMENT,
    isbn	VARCHAR(13)		NOT NULL,
	title	VARCHAR(50)		NOT NULL,	
    genre	VARCHAR(50)		NOT NULL,
    author	VARCHAR(100)	NOT NULL,
	CHECK (LENGTH(isbn) = 13),
    -- CHECK (rating > 0 AND rating < 6)
    nrOfCopies INT NOT NULL,
    price INT NOT NULL
);

CREATE USER IF NOT EXISTS 'client'@'localhost' IDENTIFIED BY 'client';

GRANT ALL PRIVILEGES ON DB_Webbshop.* TO 'client'@'localhost';

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781782276203", "Tender is the Flesh", "SciFi", "Agustina Bazterrica", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781906040093", "The Suicide Shop", "Fiction", "Jean Teulé", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780571258093", "Never Let Me Go", "Fiction", "Kazuo Ishiguro", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780062380623", "Coraline", "Fantasy", "Neil Gaiman", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780008435769", "The Maid", "Mystery", "Nita Prose", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781451690316", "Fahrenheit 451", "SciFi", "Ray Bradbury", 3, 95);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781785036354", "The Toymakers", "Historical", "Robert Dinsdale", 5, 120);

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
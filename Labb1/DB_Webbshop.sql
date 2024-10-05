CREATE DATABASE DB_Webbshop;

USE DB_Webbshop;

-- DROP DATABASE DB_Webbshop; DROP USER 'client'@'localhost';

CREATE TABLE T_User (
	authority		VARCHAR(100)	NOT NULL,
    name		VARCHAR(100)	NOT NULL,
    email	VARCHAR(30)		PRIMARY KEY, 
    password	VARCHAR(30)		NOT NULL
);

CREATE TABLE T_Category (
	genre	VARCHAR(50)	PRIMARY KEY NOT NULL
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
    price INT NOT NULL,
    CONSTRAINT T_Book_fk FOREIGN KEY (genre) REFERENCES T_Category(genre)
);

CREATE TABLE T_Order (
	orderNr	INT	 				PRIMARY KEY AUTO_INCREMENT,
    userEmail	VARCHAR(100)	NOT NULL,
    status	VARCHAR(30)			NOT NULL,
    CONSTRAINT T_Order_fk FOREIGN KEY (userEmail) REFERENCES T_User(email)
);

CREATE TABLE T_OrderItem (
	itemId	INT				PRIMARY KEY,
	nrOfItems INT 			NOT NULL,
    orderNr	INT	 			NOT NULL,
	CONSTRAINT T_OrderItem_fk FOREIGN KEY (orderNr) REFERENCES T_Order(orderNr),
    CONSTRAINT T_OrderItem_fk1 FOREIGN KEY (itemId) REFERENCES T_Book(itemId)
);

CREATE USER IF NOT EXISTS 'client'@'localhost' IDENTIFIED BY 'client';

GRANT ALL PRIVILEGES ON DB_Webbshop.* TO 'client'@'localhost';

INSERT INTO T_Category (genre)
VALUES ('Fantasy'),
       ('Science Fiction'),
       ('Romance'),
       ('Mystery'),
       ('Fiction'),
       ('Historical'),
       ('Thriller');

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)
VALUES("9781782276203", "Tender is the Flesh", "Science Fiction", "Agustina Bazterrica", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781906040093", "The Suicide Shop", "Fiction", "Jean Teulé", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780571258093", "Never Let Me Go", "Fiction", "Kazuo Ishiguro", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780062380623", "Coraline", "Fantasy", "Neil Gaiman", 19, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780008435769", "The Maid", "Mystery", "Nita Prose", 5, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781451690316", "Fahrenheit 451", "Science Fiction", "Ray Bradbury", 3, 95);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781785036354", "The Toymakers", "Historical", "Robert Dinsdale", 0, 120);

INSERT INTO T_User (authority, name, email, password) 
VALUES("Admin", "Betty", "poriazov@kth.se", "123");

INSERT INTO T_User (authority, name, email, password)
VALUES('User', 'Test User', 'testuser@example.com', 'password123');

INSERT INTO T_Order (userEmail, status)
VALUES ('testuser@example.com', 'Pending');

INSERT INTO T_Order (userEmail, status)
VALUES ('testuser@example.com', 'Pending');

INSERT INTO T_OrderItem (itemId, nrOfItems, orderNr)
VALUES ('1', '6','1');

INSERT INTO T_OrderItem (itemId, nrOfItems, orderNr)
VALUES ('2','2','1');

INSERT INTO T_OrderItem (itemId, nrOfItems, orderNr)
VALUES ('3', '10','2');

UPDATE T_Book
SET T_Book.nrOfCopies = 10, T_Book.price = 100 WHERE itemId = 10;

SELECT *
FROM T_Book;

SELECT *
FROM T_Order;

SELECT *
FROM T_OrderItem;

SELECT *
FROM T_User;

SELECT *
FROM T_Category;

CREATE VIEW OrderDetails AS
SELECT T_Order.orderNr, T_Order.user, T_Order.itemId, T_Book.title, T_Order.nrOfItems, T_Order.status
FROM T_Order
JOIN T_Book ON T_Order.itemId = T_Book.itemId;

SELECT * FROM OrderDetails;
SELECT T_OrderItem.orderNr, T_OrderItem.itemId, T_OrderItem.nrOfItems, T_Order.userEmail, T_Book.title
FROM T_OrderItem
JOIN T_Order ON T_OrderItem.orderNr = T_Order.orderNr
JOIN T_Book ON T_OrderItem.itemId = T_Book.itemId
WHERE T_OrderItem.orderNr = 1;

UPDATE T_Book 
SET T_Book.nrOfCopies = 10, T_Book.price = 100 WHERE itemId = 10;

UPDATE T_User 
SET T_User.authority = "Admin" WHERE email = "test6@hello.com";

/*
SELECT itemId
FROM T_Book
WHERE isbn = "9780062380623" AND title = "Coraline";

DELETE FROM T_Book 
WHERE itemId = 11;

SELECT T_Book.*, T_Author.*User
FROM T_AuthorAndBook
JOIN T_Author ON T_Author.authorId = T_AuthorAndBook.author
JOIN T_Book ON T_Book.bookId = T_AuthorAndBook.book
WHERE T_Book.rating LIKE 4; */
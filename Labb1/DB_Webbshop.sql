CREATE DATABASE DB_Webbshop;

USE DB_Webbshop;

-- DROP DATABASE DB_Webbshop;

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
    userEmail	VARCHAR(100),
    status	VARCHAR(30)			NOT NULL,
    CONSTRAINT T_Order_fk FOREIGN KEY (userEmail) REFERENCES T_User(email) ON DELETE SET NULL
);

CREATE TABLE T_OrderItem (
	itemId	INT				NOT NULL,
	nrOfItems INT 			NOT NULL,
    orderNr	INT	 			NOT NULL,
    CONSTRAINT T_OrderItem_pk PRIMARY KEY (orderNr, itemId),
	CONSTRAINT T_OrderItem_fk FOREIGN KEY (orderNr) REFERENCES T_Order(orderNr),
    CONSTRAINT T_OrderItem_fk1 FOREIGN KEY (itemId) REFERENCES T_Book(itemId)
);

GRANT CREATE USER ON *.* TO 'root'@'localhost';

-- CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'admin';
-- GRANT ALL PRIVILEGES on DB_Webbshop.* TO 'admin'@'localhost';

CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON DB_Webbshop.* TO 'admin'@'%';
FLUSH PRIVILEGES;

INSERT INTO T_Category (genre)
VALUES ('Fantasy'),
       ('Science Fiction'),
       ('Romance'),
       ('Mystery'),
       ('Fiction'),
       ('Historical'),
       ('Thriller');
       
INSERT INTO T_Category (genre)
VALUES ('Dystopian'),
       ('Adventure'),
       ('NonFiction');

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

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781401208417", "V for Vendetta", "Dystopian", "Alan Moore and David Lloyd", 100, 190);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780008365943", "How To Kill Your Family", "Thriller", "Bella Mackie", 0, 120);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780262516990", "How To Architect", "NonFiction", "Doug Patt", 170, 160);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780008201470", "Green Eggs and Ham", "Adventure", "Dr. Seuss", 140, 95);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9781784876609", "Animal Farm", "Dystopian", "George Orwell", 250, 60);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780701183691", "The Boy with the Cuckoo-Clock Heart", "Romance", "Mathias Malzieu", 100, 99);

INSERT INTO T_Book (isbn, title, genre, author, nrOfCopies, price)  
VALUES("9780575104044", "Steelheart", "Science Fiction", "Brandon Sanderson", 100, 99);

INSERT INTO T_User (authority, name, email, password) 
VALUES("Admin", "Admin", "admin@admin.se", "123");

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

/*
UPDATE T_Book
SET T_Book.nrOfCopies = 10, T_Book.price = 100 WHERE itemId = 10;

UPDATE T_Book
SET T_Book.nrOfCopies = 10, T_Book.price = 100 WHERE itemId = 10;

SELECT * FROM T_Order WHERE userEmail = "poriazov@kth.se";

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
DELETE FROM book;
ALTER TABLE book AUTO_INCREMENT = 1001;

DELETE FROM category;
ALTER TABLE category AUTO_INCREMENT = 1001;

INSERT INTO `category` (`name`) VALUES ('Science'),('Adventure'),('Comics'),('Romance'),('Classics');

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('All About Love', 'Bell Hooks', '', 599, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Road', 'Cormac McCarthy', '', 99, 0, FALSE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Dune', 'Frank Herbert', '', 171, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Wrong Place Wrong TIme', 'Gillian McAllister', '', 1875, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Brave New World', 'Aldous Huxley', '', 133, 0, FALSE, FALSE, 1001);


INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Jurassic Park', 'Michael Crichton', '', 699, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('A Court of Mist and Fury', 'Sarah J Maas', '', 699, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Great Gasby', 'F Fitzgeraid', '', 1399, 0, FALSE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Harry Potter', 'J K Rowling', '', 114, 0, FALSE, FALSE, 1002);


INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Creepy Carrots', 'Aaron Reynolds', '', 291, 0, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Spy X Family', 'Tatsuya Endo', '', 706, 0, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Marvel spider man', 'Pi Kids', '', 291, 0, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Legend of Zelda', 'Akira Himekawa', '', 291, 0, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Demon Slayer', 'Koyoharu Gotouge', '', 499, 0, FALSE, FALSE, 1003);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('It Ends with Us', 'Colleen Hoover', '', 498, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Ugly Love', 'Colleen Hoover', '', 639, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Reminders of Him', 'Colleen Hoover', '', 429, 0, FALSE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid', '', 352, 0, TRUE, FALSE, 1004);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Little Dorrit', 'Charles Dickens', '', 599, 0, TRUE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Iliad', 'Homer', '', 699, 0, TRUE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Brothers Karamazov', 'Fyodor Dostoyevski', '', 799, 0, TRUE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Art of War', 'Sun Tzu', '', 99, 0, FALSE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('To Kill A Mocking Bird', 'Harper Lee', '', 699, 0, TRUE, FALSE, 1005);


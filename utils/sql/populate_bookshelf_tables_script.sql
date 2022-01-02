INSERT INTO bookstorage.books(isbn, title, publisher, genre, `year`, price, description, stock) VALUES('1234567890123', 'Sapiens', 'Harper', 'Science', 2011, 48, 'A brief history of humankind', 100);
INSERT INTO bookstorage.books(isbn, title, publisher, genre, `year`, price, description, stock) VALUES('1234567890124', 'Homo deus', 'Harvill Secker', 'Science', 2016, 52, 'A brief history of tomorrow', 100);
INSERT INTO bookstorage.books(isbn, title, publisher, genre, `year`, price, description, stock) VALUES('1234567890125', '21 Lessons for the 21st Century', 'Harvill Secker', 'Lifestyle', 2018, 4, NULL, 100);


INSERT INTO bookstorage.authors(lastname, firstname) VALUES('Harari', 'Yuval Noah');
INSERT INTO bookstorage.authors(lastname, firstname) VALUES('Eminescu', 'Mihai');


INSERT INTO bookstorage.authors_books(authors_id, books_isbn, author_index) VALUES((SELECT id from authors WHERE lastname='Harari' AND firstname='Yuval Noah'), (SELECT isbn FROM books WHERE title='Sapiens'), 1);
INSERT INTO bookstorage.authors_books(authors_id, books_isbn, author_index) VALUES((SELECT id from authors WHERE lastname='Harari' AND firstname='Yuval Noah'), (SELECT isbn FROM books WHERE title='Homo deus'), 2);
INSERT INTO bookstorage.authors_books(authors_id, books_isbn, author_index) VALUES((SELECT id from authors WHERE lastname='Harari' AND firstname='Yuval Noah'), (SELECT isbn FROM books WHERE title='21 Lessons for the 21st Century'), 3);
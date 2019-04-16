/* #1 Who checked out the book 'The Hobbit’ */

SELECT member.id, member.name 
FROM member, book, checkout_item 
WHERE member.id = checkout_item.member_id 
AND book.id = checkout_item.book_id 
AND book.title = "the hobbit" COLLATE NOCASE 
ORDER BY member.id ASC;

/* #2 How many people have not checked out anything? */

SELECT member.id, member.name 
FROM member 
WHERE NOT EXISTS 
	(SELECT * FROM checkout_item 
	WHERE checkout_item.member_id = member.id)
ORDER BY member.id ASC;

/* #3 What books and movies aren't checked out? */

SELECT "Book" AS item_type, book.id, book.title 
FROM book
WHERE NOT EXISTS (SELECT * FROM checkout_item WHERE checkout_item.book_id = book.id)

UNION

SELECT "Movie" AS item_type, movie.id, movie.title FROM movie
WHERE NOT EXISTS (SELECT * FROM checkout_item WHERE checkout_item.movie_id = movie.id)

ORDER BY item_type ASC;

/* #4 Add the book 'The Pragmatic Programmer', and add yourself as a member. 
 * Check out 'The Pragmatic Programmer'. 
 * Use your query from question 1 to verify that you have checked it out. 
 * Also, provide the SQL used to update the database. 
 */

INSERT INTO book (id, title) VALUES ("The Pragmatic Programmer");

INSERT INTO member(id, name) VALUES ("Dalzy Mendoza");

INSERT INTO checkout_item(member_id, book_id)
SELECT member.id, book.id
FROM member, book
WHERE member.name = "Dalzy Mendoza" COLLATE NOCASE
AND book.title = "The Pragmatic Programmer" COLLATE NOCASE;

SELECT member.id, member.name 
FROM member, book, checkout_item 
WHERE member.id = checkout_item.member_id 
AND book.id = checkout_item.book_id 
AND book.title = "the pragmatic programmer" COLLATE NOCASE 
ORDER BY member.id ASC;

/* #5 Who has checked out more than 1 item? 
 */

SELECT id, name
FROM member
WHERE id IN
	(SELECT member_id
	FROM checkout_item 
	GROUP BY member_id
	HAVING COUNT(*) > 1)
ORDER BY id ASC;
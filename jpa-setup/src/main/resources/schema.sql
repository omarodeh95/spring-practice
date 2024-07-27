DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP SEQUENCE IF EXISTS authors_seq_id;

CREATE SEQUENCE authors_seq_id
START WITH 1
INCREMENT BY 1;

CREATE TABLE authors (
  id bigint DEFAULT nextval('authors_seq_id') NOT NULL,
  name text,
  age integer,
  CONSTRAINT authors_pkey PRIMARY KEY (id)
);

CREATE TABLE books (
  isbn text NOT NULL,
  title text,
  author_id bigint, -- Corrected column name
  CONSTRAINT books_pkey PRIMARY KEY (isbn),
  CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id)
);


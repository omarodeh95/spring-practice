DROP TABLE IF EXISTS  books;

DROP TABLE IF EXISTS  authors;

DROP SEQUENCE IF EXISTS "authors_seq_id";

CREATE TABLE authros (
  "id" bigint DEFAULT nextval("authors_seq_id") NOT NULL,
  "name" text,
  "age" integer,
  CONSTRAINT "authors.pkey" PRIMARY KEY ("id")
);

CREATE TABLE books(
  "isbn" text NOT NULL,
  "title" text,
  "authror_id" bigint,
  CONSTRAINT "books.pkey" PRIMARY KEY ("isbn"),
  CONSTRAINT "fk_author" FOREIGN KEY(author_id) REFERENCES authors(id)
);

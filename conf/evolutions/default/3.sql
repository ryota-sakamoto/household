# --- !Ups

CREATE TABLE item (
  user_id INT NOT NULL,
  category_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  value INT NOT NULL,
  date VARCHAR(10) NOT NULL
);

# --- !Downs

DROP TABLE item;
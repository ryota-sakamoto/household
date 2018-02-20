# --- !Ups

CREATE TABLE category (
  category_id int NOT NULL,
  user_id int NOT NULL,
  name varchar(255) NOT NULL,
  memo varchar(255),
  PRIMARY KEY (category_id, user_id)
);

# --- !Downs

DROP TABLE category;
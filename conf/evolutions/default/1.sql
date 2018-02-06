# --- !Ups

CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  is_admin BOOLEAN DEFAULT false,
  PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE user;
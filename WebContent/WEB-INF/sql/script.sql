CREATE SEQUENCE user_serial START 1;

CREATE TABLE users (
    uid integer PRIMARY KEY DEFAULT nextval('user_serial'),
    username varchar(40) UNIQUE
);

CREATE SEQUENCE widget_serial START 1;

CREATE TABLE widgets (
    wid integer PRIMARY KEY DEFAULT nextval('widget_serial'),
    creator_uid integer,
    widget_name varchar(40) NOT NULL,
    FOREIGN KEY (creator_uid) REFERENCES users (uid)
);
DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-JANUARY-30 10:00','Завтрак', 800),
       (100001, '2020-JANUARY-31 15:00','Обед', 300),
       (100001, '2020-JANUARY-30 12:00','Обед', 1500),
       (100000, '2020-JANUARY-31 13:00','Ужин', 100);
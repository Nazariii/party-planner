INSERT INTO `user`
(
  `auth_token`,
  `creation_date`,
  `email`,
  `name`,
  `password`)
VALUES
  (
    'auth_token', '1971-01-01 00:00:01.000000', 'email', 'name', 'password'),
  ('auth_token2', '1971-01-01 00:00:01.000000', 'email2', 'name2', 'password2');


INSERT INTO `event`
(
  `event_status`,
  `name`,
  `user_id`)
VALUES
  ('CLOSED', 'one', 1),
  ('CLOSED', 'two', 2);

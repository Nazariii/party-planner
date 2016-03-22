INSERT INTO `user`
(
  `auth_token`,
  `creation_date`,
  `email`,
  `name`,
  `password`)
VALUES
  (
    'auth_token', '1971-01-01 00:00:01.000000', 'email', 'name', '$2a$10$O2EO.dePSJNsu/sNEcQa5ej2leCa8B.Q95A2pQ.OOj.9bFPLHplBO'),
  ('auth_token2', '1971-01-01 00:00:01.000000', 'email2', 'name2', '$2a$10$O2EO.dePSJNsu/sNEcQa5ej2leCa8B.Q95A2pQ.OOj.9bFPLHplBO');


INSERT INTO `event`
(
  `event_status`,
  `name`,
  `user_id`)
VALUES
  ('CLOSED', 'one', 1),
  ('CLOSED', 'two', 1);

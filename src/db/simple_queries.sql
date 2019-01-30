select * from users;

insert into users(id, login, password, card_id, salt) values(3, "Adam3", "pass3", 333, 3333);

insert into users(login) values ("Adam4");

UPDATE users SET card_id = aes_encrypt(card_id, salt) WHERE id<3;

SELECT id, login, salt, cast(aes_decrypt(card_id, salt) as char(128)) from users where id<3;
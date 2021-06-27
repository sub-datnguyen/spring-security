-- password of all users are "12345" and encrypted using bcrypt encoder
INSERT INTO `user` (`id`, `version`, `username`, `password`) VALUES ('1', 0, 'admin', '$2a$04$IZPDzcTKUZ75ERKSGGxaIu18bEaZrhOqqeVh0tW3jTGEJUW/3XKOW');
INSERT INTO `user` (`id`, `version`, `username`, `password`) VALUES ('2', 0, 'john', '$2a$04$IZPDzcTKUZ75ERKSGGxaIu18bEaZrhOqqeVh0tW3jTGEJUW/3XKOW');
INSERT INTO `user` (`id`, `version`, `username`, `password`) VALUES ('3', 0, 'david', '$2a$04$IZPDzcTKUZ75ERKSGGxaIu18bEaZrhOqqeVh0tW3jTGEJUW/3XKOW');

INSERT INTO `role`(`id`, `name`) VALUES ('1', 'ADMIN');
INSERT INTO `role`(`id`, `name`) VALUES ('2', 'MANAGER');
INSERT INTO `role`(`id`, `name`) VALUES ('3', 'USER');

INSERT INTO `authority`(`id`, `name`) VALUES ('1', 'READ');
INSERT INTO `authority`(`id`, `name`) VALUES ('2', 'WRITE');
INSERT INTO `authority`(`id`, `name`) VALUES ('3', 'VIEW_OTHER_USERS');

INSERT INTO `role_authorities`(`role_id`, `authorities_id`) VALUES ('1', '2');
INSERT INTO `role_authorities`(`role_id`, `authorities_id`) VALUES ('1', '3');
INSERT INTO `role_authorities`(`role_id`, `authorities_id`) VALUES ('2', '2');
INSERT INTO `role_authorities`(`role_id`, `authorities_id`) VALUES ('3', '1');

INSERT INTO `user_roles`(`user_id`, `roles_id`) VALUES ('1', '1');
INSERT INTO `user_roles`(`user_id`, `roles_id`) VALUES ('2', '2');
INSERT INTO `user_roles`(`user_id`, `roles_id`) VALUES ('3', '3');



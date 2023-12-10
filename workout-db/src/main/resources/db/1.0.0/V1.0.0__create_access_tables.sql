USE `workout`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `username` varchar(50) NOT NULL,
                         `password` char(68) NOT NULL,
                         `enabled` tinyint NOT NULL,
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



INSERT INTO `users`
VALUES
    ('honza','{bcrypt}$2a$10$EZRUEVpR3CrThXDjOMuSZ.QYK.2j5C92pOLJAz3WN8XZAGO8AHnUy',1),
    ('dita','{bcrypt}$2a$10$EZRUEVpR3CrThXDjOMuSZ.QYK.2j5C92pOLJAz3WN8XZAGO8AHnUy',1);

INSERT INTO `athlete` (id,  username, password, enabled)
VALUES
    (24085813, 'honza','{bcrypt}$2a$10$EZRUEVpR3CrThXDjOMuSZ.QYK.2j5C92pOLJAz3WN8XZAGO8AHnUy',1),
    (123, 'dita','{bcrypt}$2a$10$EZRUEVpR3CrThXDjOMuSZ.QYK.2j5C92pOLJAz3WN8XZAGO8AHnUy',1);

commit;

CREATE TABLE `authorities` (
                               `username` varchar(50) NOT NULL,
                               `authority` varchar(50) NOT NULL,
                               UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
                               CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `authorities`
VALUES
    ('honza','ROLE_ADMIN'),
    ('honza','ROLE_ATHLETE'),
    ('dita','ROLE_ATHLETE');
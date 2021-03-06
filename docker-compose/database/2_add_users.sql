CREATE USER 'studies'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'studies'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'studies'@'localhost';
GRANT ALL ON *.* TO 'studies'@'%';

CREATE USER 'users'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'users'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'users'@'localhost';
GRANT ALL ON *.* TO 'users'@'%';

CREATE USER 'import'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'import'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'import'@'localhost';
GRANT ALL ON *.* TO 'import'@'%';

CREATE USER 'datasets'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'datasets'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'datasets'@'localhost';
GRANT ALL ON *.* TO 'datasets'@'%';
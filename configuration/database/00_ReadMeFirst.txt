1.) Start mysql command line
	mysql -u root -p
    Password: root
3.) Create new database educama
	mysql> create database educama DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
4.) Create database user 'dbuser' (password = user name)
	mysql> create user 'dbuser'@'localhost' IDENTIFIED BY 'dbuser';
5.) Authorize 'dbuser' to access database 'educama'
	mysql> grant all ON educama.* TO 'dbuser'@'localhost';
6.) Create new database camunda
	mysql> create database camunda DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
7.) Create database user 'camunda' (password = user name)
	mysql> create user 'camunda'@'localhost' IDENTIFIED BY 'camunda';
8.) Authorize 'dbuser' to access database 'educama'
	mysql> grant all ON camunda.* TO 'camunda'@'localhost';
9.) Enable authorization
	mysql> flush privileges;


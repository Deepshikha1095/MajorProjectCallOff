<?php 
	include("config.php");
	$con=mysql_connect($musql_host_name, $mysql_user_name, $mysql_password);
	$query="create database $dbname";
	mysql_query($query,$con);
	mysql_select_db($dbname, $con);
	$query="create table user_details(id int(100) NOT NULL AUTO_INCREMENT, primary key(id), user_name varchar(20), phone_number varchar(20), password varchar(20), ip_address varchar(20))";
	mysql_query($query,$con);
	$query="insert into user_details(user_name, phone_number, password, ip_address) values ('admin', '0000000000', 'admin', '0.0.0.0')";
	mysql_query($query,$con);
	mysql_close($con);
	echo "Your server for using CallOff is configured and ready to use...";
?>
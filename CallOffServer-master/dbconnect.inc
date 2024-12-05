<?php
include("config.php");
error_reporting(E_ERROR | E_WARNING | E_PARSE);


$con=mysql_connect($musql_host_name, $mysql_user_name, $mysql_password);

mysql_select_db($dbname,$con);


?>
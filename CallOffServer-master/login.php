<?php
include("dbconnect.inc");

$loginname    =  $_GET['user_name'];
$loginpass    =  $_GET['password'];

$rsUser=mysql_query("select * from user_details where user_name='$loginname'",$con);
if(mysql_num_rows($rsUser) == 1)
{
	$row = mysql_fetch_array($rsUser);

	if($row['password']==$loginpass)
	{
		echo $row['user_name'].'#'.$row['phone_number'];
	}
}
?>
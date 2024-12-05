<?php 
	//include("config.php");
	include("dbconnect.inc");
	
$username  = $_GET["etUserName"];
$pass   = $_GET["etPassword"];
$phone   = $_GET["etMobileNumber"];
$ip_address = $_GET["ip_address"];
	
	$rows = mysql_query("select * from user_details",$con);
	$id = mysql_num_rows($rows) + 1;
	$qr = mysql_query("insert into user_details(id, user_name, password, phone_number, ip_address) values('$id','$username','$pass','$phone','$ip_address')",$con);
	
	if($qr){
		$comm['status'] = 'success';
	}else{
		$comm['status'] = 'error';
	}
?>
 
 
 
 
<?php
include("dbconnect.inc");

$rsUser=mysql_query("select * from user_details",$con);
if(mysql_num_rows($rsUser) >= 1)
{
	while($row=mysql_fetch_array($rsUser))
	{
		echo $row["user_name"]."#".$row["phone_number"]."#".$row["ip_address"].",";
	}
}
?>
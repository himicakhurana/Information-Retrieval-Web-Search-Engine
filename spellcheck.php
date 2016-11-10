<?php

include 'SpellCorrector.php';

if (isset($_GET["q"])) //if more info is clicked display table 2

		{

			//echo"More info clicked!";

			$symbol=$_GET["q"];

   header('Content-Type: application/json');

$result=SpellCorrector::correct($symbol);

            echo json_encode($result);



        	



}

//it will output *october*

?>


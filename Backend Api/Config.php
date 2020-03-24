<?php 
define('DB_USERNAME','root');
define('DB_PASSWORD','');
define('DB_NAME','fcm');
define('DB_HOST','localhost');

	//defined a new constant for firebase api key
define('FIREBASE_API_KEY', 'AAAA68T18fY:APA91bHA-aSBkwOxLmbKYglMjz5QbRFJUyYkCoBjCUz2ARHe9GEc3NfjK6Pof27HVMBbHlGymYM0cjTYJO2OWdW4tEBGYQHE4ENjwKQ6WKmWawAyS1xZKia-ki52DqzABvQBtyY7dLWe');

$con = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);

        //Checking if any error occured while connecting
if (mysqli_connect_errno()) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}else{  //echo "Connect"; 
  
   
   }

?>


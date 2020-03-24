<?php 
	require_once 'DbOperation.php';
	$response = array(); 

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$token = $_POST['token'];
		$username = $_POST['username'];
		$password = $_POST['password'];

		$db = new DbOperation(); 

		$result = $db->registerDevice($username,$password,$token);

		if($result == 0){
			$response['status'] = false; 
			$response['message'] = 'Device registered successfully';
		}elseif($result == 2){
			$response['status'] = true; 
			$response['message'] = 'Device already registered';
		}else{
			$response['status'] = true;
			$response['message']='Device not registered';
		}
	}else{
		$response['status']=true;
		$response['message']='Invalid Request...';
	}

	echo json_encode($response);
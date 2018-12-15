# MySQLDatabase
This app connects with the online 000webhost database and registers or retrieves data from it using php files.


## Dependencies to be added in build.gradle(app) under dependencies 
```
implementation 'com.google.http-client:google-http-client-android:+' 
implementation 'com.google.api-client:google-api-client-android:+'  
implementation 'com.google.api-client:google-api-client-gson:+'
```


## Add the following in build.gradle(app) under android{}
```
useLibrary 'org.apache.http.legacy'
    packagingOptions {
        exclude 'META-INF/DEPENDENCIES.txt'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/dependencies.txt'
        exclude 'META-INF/LGPL2.1'
    }
```

#### The code to register tuples in the Database are in MainActivity and the code to retrieve from the Database is in RetrieveData activity.


## Add the following permissions in the Manifest file.
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/> 
```

## register.php
We have to create a php file with the following contents and upload it in the File Manager->public_html in order to connect and register data into the 000webhost Database.
```
<?php

	$conn =new mysqli('DB Host','DB User','Password','DB Name');   //Copy the information from Manage Databases
  
	$id=$_POST["ID"];
	$name=$_POST["Name"];
	$age=$_POST["Age"];
	$num=$_POST["Number"];
	
  //My table name is Details
	$query=("INSERT INTO Details(ID,Name,Age,Number) VALUES('{$id}','{$name}','{$age}','{$num}')");
	mysqli_query($conn,$query);
	mysqli_close($conn);

?>
```
## retrieve.php
We have to create a php file with the following contents and upload it in the File Manager->public_html in order to connect and retrieve data from the 000webhost Database.
```
<?php

	$conn =new mysqli('DB Host','DB User','Password','DB Name');   //Copy the information from Manage Databases
	
	$response=array();	
  
  //My table name is Details
	$query="SELECT * FROM Details WHERE 1";
	$result=mysqli_query($conn,$query);
	
	if(mysqli_num_rows($result)>0){
		$response['success']=1; 
		$Details=array();
		while($row=mysqli_fetch_assoc($result))
		{
			array_push($Details,$row);
		}
		$response['Details']=$Details;
	}
	else
	{
		$response['success']=0;
		$response['message']='No data';
	}
		 
	echo json_encode($response);
	mysqli_close($conn);
	
?>
```

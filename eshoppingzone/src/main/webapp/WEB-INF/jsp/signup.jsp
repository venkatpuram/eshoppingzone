<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

/* Full-width input fields */
input[type=text], input[type=password] {
	width: 100%;
	padding: 8px;
	margin: 2px 0 10px 0;
	display: inline-block;
	border: none;
	background: #f1f1f1;
}

/* Add a background color when the inputs get focus */
input[type=text]:focus, input[type=password]:focus {
	background-color: #ddd;
	outline: none;
}

/* Set a style for all buttons */
button {
	background-color: rgb(10, 102, 240);
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
	opacity: 0.9;
}

button:hover {
	opacity: 1;
}

/* Extra styles for the cancel button */
.cancelbtn {
	padding: 14px 20px;
	background-color: #646161;
}

/* Float cancel and signup buttons and add an equal width */
.cancelbtn, .signupbtn {
	float: left;
	width: 50%;
}

.signupbtn:hover {
	background-color: #45a049;
}

/* Add padding to container elements */
.container {
	padding: 16px;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: #474e5d;
	padding-top: 50px;
}

/* Modal Content/Box */
.modal-content {
	background-color: #fefefe;
	margin: 5% auto 15% auto;
	/* 5% from the top, 15% from the bottom and centered */
	border: 1px solid #888;
	width: 80%; /* Could be more or less, depending on screen size */
}

/* Style the horizontal ruler */
hr {
	border: 1px solid #f1f1f1;
	margin-bottom: 25px;
}

/* The Close Button (x) */
.close {
	position: absolute;
	right: 35px;
	top: 15px;
	font-size: 40px;
	font-weight: bold;
	color: #f1f1f1;
}

.close:hover, .close:focus {
	color: #f44336;
	cursor: pointer;
}

/* Clear floats */
.clearfix::after {
	content: "";
	clear: both;
	display: table;
}
</style>
</head>
<body>
	<form class="modal-content" action="/profilecreation"
		onsubmit="return fun()">
		<div class="container">
			<h1>Sign Up</h1>
			<p>Please fill in this form to create an account.</p>
			<hr>
			<label for="mobileno"><b>Mobile Number</b></label> 
			<input id="mobileNumber" type="text" placeholder="Enter Mobile Number" name="mobilenumber" autofocus="autofocus" required> 
			<label for="email"><b>Email</b></label> 
			<input type="text" placeholder="Enter Email" name="email" required> 
			<label for="psw"><b>Password</b></label> 
			<input id="password" type="password" placeholder="Enter Password" name="password" required> 
			<label for="psw-repeat"><b>Re-Enter	Password</b></label> 
			<input id="passswordrepeat " type="password" placeholder="Re-Enter Password" name="psw-repeat" required>

			<label> <input type="checkbox" checked="checked" name="remember" style="margin-bottom: 15px"> Remember me</label>

			<p>
				By creating an account you agree to our <a href="#"
					style="color: dodgerblue">Terms & Privacy</a>
			</p>

			<div class="clearfix">
				<button type="button" class="cancelbtn">Cancel</button>
				<button type="submit" class="signupbtn">Sign Up</button>
			</div>
		</div>
	</form>
	<script>
		function fun() {
			var x = document.getElementById("mobileNumber").value;

			var a = document.getElementById("password").value;

			var b = document.getElementById("passswordrepeat").value;

			var count = 0;
			if (x >= 1)
				++count;

			while (x / 10 >= 1) {
				x /= 10;
				++count;
			}

			y = count;
			if (isNaN(x)) {
				alert("mobile number contains only Numbers");
				return false;
			}

			else if (y<10 || y>10) {
				alert("mobile number contains 10 digits");
				return false;
			}

			return true;

		}
	</script>
</body>
</html>
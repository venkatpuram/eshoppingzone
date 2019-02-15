<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div>
	<jsp:include page="index.jsp" />
</div>
<div>
<h2>Order placed successfully, will be delivered within 7 days!!!</h2><br>
<h3>Order Summary</h3><br>
<h4>Customer Details :</h4>
	<p>
	Customer Id : ${order.useraddress.customerId}<br>
	Full Name : ${order.useraddress.fullName}<br>
	Mobile Number : ${order.useraddress.mobileNumber}<br>
	Address : <br> 
		Flat No : ${order.useraddress.flatNumber}<br>
		City : ${order.useraddress.city}<br>
	  	PinCode : ${order.useraddress.pincode}<br>
		State : ${order.useraddress.state}<br>
	</p>
	</div>
	<h4>Order Details :</h4>
	<div>
	<p>
	Order Id : ${order.orders.orderId }<br>
	Order Date : ${order.orders.orderDate}<br>
	Quantity : ${order.orders.quantity}<br>
	Amount : ${order.orders.ammountPaid}<br>
	<%-- Order Status : ${order.orders.orderStatus}<br> --%>
	
	</div>
</body>
</html>
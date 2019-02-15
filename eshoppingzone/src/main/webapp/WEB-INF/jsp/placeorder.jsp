<%@ page isELIgnored="false" language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="jstl" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
   
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
<h1 align="center">Address</h1>
	<form action="payment">
		<table align="center">
			<tr>
				<td><label>Customer ID:</label></td>
				<td><input type="text" name="customerId" readonly="readonly" value="${address.customerId}"></td>
			</tr>
			<tr>
				<td><label>Fullname:</label></td>
				<td>${address.fullName}</td>
			</tr>
			<tr>
				<td><label>Mobile Number:</label></td>
				<td>${address.mobileNumber}</td>
			</tr>
			<tr>
				<td><label>Flat No:</label></td>
				<td>${address.flatNumber}</td>
			</tr>
			<tr>
				<td><label>City:</label></td>
				<td>${address.city}</td>
			</tr>
			<tr>
				<td><label>PinCode:</label></td>
				<td>${address.pincode}</td>
			</tr>
			<tr>
				<td><label>State:</label></td>
				<td>${address.state}</td>
			</tr>
			<tr>
				<td><input type="submit" value="Procced To Payment" name="submit"></td>
			</tr>
		</table>

	</form>
</body>
</html>
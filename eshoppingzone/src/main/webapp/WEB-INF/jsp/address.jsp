<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
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
<spring:form action="address" modelAttribute="address">
	<table align="center">
		<tr>
			<td><label>Fullname:</label></td>
			<td><input type="text" name="fullName" value=""></td>
		</tr>
		<tr>
			<td><label>Mobile Number:</label></td>
			<td><input type="number" name="mobileNumber" value=""></td>
		</tr>
		<tr>
			<td><label>Flat No:</label></td>
			<td><input type="text" name="flatNumber" value=""></td>
		</tr>
		<tr>
			<td><label>City:</label></td>
			<td><input type="text" name="city" value=""></td>
		</tr>
		<tr>
			<td><label>PinCode:</label></td>
			<td><input type="number" name="pincode" value=""></td>
		</tr>
		<tr>
			<td><label>State:</label></td>
			<td><input type="text" name="state" value=""></td>
		</tr>
		<tr>
			<td><input type="submit" value="submit" name="submit"></td>
		</tr>
	</table>
	</spring:form>
</body>
</html>
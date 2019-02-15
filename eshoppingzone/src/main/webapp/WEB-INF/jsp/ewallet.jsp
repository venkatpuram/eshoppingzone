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
<form action="paymoney">
	<h3>Wallet Balance : ${wallet}</h3>
	<input type="submit" value="Click Here to Pay" name="pay">
	</form>
</body>
</html>
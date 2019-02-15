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

	<p><input type="text" name="customerId" readonly="readonly" value="${customerId}"></p>
	<div class="dropdown" align="center">
	
		<h4>Select payment mode to proceed :</h4><button class="btn btn-primary dropbtn">Payment Mode</button>
		<div class="dropdown-content">
			<a href="/walletpayment">E-Wallet</a>
			<a href="/codpayment">Cash On Delivery</a>
		</div>
	</div>
</body>
</html>
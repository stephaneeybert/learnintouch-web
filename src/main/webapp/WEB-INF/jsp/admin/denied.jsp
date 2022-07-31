<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h1>Unauthorized Access !</h1>
	<c:if test="${not empty failed}">
		<div style="color:red;">
			<p>Your fake login attempt was busted, dare again !</p>
			<p>Caused: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
		</div>
	</c:if>
	<p><a href="login">Go back to the login page</a></p>
</body>
</html>
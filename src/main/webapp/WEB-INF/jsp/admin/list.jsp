<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
</head>
  <body>
    <div>
      <c:forEach var="admin" items="${page.content}">
        <c:out value="${admin.login}"/>
        <c:out value="${admin.email}"/>
      </c:forEach>
    </div>
    <div><%@include file='../template/pagination-bar.jsp'%></div>
  </body>
</html>
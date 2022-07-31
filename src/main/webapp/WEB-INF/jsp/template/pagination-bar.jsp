<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
  <ul>
    <c:if test="${not page.firstPage}">
      <li>
        <a href="<c:url value="${page.url}?page.page=1&amp;page.size=${page.size}"/>">First</a>
      </li>
    </c:if>
    <li <c:if test="${not page.hasPreviousPage}">style="display:none;"</c:if>>
      <c:if test="${page.hasPreviousPage}">
       <a href="<c:url value="${page.url}?page.page=${page.number-1}&amp;page.size=${page.size}"/>" title="Go to previous page">Previous</a>
      </c:if>
    </li>
    <c:forEach var="item" items="${page.items}">
      <li <c:if test="${item.currentPage} == ${page.number}">style="display:none;"</c:if>>
    	<c:if test="${item.currentPage}">
      	  <c:out value="${item.number}"/>
        </c:if>
    	<c:if test="${not item.currentPage}">
  	    <a href="<c:url value="${page.url}?page.page=${item.number}&amp;page.size=${page.size}"/>">
  	      <c:out value="${item.number}"/>
  	    </a>
        </c:if>
      </li>
    </c:forEach>
    <li <c:if test="${not page.hasNextPage}">style="display:none;"</c:if>>
      <c:if test="${page.hasNextPage}">
        <a href="<c:url value="${page.url}?page.page=${page.number+1}&amp;page.size=${page.size}"/>" title="Go to next page">Next</a>
      </c:if>
    </li>
    <c:if test="${not page.lastPage}">
      <li>
       <a href="<c:url value="${page.url}?page.page=${page.totalPages}&amp;page.size=${page.size}"/>">Last</a>
      </li>
    </c:if>
  </ul>
</div>

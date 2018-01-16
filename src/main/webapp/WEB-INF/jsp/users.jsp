<title>App users</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>

</head>

<body>
Here are the list of your users:
${users}
<table>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="Name: ${user.name}"/>
            </td>
            <td>
                <c:out value="Email: ${user.email}"/>
            </td>
            <td>
                <fmt:formatDate value="${user.creationDate}" type="date"/>
            </td>
            <td>
                <c:if test="${not empty user.phone}">
                    <c:out value="Phone: ${user.phone}"/>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>


<BR/>


Your Name is : ${name}
<BR/>
Using tag lig:
<c:out value="${name}"/>

<BR/>
<BR/>

External link content:
<c:import var="webData" url="http://www.google.com"/>
<c:out value="${webData}" escapeXml="false"/>
</body>

</html>

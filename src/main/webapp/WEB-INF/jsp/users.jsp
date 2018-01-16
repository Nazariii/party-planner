<title>First Web Application</title>
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
                <td><c:out value="Name: ${user.name}"/></td>
                <td><c:out value="Email: ${user.email}"/></td>
                <td><fmt:formatDate value="${user.creationDate}" type="date"/></td>
            </tr>
        </c:forEach>
    </table>

    <BR/>
    Your Name is : ${name}
    Using tag lig: <c:out value="${name}"/>
</body>

</html>
<%-- 
    Document   : index
    Created on : Jan 25, 2014, 10:23:13 PM
    Author     : wesley
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${loggedInUser == null}">
    <%
        // redirect user to the loginpage if he is not logged in
        response.sendRedirect("index.jsp");
    %>
</c:if>
<html>
    <head>
    </head>
    <body>
        <!-- Made in Europe -->
        <!-- Message Handling -->
        <c:if test="${isUpdated}">
            Your TestCase has been successfully updated.
        </c:if>
        <c:if test="${isCreated}">
            TestCase successfully created.
        </c:if>
        <!-- eof Message Handling -->
        
        <!-- Scheduler Information -->
        <div id="scheduler">
            <h3>Scheduler Information</h3>
            <c:choose>
                <c:when test="${scheduler != null}">
                    Last heartbeat on: <b>${scheduler.lastHeartbeat}</b>
                </c:when>
                <c:otherwise>
                    Scheduler never started.
                </c:otherwise>
            </c:choose>
        </div>
        ====
        <!-- eof Scheduler Information -->
            
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Active</th>
                <th>Status</th>
                <th>Last Tested</th>
                <th>Manage</th>
            </tr>
            <c:forEach var="testcase" items="${testCases}">
            <tr>
                <td>
                    ${testcase.name}
                </td>
                <td>
                    ${testcase.isActive}
                </td>
                <td>
                    ${testcase.status}
                </td>
                <td>
                    ${testcase.lastTested}
                </td>
                <td>
                    <form id="frmEditTestCase" action="loadTestCase" method="post">
                        <input type="hidden" id="case_id" name="case_id" value="${testcase.case_id}">
                        <button type="submit" value="submit">Edit</button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
        <form id="frmNewTestCase" action="newTestCase" method="post">
            <button type="submit" id="btnNewTestCase">New</button>
        </form>
        <div><a href="homepage.html">Back to Home</a></div>
        
    </body>
</html>

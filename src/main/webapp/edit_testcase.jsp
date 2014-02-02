<%-- 
    Document   : edit_testcase
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
        
        <form id="frmEditTestCase" action="editTestCase" method="post">
            <input type="text" id="caseName" name="caseName" placeholder="Enter Case Name" value="${testCase.name}">
            <input type="checkbox" id="true" name="isActive" ${testCase.isActive == true ? 'checked' : ''}> Active
            <select id="selectTimeframe" name="selectTimeframe">
                <option value="" disabled selected>Select your option</option>
                <option value="EVERY_HOUR" ${(testCase.timeframe == 'EVERY_HOUR') ? 'selected' : ''}>Every hour</option>
                <option value="EVERY_12_HOURS" ${(testCase.timeframe == 'EVERY_12_HOURS') ? 'selected' : ''}>Every 12 hours</option>
                <option value="EVERY_24_HOURS" ${(testCase.timeframe == 'EVERY_24_HOURS') ? 'selected' : ''}>Every day</option>
            </select>

            <input type="hidden" id="case_id" name="case_id" value="${testCase.case_id}">
            <input type="hidden" id="actions" name="actions" value="empty">
        </form>
        <table class="table" id="tblActions" border="1">
            <tr id="tr0" style="display:none">
                <td>
                    <select id="select0" onchange="addSubLevel(0)">
                        <option value="" disabled selected>Select your option</option>
                        <option value="GET_URL">Get URL</option>
                        <option value="CLICK">Click</option>
                        <option value="SEND_KEYS">Send Keys</option>
                        <option value="CLEAR">Clear</option>
                        <option value="CONTAINS_TEXT">Contains Text</option>
                    </select>
                </td>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
            </tr>
        </table>

        <button id="btnAddCriteria" onclick="addCriteria()">Add</button>
        <button id="btnEditTestCase" onclick="editTestCase()">Save</button>

        <script>
            var actions = eval(${actions}); // convert json data from request
            if (!actions)
            {
                addCriteria(); // add one row on page load if it is an empty testcase
            }
            else // load json data
            {
                for (var action in actions) {
                    var actionType = actions[action]['action'];

                    if (actionType == "GET_URL")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;

                        // set url
                        addSubLevel(id);
                        document.getElementById("urlValue" + id).value = actions[action]['url'];
                    }
                    else if (actionType == "CLICK")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;

                        // set elementType and path
                        addSubLevel(id);
                        document.getElementById("elementTypeId" + id).value = actions[action]['elementType'];
                        document.getElementById("clickId" + id).value = actions[action]['path'];
                    }
                    else if (actionType == "SEND_KEYS")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;

                        // set elementType and path, and value
                        addSubLevel(id);
                        document.getElementById("elementTypeId" + id).value = actions[action]['elementType'];
                        document.getElementById("sendKeysId" + id).value = actions[action]['path'];
                        document.getElementById("sendKeysValue" + id).value = actions[action]['value'];
                    }
                    else if (actionType == "CLEAR")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;

                        // set elementType and path
                        addSubLevel(id);
                        document.getElementById("elementTypeId" + id).value = actions[action]['elementType'];
                        document.getElementById("clearId" + id).value = actions[action]['path'];
                    }
                    else if (actionType == "CONTAINS_TEXT")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;

                        // set value
                        addSubLevel(id);
                        document.getElementById("containsTextId" + id).value = actions[action]['value'];
                        document.getElementById("containsTextAssert" + id).value = actions[action]['assert'];
                    }
                }
            }


            function editTestCase()
            {
                var table = document.getElementById("tblActions");
                var rowCount = table.rows.length;

                // go along every row in the table to see what actions must be performed
                var actionsToPerform = "";
                var actions = new Array(); // all individual actions

                for (var i = 1; i < rowCount; i++)
                {
                    var selectedValue = document.getElementById("select" + i).value;

                    var action = new Object();

                    if (selectedValue == "GET_URL")
                    {
                        action.action = "GET_URL";
                        action.url = document.getElementById("urlValue" + i).value;
                    }
                    else if (selectedValue == "CLICK")
                    {
                        action.action = "CLICK";
                        action.elementType = document.getElementById("elementTypeId" + i).value;
                        action.path = document.getElementById("clickId" + i).value;
                    }
                    else if (selectedValue == "SEND_KEYS")
                    {
                        action.action = "SEND_KEYS";
                        action.elementType = document.getElementById("elementTypeId" + i).value;
                        action.path = document.getElementById("sendKeysId" + i).value;
                        action.value = document.getElementById("sendKeysValue" + i).value;
                    }
                    else if (selectedValue == "CLEAR")
                    {
                        action.action = "CLEAR";
                        action.elementType = document.getElementById("elementTypeId" + i).value;
                        action.path = document.getElementById("clearId" + i).value;
                    }
                    else if (selectedValue == "CONTAINS_TEXT")
                    {
                        action.action = "CONTAINS_TEXT";
                        action.value = document.getElementById("containsTextId" + i).value;
                        action.assert = document.getElementById("containsTextAssert" + i).value;
                    }
                    actions.push(action);
                }

                // send the actions to the server
                document.getElementById("actions").value = JSON.stringify(actions);
                document.getElementById("frmEditTestCase").submit();
            }

            function addCriteria()
            {
                var table = document.getElementById("tblActions");
                var id = table.rows.length;
                var row = table.insertRow(id);
                row.innerHTML = table.rows[0].innerHTML; // 0 is placeholder
                row.id = "tr" + id;

                // make the row visible
                row.style.display = "block";

                // change id's of the select and addSubLevel parameter
                row.innerHTML = row.innerHTML.replace("select0", "select" + id);
                row.innerHTML = row.innerHTML.replace("addSubLevel(0", "addSubLevel(" + id);

                return id;
            }

            function addSubLevel(id)
            {
                var row = document.getElementById("tr" + id);
                var selectElementTypes = "" +
                        "<select id=\"elementTypeId" + id + "\">" +
                        "<option value=\"\" disabled selected>Select your option</option>" +
                        "<option value=\"ID\">Id</option>" +
                        "<option value=\"NAME\">Name</option>" +
                        "<option value=\"LINK_TEXT\">LinkText</option>" +
                        "</select>";


                // remove old data
                for (var i = 1; i < 4; i++)
                {
                    row.cells[i].innerHTML = "";
                }

                var selectedValue = document.getElementById("select" + id).value;

                // value = geturl
                if (selectedValue == "GET_URL") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"urlValue" + id + "\" placeholder=\"url\">";
                }

                // value = click
                else if (selectedValue == "CLICK") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = selectElementTypes;

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"clickId" + id + "\" placeholder=\"path\">";
                }

                // value = sendKeys
                else if (selectedValue == "SEND_KEYS") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = selectElementTypes;

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"sendKeysId" + id + "\" placeholder=\"path\">";

                    var cell3 = row.cells[3];
                    cell3.innerHTML = "<input type=\"text\" id=\"sendKeysValue" + id + "\" placeholder=\"text\">";

                }

                // value = clear
                else if (selectedValue == "CLEAR") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = selectElementTypes;

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"clearId" + id + "\" placeholder=\"path\">";
                }

                // value = containsText
                else if (selectedValue == "CONTAINS_TEXT") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"containsTextId" + id + "\" placeholder=\"value\">";

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<select id=\"containsTextAssert" + id + "\"><option value=\"true\">Equals</option><option value=\"false\">Does not Equals</option></select>";
                }
            }
        </script>

    </body>
</html>

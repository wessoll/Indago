<%-- 
    Document   : edit_testcase
    Created on : Jan 25, 2014, 10:23:13 PM
    Author     : wesley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
    </head>
    <body>
        <!-- Made in Europe -->

        <form id="frmEditTestCase" action="editTestCase" method="post">
            <input type="text" id="caseName" name="caseName" placeholder="Enter a Case name" value="${testCase.name}">
            <input type="checkbox" id="true" name="isActive" ${testCase.isActive == true ? 'checked' : ''}> Active
            
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
                        <option value="MATCH_TEXT">Match Text</option>
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
            var actions = eval(${actions}); // convert json data
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
                    if (actionType == "CLICK")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;
                        
                        // set elementType and path
                        addSubLevel(id);
                        document.getElementById("elementTypeId" + id).value = actions[action]['elementType'];
                        document.getElementById("clickId" + id).value = actions[action]['path'];
                    }
                    if (actionType == "SEND_KEYS")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;
                        
                        // set elementType and path
                        addSubLevel(id);
                        document.getElementById("elementTypeId" + id).value = actions[action]['elementType'];
                        document.getElementById("sendKeysId" + id).value = actions[action]['path'];
                        document.getElementById("sendKeysValue" + id).value = actions[action]['value'];
                    }
                    if (actionType == "CLEAR")
                    {
                        // set action type
                        var id = addCriteria();
                        document.getElementById("select" + id).value = actionType;
                        
                        // set elementType and path
                        addSubLevel(id);
                        document.getElementById("elementTypeId" + id).value = actions[action]['elementType'];
                        document.getElementById("clearId" + id).value = actions[action]['path'];
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
                    if (selectedValue == "CLICK")
                    {
                        action.action = "CLICK";
                        action.elementType = document.getElementById("elementTypeId" + i).value;
                        action.path = document.getElementById("clickId" + i).value;
                    }
                    if (selectedValue == "SEND_KEYS")
                    {
                        action.action = "SEND_KEYS";
                        action.elementType = document.getElementById("elementTypeId" + i).value;
                        action.path = document.getElementById("sendKeysId" + i).value;
                        action.value = document.getElementById("sendKeysValue" + i).value;
                    }
                    if (selectedValue == "CLEAR")
                    {
                        action.action = "CLEAR";
                        action.elementType = document.getElementById("elementTypeId" + i).value;
                        action.path = document.getElementById("clearId" + i).value;
                    }
                    actions.push(action);
                }

                // send the actions to the server
                alert(JSON.stringify(actions));
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
                if (selectedValue == "CLICK") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = selectElementTypes;
                    
                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"clickId" + id + "\" placeholder=\"path\">";
                }

                // value = sendKeys
                if (selectedValue == "SEND_KEYS") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = selectElementTypes;
                    
                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"sendKeysId" + id + "\" placeholder=\"path\">";

                    var cell3 = row.cells[3];
                    cell3.innerHTML = "<input type=\"text\" id=\"sendKeysValue" + id + "\" placeholder=\"text\">";

                }

                // value = clear
                if (selectedValue == "CLEAR") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = selectElementTypes;
                    
                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"clearId" + id + "\" placeholder=\"path\">";
                }

                // value = matchText
                if (selectedValue == "MATCH_TEXT") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"matchTextId" + id + "\" placeholder=\"path\">";

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"matchTextValue" + id + "\" placeholder=\"text\">";

                    var cell3 = row.cells[3];
                    cell3.innerHTML = "<select><option>Equals</option><option>Does not Equals</option></select>";
                }
            }
        </script>

    </body>
</html>

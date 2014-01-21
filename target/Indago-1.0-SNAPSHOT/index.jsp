<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="bootstrap.min.css">
    </head>
    <body>
        <!-- Made in The Netherlands -->

        <form id="frmAddAction" action="addAction" method="post">
            <input type="hidden" id="actions" name="actions" value="empty">
        </form>
        <table class="table" id="tblActions" border="1">
            <tr id="tr0" style="display:none">
                <td>
                    <select id="select0" onchange="addSubLevel(0)">
                        <option value="" disabled selected>Select your option</option>
                        <option value="geturl">Get URL</option>
                        <option value="click">Click</option>
                        <option value="sendKeys">Send Keys</option>
                        <option value="clear">Clear</option>
                        <option value="matchText">Match Text</option>
                    </select>
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
        <button id="btnAddAction" onclick="addAction()">Add to Actions</button>

        <script>

            function addAction()
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

                    if (selectedValue == "geturl")
                    {
                        action.action = "geturl";
                        action.value = document.getElementById("urlValue" + i).value;
                    }
                    if (selectedValue == "click")
                    {
                        action.action = "click";
                        action.id = document.getElementById("clickId" + i).value;
                    }
                    if (selectedValue == "sendKeys")
                    {
                        action.action = "sendKeys";
                        action.id = document.getElementById("sendKeysId" + i).value;
                        action.value = document.getElementById("sendKeysValue" + i).value;
                    }
                    if (selectedValue == "clear")
                    {
                        action.action = "clear";
                        action.id = document.getElementById("clearId" + i).value;
                    }
                    actions.push(action);
                }

                // send the actions to the server
                document.getElementById("actions").value = JSON.stringify(actions);
                document.getElementById("frmAddAction").submit();
            }

            function addCriteria()
            {
                var table = document.getElementById("tblActions");
                var rowCount = table.rows.length;
                var row = table.insertRow(rowCount);
                row.innerHTML = table.rows[0].innerHTML; // 0 is placeholder
                row.id = "tr" + rowCount;

                // make the row visible
                row.style.display = "block";

                // change id's of the select and addSubLevel parameter
                row.innerHTML = row.innerHTML.replace("select0", "select" + rowCount);
                row.innerHTML = row.innerHTML.replace("addSubLevel(0", "addSubLevel(" + rowCount);
            }

            function addSubLevel(id)
            {
                var row = document.getElementById("tr" + id);

                // remove old data
                for (var i = 1; i < 4; i++)
                {
                    row.cells[i].innerHTML = "";
                }

                var selectedValue = document.getElementById("select" + id).value;

                // value = geturl
                if (selectedValue == "geturl") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"urlValue" + id + "\" placeholder=\"url\">";
                }

                // value = click
                if (selectedValue == "click") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"clickId" + id + "\" placeholder=\"element id or name\">";
                }

                // value = sendKeys
                if (selectedValue == "sendKeys") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"sendKeysId" + id + "\" placeholder=\"element id or name\">";

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"sendKeysValue" + id + "\" placeholder=\"text\">";

                }

                // value = clear
                if (selectedValue == "clear") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"clearId" + id + "\" placeholder=\"element id or name\">";
                }

                // value = matchText
                if (selectedValue == "matchText") {
                    var cell1 = row.cells[1];
                    cell1.innerHTML = "<input type=\"text\" id=\"matchTextId" + id + "\" placeholder=\"element id or name\">";

                    var cell2 = row.cells[2];
                    cell2.innerHTML = "<input type=\"text\" id=\"matchTextValue" + id + "\" placeholder=\"text\">";

                    var cell3 = row.cells[3];
                    cell3.innerHTML = "<select><option>Equals</option><option>Does not Equals</option></select>";
                }
            }
        </script>

    </body>
</html>

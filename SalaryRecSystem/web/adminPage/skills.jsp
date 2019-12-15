<%-- 
    Document   : skills
    Created on : Dec 12, 2019, 10:11:41 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Skills page</title>
        <style>
            body{
                padding-left: 50px;
            }
            th, td{
                padding: 5px 20px;
            }
            tr{

            }
            input{
                position: fixed;
                top:90px;
                left: 550px;
            }
        </style>
        <script>
            var skillsArr = [];
            var curSize = 10;

            function getSkills(from, to) {
                var url = "http://localhost:8084/SalaryRecSystem/webresources/skills";
                let newUrl = url + "/" + from + "/" + to;
                sendGetRequest(newUrl, null, function (response) {
                    // render list location
                    let xmlDocs = response.responseXML;
                    let skills = xmlDocs.childNodes[0];
                    for (let i = 0; i < skills.children.length; i++) {
                        let skill = skills.children[i];
                        let id = skill.getElementsByTagName("id")[0].textContent.toString();
                        let name = skill.getElementsByTagName("name")[0].textContent.toString();
                        let type = skill.getElementsByTagName("type")[0].textContent.toString();
                        let obj = {
                            id: id,
                            name: name,
                            type: type,
                        };
                        skillsArr.push(obj);
                    }
                    renderSkills(curSize);
                });
            }
            function deleteItem(id) {
                var url = "http://localhost:8084/SalaryRecSystem/webresources/skills";
                let newUrl = url + "/" + id;
                sendGetRequest(newUrl, null, function (response) {
                    alert("Delete successfully");
                    location.reload();
                });
            }

            function renderSkills(length) {
                for (let i = length - 10; i < length; i++) {
                    var row = document.createElement("tr");
                    var id = document.createElement("td");
                    var name = document.createElement("td");
                    var type = document.createElement("td");

                    var updateTd = document.createElement("td");
                    var updateBtn = document.createElement("button");
                    updateBtn.innerHTML = "Update";
                    updateTd.appendChild(updateBtn);

                    var deleteTd = document.createElement("td");
                    var deleteBtn = document.createElement("button");
                    deleteBtn.innerHTML = "Delete";
                    deleteTd.appendChild(deleteBtn);

                    id.innerHTML = skillsArr[i].id;
                    name.innerHTML = skillsArr[i].name;
                    type.innerHTML = skillsArr[i].type;
                    row.appendChild(id);
                    row.appendChild(name);
                    row.appendChild(type);
                    row.appendChild(updateTd);
                    row.appendChild(deleteTd);
                    document.getElementById("data").appendChild(row);
                }
            }
            function createRow(skill) {
                var row = document.createElement("tr");
                var id = document.createElement("td");
                var name = document.createElement("td");
                var type = document.createElement("td");
                id.innerHTML = skill.getElementsByTagName("id")[0].textContent.toString();
                name.innerHTML = skill.getElementsByTagName("name")[0].textContent.toString();
                type.innerHTML = skill.getElementsByTagName("type")[0].textContent.toString();
                row.appendChild(id);
                row.appendChild(name);
                row.appendChild(type);
                return row;
            }
            function loadMore() {
                renderSkills(curSize + 10);
                if (skillsArr.length - curSize < 50) {
                    getSkills(skillsArr.length, skillsArr.length + 50);
                }
            }
        </script>
    </head>
    <body onload="getSkills(0, 50)">
        <h1>List Skills</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="data">

            </tbody>
        </table>
        <input name="btnLoadMore" type="submit" value="Load more" onclick="loadMore()"/>
        <script src="/SalaryRecSystem/js/utils.js"></script>
    </body>
</html>

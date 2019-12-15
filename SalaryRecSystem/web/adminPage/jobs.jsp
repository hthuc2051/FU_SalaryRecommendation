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
        <title>Jobs page</title>
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
                left: 650px;
            }
        </style>
        <script>
            var jobsArr = [];
            var curSize = 10;

            function getSkills(from, to) {
                var url = "http://localhost:8084/SalaryRecSystem/webresources/Jobs";
                let newUrl = url + "/" + from + "/" + to;
                sendGetRequest(newUrl, null, function (response) {
                    // render list location
                    let xmlDocs = response.responseXML;
                    let skills = xmlDocs.childNodes[0];
                    for (let i = 0; i < skills.children.length; i++) {
                        let skill = skills.children[i];
                        let id = skill.getElementsByTagName("id")[0].textContent.toString();
                        let expLevel = skill.getElementsByTagName("expLevel")[0].textContent.toString();
                        let link = skill.getElementsByTagName("link")[0].textContent.toString();
                        let obj = {
                            id: id,
                            expLevel: expLevel,
                            link: link,
                        };
                        jobsArr.push(obj);
                    }
                    renderSkills(curSize);
                });
            }


            function renderSkills(length) {
                for (let i = length - 10; i < length; i++) {
                    var row = document.createElement("tr");
                    var id = document.createElement("td");
                    var expLevel = document.createElement("td");
                    var link = document.createElement("td");

                    var updateTd = document.createElement("td");
                    var updateBtn = document.createElement("button");
                    updateBtn.value = "Update";
                    updateTd.appendChild(updateBtn);

                    var deleteTd = document.createElement("td");
                    var deleteBtn = document.createElement("button");
                    deleteBtn.value = "Delete";
                    deleteTd.appendChild(deleteBtn);

                    id.innerHTML = jobsArr[i].id;
                    expLevel.innerHTML = jobsArr[i].expLevel;
                    link.innerHTML = jobsArr[i].link;
                    row.appendChild(id);
                    row.appendChild(expLevel);
                    row.appendChild(link);
                    row.appendChild(updateBtn);
                    row.appendChild(deleteBtn);
                    document.getElementById("data").appendChild(row);
                }
            }

            function loadMore() {
                renderSkills(curSize + 10);
                if (jobsArr.length - curSize < 50) {
                    getSkills(jobsArr.length, jobsArr.length + 50);
                }
            }
        </script>
    </head>
    <body onload="getSkills(0, 50)">
        <h1>List Jobs</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Update</th
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="data">

            </tbody>
        </table>
        <input  type="submit" value="Load more" onclick="loadMore()"/>
        <script src="/SalaryRecSystem/js/utils.js"></script>
    </body>
</html>

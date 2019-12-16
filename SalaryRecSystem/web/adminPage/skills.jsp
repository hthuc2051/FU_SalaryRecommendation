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
            #btnLoadMore{
                position: fixed;
                top:90px;
                left: 720px;
            }
            .form-update{
                max-width: 300px;
                position: fixed;
                right: 150px;
                top: 150px;
                min-width: 300px;
            }
            .form-update input{
                margin: 10px;
            }
            button{
                cursor: pointer;
            }
            input[type=text] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                box-sizing: border-box;
            }
            input[type=submit] {
                background-color: #4CAF50;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            input[type=submit]:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <form class="form-update" method="POST" action="/SalaryRecSystem/UpdateServlet" >
            <input type="hidden" name="txtId">
            <label for="fname">Skill Name </label>
            <input type="text" id="fname" name="txtSkillName">
            <label for="lname">Skill Type</label>
            <input type="text" id="lname" name="txtSkillType">
            <input type="submit" name="btnAction" value="Update Skill" /><br/>
        </form>

        <h1>List Skills</h1>
        <a class="btn" href="/SalaryRecSystem/adminPage/admin.jsp"  >Back to admin control</a>

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Is Active</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="data">

            </tbody>
        </table>

        <input id="btnLoadMore" name="btnLoadMore" type="submit" value="Load more"/>
        <script src="/SalaryRecSystem/js/utils.js"></script>
        <script src="/SalaryRecSystem/js/admin.js"></script>

    </body>
</html>

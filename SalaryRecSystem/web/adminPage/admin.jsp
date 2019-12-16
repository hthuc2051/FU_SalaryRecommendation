<%-- 
    Document   : index
    Created on : Nov 12, 2019, 2:28:27 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crawlers control page</title>
        <style>
            .btn{
                position: relative;
                outline: none;
                text-decoration: none;
                border-radius: 50px;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;
                text-transform: uppercase;
                height: 60px;
                width: 210px;
                opacity: 1;
                background-color: #ffffff;
                border: 1px solid rgba(22, 76, 167, 0.6);
            }

            .btn:hover{
                animation:  storm 0.7s ease-in-out;
            }
            .btn:hover span{
                animation:  rotate 0.7s ease-in-out;
            }
            .btn span{
                color: #164ca7;
                font-size: 12px;
                font-weight: 500;
                letter-spacing: 0.7px;
                text-transform: uppercase;
            }
            td:first-child{
                margin-top: 0px;
            }
            td{
                padding:0px 50px
            }
            form, a{
                margin: 10px;
            }
        </style>
    </head>
    <body>
 
        <p>${requestScope.DONE}</p>
        
        <h1>Welcome to Salary recommendation sytem</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>View data action</th>
                    <th>Control action</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>

                        <a class="btn" href="/SalaryRecSystem/adminPage/skills.jsp"  >View all skills</a>
                        <a class="btn" href="/SalaryRecSystem/adminPage/jobs.jsp"  >View all jobs</a>
                        <a class="btn" href="/SalaryRecSystem/homePage/home.jsp"  >Go back to home page</a>

                    </td>
                    <td>
                        <form action="/SalaryRecSystem/ProcessServlet" method="POST">
                            <input class="btn" type="submit" value="CrawlSkills" name="btnAction" />
                        </form>
                        <form action="/SalaryRecSystem/ProcessServlet" method="POST">
                            <input class="btn" type="submit" value="CrawlJobs" name="btnAction" />
                        </form>
                        <form action="/SalaryRecSystem/ProcessServlet" method="POST">
                            <input class="btn" type="submit" value="StopCrawl" name="btnAction" />
                        </form>
                        <form action="/SalaryRecSystem/ProcessServlet" method="POST">
                            <input class="btn" type="submit" value="Process" name="btnAction" />
                        </form>
                        <form action="/SalaryRecSystem/ProcessServlet" method="POST">
                            <input class="btn" type="submit" value="Test" name="btnAction" />
                        </form>
                        <form action="/SalaryRecSystem/PdfServlet" method="POST">
                            <input class="btn" type="submit" value="GeneratePDF" name="btnAction" />
                        </form></td>
                </tr>

            </tbody>
        </table>


    </body>
</html>

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
        </style>
    </head>
    <body>
        <h1>Welcome to Salary recommendation sytem</h1>

        <form action="ProcessServlet" method="POST">
            Crawl Skill <br/><input class="btn" type="submit" value="CrawlSkills" name="btnAction" />
        </form>
        <form action="ProcessServlet" method="POST">
            Crawl Jobs <br/><input class="btn" type="submit" value="CrawlJobs" name="btnAction" />
        </form>
        <form action="ProcessServlet" method="POST">
            Stop Crawl <br/><input class="btn" type="submit" value="StopCrawl" name="btnAction" />
        </form>
        <form action="ProcessServlet" method="POST">
            Process <br/><input class="btn" type="submit" value="Process" name="btnAction" />
        </form>
         <form action="ProcessServlet" method="POST">
            Test <br/><input class="btn" type="submit" value="Test" name="btnAction" />
        </form>
        <form action="PdfServlet" method="POST">
            Test <br/><input class="btn" type="submit" value="GeneratePDF" name="btnAction" />
        </form>
    </body>
</html>

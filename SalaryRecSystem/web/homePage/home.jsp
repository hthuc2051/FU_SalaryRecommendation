<%-- 
    Document   : home
    Created on : Dec 11, 2019, 10:23:41 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <title>Welcome page</title>
        <link href="homePage/style.css" rel="stylesheet" type="text/css">

    </head>

    <body>

        <div id="container">
            <ul id="menu-bar">
                <li onclick="loadContent(1);">
                    <img src="images/home.png" class="icon" style="filter: opacity(100%);">
                </li>
                <li style="margin-right: 7px;" onclick="loadContent(2);">
                    <img src="images/login-button.png" class="icon" style="filter: opacity(50%);">
                </li>
            </ul>
            <div class="content">
                <ul class="insinde-content">
                    <li>
                        <div id="msg">Salary recommendation system</div>
                        <p class="line1"></p>
                        <div id="dot"></div>
                        <p class="line2"></p>
                        <div id="msg-1">" Our product give you suitable salary based on experience in each skill "</div>
                        <a href="userPage/user.jsp" id="viewProduct">View our product !</a>

                        <a onclick="loadContent(2);" id="login">Admin Login!</a>
                    </li>
                    <li>
                        <div id="container-login">
                            <div id="container-intro">
                                <ul>
                                    <li id="quotes">"This tab is use for admin only !" </li>
                                    <li id="author">_Thuc Nguyen Huy</li>
                                </ul>
                            </div>
                            <form id="login-form" action="ProcessServlet" method="POST">
                                <p class="form-title">Log In</p>
                                <input class="form-attribute" type="text" name="txtUsername" 
                                       placeholder="Username"><br>
                                <input class="form-attribute" type="password" name="txtPassword" 
                                       placeholder="Password"><br>
                                <input class="form-submit" type="submit" name="btnAction" value="Login">
                            </form>

                        </div>

                    </li>
                </ul>
            </div>

        </div>
        <script>
            function loadContent(i) {
                var content = document.getElementsByClassName("insinde-content");
                var menu_bar = document.getElementById("menu-bar");
                var icon = document.getElementsByClassName("icon");
                if (i == 1) {
                    content[0].style.transform = "translateX(0px)";
                    menu_bar.style.backgroundColor = "rgba(0,0,0,0)";
                    icon[0].style.filter = "opacity(100%)";
                    icon[1].style.filter = "opacity(50%)";
                    icon[2].style.filter = "opacity(50%)";
                    icon[3].style.filter = "opacity(50%)";


                } else if (i == 2) {
                    content[0].style.transform = "translateX(-1190px)";
                    menu_bar.style.backgroundColor = "rgba(0,0,0,0.8)";
                    icon[0].style.filter = "opacity(50%)";
                    icon[1].style.filter = "opacity(100%)";
                    icon[2].style.filter = "opacity(50%)";
                    icon[3].style.filter = "opacity(50%)";

                }
            }
            ;
        </script>
    </body>

</html>

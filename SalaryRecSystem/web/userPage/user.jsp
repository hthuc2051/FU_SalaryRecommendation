<%-- 
    Document   : user
    Created on : Dec 3, 2019, 1:36:02 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset='utf-8'>
        <title>Index</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
              integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link rel='stylesheet' type='text/css' media='screen' href='/SalaryRecSystem/userPage/style.css'>


    </head>
    <body>
        <nav class="navbar">
            <a href="/SalaryRecSystem/homePage/home.jsp" class="nav-back"><img class="icon-back" src="./assets/left-arrow.png"><span class="nav-caption">EXIT</span>
            </a>
            <div class="nav-center">
                <img class="nav-logo" src="/SalaryRecSystem/userPage/assets/logo2.png" />
                <h3 class="text">Salary recommendation system</h3>
            </div>
            <div class="nav-title col-2 col-sm-3">
                <div class="title">
                    <h2 class="text">Welcome, Glad to see you</h2>
                    <h3 class="text"><3</h3>
                </div>
        </nav>
        <!-- Loading -->
        <div id="overlay">
            <div class="lds-dual-ring">
                <div></div>
                <div></div>
            </div>
            <div class="loading-text">Calculating</div>
        </div>
        <div id="container">
            <ul id="menu-bar">
                <li id="btn-skill-choice" style="padding-left:8px;">
                    <img src="./assets/choices.png" class="icon">
                </li>
                <li id="img-nextStep" style="padding-left:8px;" >
                    <img src="./assets/edit.png" class="icon">
                </li>
                <li id="img-cal" >
                    <img src="./assets/analysis.png" class="icon">
                </li>
                <li id="img-cal" onclick="loadContent(4);" >
                    <img style="margin-left: 5px;" src="./assets/search.png" class="icon">
                </li>
            </ul>
            <div class="content">
                <ul class="insinde-content">
                    <li>
                        <p class="title">Select your skills</p>
                        <div class="container-skills-selected">
                            <span class="autocomplete-select">
                                <div class="selected-stage">
                                    <div id="selected-skills"
                                         class="select-pure__select select-pure__select--multiple select-pure__select--opened">
                                        <span id="select-pure__label">

                                        </span>
                                        <span class="clickable" onclick="openSkillsBox();"></span>
                                        <div class="select-pure__options" style="top: 52px;">
                                            <input class="select-pure__autocomplete" placeholder="Search your skills "
                                                   type="text" onkeyup="onSearch(this.value);">
                                        </div>
                                        <i id="btn-dropdown" class="fa fa-arrow-down"></i>

                                    </div>

                                </div>
                            </span>

                        </div>
                        <button class="btn next-btn" id="btn-nextStep">
                            <span>Next step</span>
                        </button>
                    </li>
                    <li>
                        <p class="title">Select your skill's year of experience</p>
                        <div class="input-stage"></div>
                        <button class="btn cal-btn" id="btn-cal">
                            <span>CALCULATE!</span>
                        </button>
                    </li>
                    <!--end register-->
                    <li>
                        <ul id="container-heroes">
                            <div id="ngoai">
                                <img src="assets/pre-arrow.png" onclick="pre()" class="pre" />
                                <div id="trong">

                                </div>
                                <div id="pdfLink">
                                    <a href="#"></a>
                                    <div id="pdf-loader"></div>
                                    <span id="msg-pdf" >

                                        Click to download jobs that are suitable for you</span>
                                </div>
                                <img src="assets/next-arrow.png" onclick="next()" class="next" />
                            </div>

                        </ul>
                    </li>
                    <li>
                        <p class="title">Find jobs with the salary you want </p>
                        <div class="searchBySalary">
                            <input id="salary-from" class="select-pure__autocomplete" placeholder="Input your min salary " type="number" min="100000" max="1000000000">
                            <input id="salary-to" class="select-pure__autocomplete" placeholder="Input your max salary " type="number" min="100000" max="1000000000">
                            <input class="select-pure__autocomplete btnSearchSalary" placeholder="Input your max salary " type="submit">
                        </div>
                        <div  class="searchBySalaryResult">
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Skill Name</th>
                                        <th>Experience level</th>
                                        <th>Salary</th>
                                        <th>Link</th>
                                    </tr>
                                </thead>
                                <tbody id="data">

                                </tbody>
                            </table>

                        </div>
                    </li>
                </ul>
            </div>

        </div>

        <script src="/SalaryRecSystem/js/utils.js"></script>
        <script src="/SalaryRecSystem/js/script.js"></script>
        <script src="/SalaryRecSystem/js/canvasjs.min.js"></script>
        <script src="/SalaryRecSystem/js/user.js"></script>

    </body>
</html>

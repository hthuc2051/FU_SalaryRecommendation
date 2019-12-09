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
        <link rel='stylesheet' type='text/css' media='screen' href='./style.css'>

     
    </head>
    <body>
        <nav class="navbar">
            <div class="nav-back"><img class="icon-back" src="./assets/left-arrow.png"><span class="nav-caption">EXIT</span>
            </div>
            <div class="nav-center">
                <img class="nav-logo" src="./assets/logo2.png" />
                <h3 class="text">Salary recommendation system</h3>
            </div>
            <div class="nav-title col-2 col-sm-3">
                <div class="title">
                    <h2 class="text">Welcome, Glad to see you</h2>
                    <h3 class="text">Nguyen Huy Thuc</h3>
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
                <li id="btn-skill-choice" style="padding-left:8px;" onClick="loadContent(1);">
                    <img src="./assets/choices.png" class="icon">
                </li>
                <li style="padding-left:8px;" onclick="loadContent(2);">
                    <img src="./assets/edit.png" class="icon">
                </li>
                <li onclick="loadContent(3);">
                    <img src="./assets/analysis.png" class="icon">
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
                                            <!--                                            <div class="select-pure__option" data-value="Angular"
                                                                                             onclick="selectSkill('Angular');">Angular
                                                                                        </div>
                                                                                        <div class="select-pure__option " data-value="Nodejs"
                                                                                             onclick="selectSkill('Nodejs');">Node JS
                                                                                        </div>
                                                                                        <div class="select-pure__option" data-value="ReactJS"
                                                                                             onclick="selectSkill('ReactJS');">React Js
                                                                                        </div>
                                                                                        <div class="select-pure__option" data-value="MongoDB"
                                                                                             onclick="selectSkill('MongoDB');">MongoDB
                                                                                        </div>
                                                                                        <div class="select-pure__option " data-value="Java"
                                                                                             onclick="selectSkill('Java');">Java</div>-->
                                        </div>
                                        <i id="btn-dropdown" class="fa fa-arrow-down"></i>

                                    </div>

                                </div>
                            </span>

                        </div>
                        <button class="btn next-btn" onclick="loadContent(2);">
                            <span>Next step</span>
                        </button>
                    </li>
                    <li>
                        <p class="title">Select your skill's year of experience</p>
                        <div class="input-stage"></div>
                        <button class="btn cal-btn" onclick="loadContent(3);">
                            <span>CALCULATE!</span>
                        </button>
                    </li>
                    <!--end register-->
                    <li>
                        <ul id="container-heroes">
                            <div id="ngoai">
                                <img src="assets/pre-arrow.png" onclick="pre()" class="pre" />
                                <div id="trong">
                                    <!-- Lấy max 11  -->
                                    <!-- <div class="testDiv"></div>
                                    <div class="testDiv"></div>
                                    <div class="testDiv"></div>
                                    <div class="testDiv"></div>
                                    <div class="testDiv"></div>
                                    <div class="testDiv"></div>
                                    <div class="testDiv"></div> -->

<!--                                    <div id="chartContainer" class="chart">
                                    </div>-->
                                </div>
                                <img src="assets/next-arrow.png" onclick="next()" class="next" />
                            </div>

                        </ul>
                    </li>
                </ul>
            </div>

        </div>

        <script src="../js/utils.js"></script>
        <script src="./script.js"></script>
        <script src="./canvasjs.min.js"></script>
        <script src="../js/user.js"></script>

    </body>
</html>
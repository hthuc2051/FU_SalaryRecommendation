(function () {

    var homeModel = {
        skillArr: [],
        salaryRecArr: [],
        xmlSkills: null,
        xmlSalaryRecs: null,
        jobsArr: [],

        arrChart: [],
        selectedArr: [],
        selectedRecsArr: [],
    };
    var homeView = {
        init: function () {
            // init 
            homeView.initData(function () {
                document.getElementById("overlay").style.display = "none";
            });

            let btnSkillChoice = document.getElementById("btn-skill-choice");
            btnSkillChoice.addEventListener('click', function () {
                homeView.resetPage();
            });

            let btnPdf = document.getElementById("pdfLink");
            btnPdf.addEventListener('click', function () {
                let msgPdf = document.getElementById("msg-pdf");
                if (typeof (msgPdf) !== 'undefined') {
                    msgPdf.style = "display:none";
                }
                let pdfLoader = document.getElementById("pdf-loader");
                if (typeof (pdfLoader) !== 'undefined') {
                    pdfLoader.style = "display:inline";
                }
                octopus.getPdf(function () {
                    if (typeof (pdfLoader) !== 'undefined') {
                        pdfLoader.style = "display:none";
                    }
                    if (typeof (msgPdf) !== 'undefined') {
                        msgPdf.style = "display:flex";
                    }
                });
            });

//            Trigger not move next step
            let btnNext = document.getElementById("btn-nextStep");
            btnNext.addEventListener('click', function () {
                loadContent(2);
                homeView.resetChart();
            });
            let imgNext = document.getElementById("img-nextStep");
            imgNext.addEventListener('click', function () {
                loadContent(2);
                homeView.resetChart();
            });
            let imgCal = document.getElementById("img-cal");
            imgCal.addEventListener('click', function () {
                document.getElementById("overlay").style.display = "block";
                loadContent(3);
                homeView.calculateSalaryRec(function () {
                    document.getElementById("overlay").style.display = "none";

                });

            });

            let btnCal = document.getElementById("btn-cal");
            btnCal.addEventListener('click', function () {
                document.getElementById("overlay").style.display = "block";
                loadContent(3);
                homeView.calculateSalaryRec(function () {
                    document.getElementById("overlay").style.display = "none";
                });
            });
            let btnSearchSalary = document.getElementsByClassName("btnSearchSalary")[0];
            let dataTag = document.getElementById("data");
            btnSearchSalary.addEventListener('click', function () {
                dataTag.innerHTML = "";
                homeModel.jobsArr = [];
                homeView.getTop20JobBySalary();
            });
        },
        initData: function (callBack) {
            octopus.getSkillsData();
            octopus.getSalaryRecsData();
            callBack();
        },
        parsingXmlSalary: function () {
            let element = document.getElementsByClassName("loading-text");
            if (element !== null) {
                let xmlDocs = octopus.getXmlSalaryRecs();
                let salaryRecs = xmlDocs.childNodes[0];
                if (salaryRecs !== null && salaryRecs.children !== null && salaryRecs.children.length > 0) {
                    for (let i = 0; i < salaryRecs.children.length; i++) {
                        let item = salaryRecs.children[i];
                        let id = item.getElementsByTagName("id")[0].textContent.toString();
                        let skillId = item.getElementsByTagName("skillId")[0].textContent.toString();
                        let skillName = item.getElementsByTagName("skillName")[0].textContent.toString();
                        let expLevel = item.getElementsByTagName("expLevel")[0].textContent.toString();
                        let salary = item.getElementsByTagName("salaryRec")[0].textContent.toString();
                        let salaryRec = {
                            id: id,
                            name: skillName,
                            skillId: skillId,
                            expLevel: expLevel,
                            salary: salary
                        };
                        homeModel.salaryRecArr.push(salaryRec);
                    }
                }
            }
        },
        getArrSummaryPoint: function (salaryJob, hash, index, callBackMethod) {
            let arr = [];
            octopus.getSummaryJob(salaryJob, hash, function (xmlDocs) {
                let summaryJobs = xmlDocs.childNodes[0];
                if (summaryJobs !== null && summaryJobs.children !== null && summaryJobs.children.length > 0) {
                    for (let i = 0; i < summaryJobs.children.length; i++) {
                        let item = summaryJobs.children[i];
                        let noOfJobs = item.getElementsByTagName("noOfJobs")[0].textContent.toString();
                        let salary = item.getElementsByTagName("salary")[0].textContent.toString();
                        let salaryRec = {
                            y: parseInt(noOfJobs),
                            label: parseFloat(salary)
                        };
                        arr.push(salaryRec);
                    }
                }
                arr.sort(function (a, b) {
                    return a.label - b.label;
                });
                arr.forEach(function (item) {
                    item.label = formatMilionNum(item.label);
                });
                callBackMethod(index, arr);
            });
        },
        renderSkills: function () {
            if (document.implementation && document.implementation.createDocument) {
                let xmlDocs = octopus.getXmlSkills();
                let skills = xmlDocs.childNodes[0];
                let select_pure_options = document.getElementsByClassName("select-pure__options");
                if (select_pure_options !== null) {
                    for (let i = 0; i < skills.children.length; i++) {
                        let skill = skills.children[i];
                        let id = skill.getElementsByTagName("id")[0].textContent.toString();
                        let name = skill.getElementsByTagName("name")[0].textContent.toString();
                        let arrExpLevel = [];

                        let tempArr = skill.getElementsByTagName("expLevels");
                        for (var j = 0; j < tempArr.length; j++) {
                            let expLevel = tempArr[j].textContent.toString();
                            arrExpLevel.push(expLevel);
                        }
                        let skillObj = {
                            id: id,
                            name: name,
                            arrExpLevel: arrExpLevel
                        };
                        homeModel.skillArr.push(skillObj);
                        // Create element
                        let item = document.createElement('div');
                        item.innerHTML = name;
                        item.classList.add('select-pure__option');
                        item.setAttribute('data-value', id);
                        item.onclick = function () {
                            var paramId = id;
                            homeView.selectSkill(id);
                        };
                        select_pure_options[0].appendChild(item);
                    }
                }
            }
        },
        calculateSalaryRec: function (callBackMethod) {
            if (homeModel.selectedArr.length > 0) {
                console.log(homeModel.selectedArr);
                for (var i = 0; i < homeModel.selectedArr.length; i++) {
                    // TODO:Check thêm cả expYear nữa
                    let obj = homeModel.salaryRecArr.find(item =>
                        ((item.skillId === homeModel.selectedArr[i].id) && (item.expLevel === homeModel.selectedArr[i].expLevel)));
                    if (obj !== null) {
                        homeModel.arrChart.push(obj);
                    }
                }
            }
//            await homeView.onLoading();
            homeView.renderChart(callBackMethod);
        },
        selectSkill: function (id) {
            let arr = document.getElementsByClassName('select-pure__option');
            let selected_label = document.getElementById('select-pure__label');
            let input_stage = document.getElementsByClassName('input-stage')[0];

            if (arr !== null) {
                for (let i = 0; i < arr.length; i++) {
                    let element = arr[i];
                    let key = element.getAttribute('data-value');
                    let value = element.textContent;
                    if (key === id) {
                        // disable selected
                        element.classList.add('select-pure__option--selected');

                        // create new tag for label selected
                        let newSkillAppend = document.createElement('span');
                        newSkillAppend.classList.add('select-pure__selected-label');
                        newSkillAppend.innerHTML = value;
                        selected_label.appendChild(newSkillAppend);
                        // create remove button
                        let removeBtn = document.createElement('i');
                        removeBtn.classList.add('fa');
                        removeBtn.classList.add('fa-times');
                        removeBtn.classList.add('removeBtn');
                        removeBtn.setAttribute('data-value', id);
                        removeBtn.onclick = function () {
                            var paramId = id; // 
                            homeView.removeSelectedSkill(id);
                        };
                        newSkillAppend.appendChild(removeBtn);

                        // create skill dropdown
                        let dropdown_element = document.createElement('div');
                        dropdown_element.classList.add('app-cover');
                        dropdown_element.setAttribute('id', id);

                        // child of skill dropdown
                        // add checkbox
                        let checkBox = document.createElement('input');
                        checkBox.setAttribute('type', 'checkbox');
                        checkBox.classList.add('options-view-button');

                        // add button div
                        let select_button = document.createElement('div');
                        select_button.classList.add('select-button');
                        select_button.classList.add('brd');

                        // add label
                        let dropdown_label = document.createElement('div');
                        dropdown_label.classList.add('selected-label');
                        dropdown_label.setAttribute('id', 'selected-' + id);

                        //child of label
                        let selected_span = document.createElement('span');
                        selected_span.classList.add('selected-value');
                        selected_span.setAttribute('dropdown-id', id);
                        selected_span.innerHTML = value + ' - Select year of experience';
                        dropdown_label.appendChild(selected_span);

                        select_button.appendChild(dropdown_label);

                        // dropdown icon
                        let icon_div = document.createElement('div');
                        icon_div.classList.add('chevrons');

                        // icon
                        let icon_up = document.createElement('i');
                        icon_up.classList.add('fa');
                        icon_up.classList.add('fa-angle-up');

                        let icon_down = document.createElement('i');
                        icon_down.classList.add('fa');
                        icon_down.classList.add('fa-angle-down');
                        icon_div.appendChild(icon_up);
                        icon_div.appendChild(icon_down);

                        select_button.appendChild(icon_div);
                        // Add options

                        let options = homeView.generateOptions(id, value);
//
                        dropdown_element.appendChild(checkBox);
                        dropdown_element.appendChild(select_button);
                        dropdown_element.appendChild(options);
                        input_stage.appendChild(dropdown_element);
                    }
                }
            }
        },
        generateOptions: function (key, name) {
            let options = document.createElement('div');
            options.setAttribute('id', 'options');
            let arrExpLevel = [];
            homeModel.skillArr.forEach(function (item) {
                if (item.id === key) {
                    arrExpLevel = item.arrExpLevel;
                }
            });
            for (let i = 0; i < arrExpLevel.length; i++) {
                let option = document.createElement('div');
                option.classList.add('option');
                option.onclick = function () {
                    homeView.selectExpLevel(key, name, arrExpLevel[i]);
                };
                let radio_1 = document.createElement('input');
                radio_1.setAttribute('type', 'radio');
                radio_1.setAttribute('name', 'platform');
                radio_1.classList.add('s-c');
                radio_1.classList.add('top');

                let radio_2 = document.createElement('input');
                radio_2.setAttribute('type', 'radio');
                radio_2.setAttribute('name', 'platform');
                radio_2.classList.add('s-c');
                radio_2.classList.add('bottom');

                let img_option = document.createElement('img');
                img_option.src = './assets/quality.png';

                let option_label = document.createElement('span');
                option_label.classList.add('label');
                option_label.innerHTML = homeView.getExpLevelLabel(arrExpLevel[i]);

                option.appendChild(radio_1);
                option.appendChild(radio_2);
                option.appendChild(img_option);
                option.appendChild(option_label);

                options.appendChild(option);
            }
            return options;
        },
        removeSelectedSkill: function (id) {
            console.log(homeModel.selectedArr);
            let arr = document.getElementsByClassName('select-pure__option');
            let selected_label = document.getElementById('select-pure__label');
            let arrBtn = document.getElementsByClassName('removeBtn');
            let arrLabel = document.getElementsByClassName('select-pure__selected-label');
            let input_stage = document.getElementsByClassName('input-stage')[0];
            let yearExp_dropdown = document.getElementById(id);
            if (yearExp_dropdown) {
                input_stage.removeChild(yearExp_dropdown);
            }
            for (let i = 0; i < arr.length; i++) {
                let element = arr[i];
                let key = element.getAttribute('data-value');
                if (key === id) {
                    // disable selected
                    element.classList.remove('select-pure__option--selected');
                    for (let j = 0; j < arrBtn.length; j++) {
                        let labelElement = arrBtn[j];
                        let key = labelElement.getAttribute('data-value');
                        if (key === id) {
                            // remove from list
                            selected_label.removeChild(arrLabel[j]);
                        }
                    }
                }
            }
            if (homeModel.selectedArr.length > 0) {
                for (var i = 0; i < homeModel.selectedArr.length; i++) {
                    let obj = homeModel.selectedArr[i];
                    if (typeof obj !== 'undefined') {
                        if (obj.id === id) {
                            homeModel.selectedArr.splice(i, 1);
                        }
                    }
                }
            }

//            else {
//                homeModel.selectedArr.push(homeModel.selectedArr[0]);
//            }

        },
        selectExpLevel: function (key, name, expLevel) {
            let selected_label = document.getElementById('selected-' + key);
            selected_label.firstElementChild.innerHTML = name + " - " + homeView.getExpLevelLabel(expLevel);
            let obj = {
                id: key,
                skill: name,
                expLevel: expLevel,
            };

            if (homeModel.selectedArr.length > 0) {
                let expLevelItem = homeModel.selectedArr.find(item => (item.id === key));
                if (typeof expLevelItem === 'undefined') {
                    homeModel.selectedArr.push(obj);
                } else {
                    expLevelItem.expLevel = expLevel;
                    console.log('Update explevel');
                }
            } else {
                homeModel.selectedArr.push(obj);
            }
        },
        renderChart: function (callBack) {
            let pdfLink = document.getElementById("pdfLink");
            var arrChart = homeModel.arrChart;
            if (arrChart.length > 0) {
                pdfLink.style = "visibility: inherit;";
                let chartTab = document.getElementById("trong");
                for (var i = 0; i < arrChart.length; i++) {
                    let hashValue = hasingString(arrChart[i].expLevel + arrChart[i].skillId);
                    homeView.getArrSummaryPoint(arrChart[i].salary, hashValue, i, function (index, dataPoints) {

                        // For PDF
                        homeModel.selectedRecsArr.push(arrChart[index].salary + "~" + hashValue);

                        let data = [{
                                type: "column",
                                showInLegend: true,
                                legendText: arrChart[index].name + " - " + arrChart[index].expLevel,
                                legendMarkerColor: "grey",
                                dataPoints: dataPoints,
                            }];
                        let chartDiv = document.createElement('div');
                        chartDiv.classList.add('chart');
                        chartDiv.setAttribute('id', "chartContainer" + arrChart[index].id);
                        chartTab.appendChild(chartDiv);
                        var chart = new CanvasJS.Chart("chartContainer" + arrChart[index].id, {
                            animationEnabled: true,
                            theme: "light2", // "light1", "light2", "dark1", "dark2"
                            title: {
                                text: arrChart[index].name + " salary recommend :" + homeView.formatVietnameseCurrency(parseFloat(arrChart[index].salary)) + "đ"
                            },
                            axisY: {
                                title: "Number of company"
                            },
                            data: data
                        });
                        chart.render();
                    });
                }
            } else {
                pdfLink.style = "visibility: hidden;";

            }
            callBack();
        },
        getTop20JobBySalary: function () {
            let from = parseFloat(document.getElementById("salary-from").value);
            let to = parseFloat(document.getElementById("salary-to").value);


            if (from > 0 && from < to) {
                octopus.getSalaryRange(from, to, function (xmlDocs) {
                    let jobs = xmlDocs.childNodes[0];
                    for (let i = 0; i < jobs.children.length; i++) {
                        let job = jobs.children[i];
                        let skillName = job.getElementsByTagName("skillName")[0].textContent.toString();
                        let salary = job.getElementsByTagName("salary")[0].textContent.toString();
                        let expLevel = job.getElementsByTagName("expLevel")[0].textContent.toString();
                        let link = job.getElementsByTagName("link")[0].textContent.toString();

                        let obj = {
                            skillName: skillName,
                            salary: salary,
                            expLevel: expLevel,
                            link: link
                        };
                        homeModel.jobsArr.push(obj);
                    }
                    homeView.renderListTop20Jobs();
                });
            } else {
                alert("Salary 1 must less than salary 2 !");
            }
        },
        renderListTop20Jobs: function () {
            let arr = homeModel.jobsArr;
            for (let i = 0; i < arr.length; i++) {
                var row = document.createElement("tr");
                var count = document.createElement("td");
                var skillName = document.createElement("td");
                var salary = document.createElement("td");
                var expLevel = document.createElement("td");
                var link = document.createElement("td");
                var a = document.createElement("a");

                count.innerHTML = (i + 1);
                skillName.innerHTML = arr[i].skillName;
                salary.innerHTML = homeView.formatVietnameseCurrency(parseFloat(arr[i].salary));
                expLevel.innerHTML = arr[i].expLevel;

                a.innerHTML = arr[i].link;
                a.setAttribute('href', arr[i].link);

                link.appendChild(a);
                row.appendChild(count);
                row.appendChild(skillName);
                row.appendChild(expLevel);
                row.appendChild(salary);
                row.appendChild(link);
                document.getElementById("data").appendChild(row);
            }
        },
        resetChart: function () {
            let chartStage = document.getElementById('trong');
            chartStage.innerHTML = '';
            homeModel.arrChart = [];
        },
        onLoading: function () {
            document.getElementById("overlay").style.display = "block";
        },
        resetPage: async function () {
            location.reload(true);
        },
        formatVietnameseCurrency: function (price)
        {
            return new Intl.NumberFormat().format(price)
        },
        getExpLevelLabel: function (expLevelStr)
        {
            switch (expLevelStr) {
                case 'SA':
                    return ' Software Architecture [ >10 years expericence ]';
                case 'TL':
                    return ' Technical Lead [ 6-10 years expericence ]';
                case 'Senior':
                    return ' Senior developer [ 3-6 years expericence ]';
                case 'Dev':
                    return ' Developer [ 1-3 years expericence ]';
                case 'Junior':
                    return ' Junior developer [ 0.5-1 year expericence ]';
                case 'Fresher':
                    return ' Fresher developer [ 0-0.5 year expericence ]';
                default:
            }
            return new Intl.NumberFormat().format(price)
        },
    };

    var octopus = {
        init: async function () {
            await homeView.init();
        },
        getSkillsData: function () {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/skills/skillsItems";
            sendGetRequest("GET", url, null, function (response) {
                var xmlDoc = response.responseXML;
                homeModel.xmlSkills = xmlDoc;
                homeView.renderSkills();
            });
        },
        getSalaryRecsData: function () {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/salaryRecs";
            sendGetRequest("GET", url, null, function (response) {
                var xmlDoc = response.responseXML;
                homeModel.xmlSalaryRecs = xmlDoc;
                homeView.parsingXmlSalary();
            });
        },
        getSummaryJob: function (salaryJob, hash, callBackMethod) {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/SummaryJobs/" + hash + "/" + salaryJob;
            sendGetRequest("GET", url, null, function (response) {
                var xmlDoc = response.responseXML;
                callBackMethod(xmlDoc);
            });
        },
        getPdf: function (callbackMethod) {
            var url = "/SalaryRecSystem/PdfServlet";
            var param = {
                skillSelectedArr: homeModel.selectedRecsArr
            };
            sendGetRequest("GET", url, param, function (response) {
                callbackMethod();
                window.open(response.responseURL);
            });
        },
        getSalaryRange: function (from, to, callBackMethod) {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/Jobs/salary/" + from + "/" + to;
            sendGetRequest("GET", url, null, function (response) {
                var xmlDoc = response.responseXML;
                callBackMethod(xmlDoc);
            });
        },
        getXmlSkills: function () {
            return homeModel.xmlSkills;
        },
        getXmlSalaryRecs: function () {
            return homeModel.xmlSalaryRecs;
        },
    };

    octopus.init();
}());

(function () {

    var adminModel = {
        skillArr: [],
        salaryRecArr: [],
        curSize: 10,
        xmlSkills: null,
        xmlSalaryRecs: null,
        xmlJobs: null,
    };
    var adminView = {
        init: function () {
            // init 
            octopus.getSkillsData(0, 50, function (xmlDocs) {
                adminView.parseXMLSkills(xmlDocs);
            });
            let btnLoadMore = document.getElementById("btnLoadMore");
            btnLoadMore.addEventListener('click', function () {
                adminView.loadMore();
            });
        },
        parseXMLSkills: function (xmlDocs) {
            if (xmlDocs !== null) {
                let skills = xmlDocs.childNodes[0];
                for (let i = 0; i < skills.children.length; i++) {
                    let skill = skills.children[i];
                    let active = skill.getElementsByTagName("active")[0].textContent.toString();
                    let id = skill.getElementsByTagName("id")[0].textContent.toString();
                    let name = skill.getElementsByTagName("name")[0].textContent.toString();
                    let type = skill.getElementsByTagName("type")[0].textContent.toString();

                    let obj = {
                        id: id,
                        name: name,
                        type: type,
                        active: active,
                    };
                    adminModel.skillArr.push(obj);
                }
                adminView.renderSkills(adminModel.curSize);
            }
        },
        renderSkills: function (length) {
            let skillsArr = adminModel.skillArr;
            for (let i = length - 10; i < length; i++) {
                var row = document.createElement("tr");
                var id = document.createElement("td");
                var name = document.createElement("td");
                var type = document.createElement("td");
                var active = document.createElement("td");
                var updateTd = document.createElement("td");
                var updateBtn = document.createElement("button");
                updateBtn.addEventListener('click', function () {
                    let inputId = document.getElementsByName("txtId");
                    let inputName = document.getElementsByName("txtSkillName");
                    let inputType = document.getElementsByName("txtSkillType");
                    inputId[0].value = skillsArr[i].id;
                    inputName[0].value = skillsArr[i].name;
                    inputType[0].value = skillsArr[i].type;
                });
                updateBtn.innerHTML = "Edit";
                updateTd.appendChild(updateBtn);

                var deleteTd = document.createElement("td");
                var deleteBtn = document.createElement("button");
                deleteBtn.addEventListener('click', function () {
                    octopus.deleteSkill(skillsArr[i].id);
                });
                deleteBtn.innerHTML = "Delete";
                deleteTd.appendChild(deleteBtn);

                id.innerHTML = skillsArr[i].id;
                name.innerHTML = skillsArr[i].name;
                type.innerHTML = skillsArr[i].type;
                active.innerHTML = skillsArr[i].active;

                row.appendChild(id);
                row.appendChild(name);
                row.appendChild(type);
                row.appendChild(active);
                row.appendChild(updateTd);
                row.appendChild(deleteTd);
                document.getElementById("data").appendChild(row);
            }
        },
        loadMore: function () {
            if (adminModel.curSize < adminModel.skillArr.length) {
                adminModel.curSize += 10;
                adminView.renderSkills(adminModel.curSize);
            }
        }
    };

    var octopus = {
        init: async function () {
            await adminView.init();
        },
        getSkillsData: function (from, to, callbackMethod) {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/skills";
            let newUrl = url + "/" + from + "/" + to;
            sendGetRequest("GET", newUrl, null, function (response) {
                // render list location
                callbackMethod(response.responseXML);
            });
        },
        getSalaryRecsData: function () {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/salaryRecs";
            sendGetRequest(url, null, function (response) {
                var xmlDoc = response.responseXML;
                adminModel.xmlSalaryRecs = xmlDoc;
            });
        },
        deleteSkill: function (id) {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/skills/" + id;
            sendGetRequest("DELETE", url, null, function () {
                alert("Delete successfully");
                location.reload();
            });
        },
        getJobData: function (salaryJob, hash, callBackMethod) {

        },
        getXmlSkills: function () {
            return adminModel.xmlSkills;
        },
        getXmlSalaryRecs: function () {
            return adminModel.xmlSalaryRecs;
        },
    };

    octopus.init();
}());

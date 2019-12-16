
(function () {

    var adminModel = {
        jobArr: [],
        curSize: 10,
        xmlJobs: null,
    };
    var adminView = {
        init: function () {
            // init 
            octopus.getJobsData(0, 50, function (xmlDocs) {
                adminView.parsexmlJobs(xmlDocs);
            });
            let btnLoadMore = document.getElementById("btnLoadMore");
            btnLoadMore.addEventListener('click', function () {
                adminView.loadMore();
            });
        },
        parsexmlJobs: function (xmlDocs) {
            if (xmlDocs !== null) {
                let Jobs = xmlDocs.childNodes[0];
                for (let i = 0; i < Jobs.children.length; i++) {
                    let job = Jobs.children[i];
                    let active = job.getElementsByTagName("active")[0].textContent.toString();
                    let id = job.getElementsByTagName("id")[0].textContent.toString();
                    let link = job.getElementsByTagName("link")[0].textContent.toString();
                    let expLevel = job.getElementsByTagName("expLevel")[0].textContent.toString();
                    let salary = job.getElementsByTagName("salary")[0].textContent.toString();

                    let obj = {
                        id: id,
                        link: link,
                        expLevel: expLevel,
                        salary: salary,
                        active: active,
                    };
                    adminModel.jobArr.push(obj);
                }
                adminView.renderJobs(adminModel.curSize);
            }
        },
        renderJobs: function (length) {
            let jobsArr = adminModel.jobArr;
            for (let i = length - 10; i < length; i++) {
                var row = document.createElement("tr");
                var id = document.createElement("td");
                var txtJobLevel = document.createElement("td");
                var txtJobSalary = document.createElement("td");
                var active = document.createElement("td");
                var updateTd = document.createElement("td");
                var updateBtn = document.createElement("button");
                updateBtn.addEventListener('click', function () {
                    let inputId = document.getElementsByName("txtId");
                    let txtJobLevel = document.getElementsByName("txtJobLevel");
                    let txtJobLink = document.getElementsByName("txtJobLink");
                    let txtJobSalary = document.getElementsByName("txtJobSalary");
                    inputId[0].value = jobsArr[i].id;
                    txtJobLevel[0].value = jobsArr[i].expLevel;
                    txtJobLink[0].value = jobsArr[i].link;
                    txtJobSalary[0].value = parseFloat(jobsArr[i].salary);
                });
                updateBtn.innerHTML = "Edit";
                updateTd.appendChild(updateBtn);

                var deleteTd = document.createElement("td");
                var deleteBtn = document.createElement("button");
                deleteBtn.addEventListener('click', function () {
                    octopus.deleteJob(jobsArr[i].id);
                });
                deleteBtn.innerHTML = "Delete";
                deleteTd.appendChild(deleteBtn);

                id.innerHTML = jobsArr[i].id;
                txtJobLevel.innerHTML = jobsArr[i].expLevel;
                txtJobSalary.innerHTML = jobsArr[i].salary;
                active.innerHTML = jobsArr[i].active;

                row.appendChild(id);
                row.appendChild(txtJobLevel);
                row.appendChild(txtJobSalary);
                row.appendChild(active);
                row.appendChild(updateTd);
                row.appendChild(deleteTd);
                document.getElementById("data").appendChild(row);
            }
        },
        loadMore: function () {
            if ((adminModel.jobArr.length - adminModel.curSize) < 50) {
                octopus.getJobsData(adminModel.jobArr.length, adminModel.jobArr.length + 50, function (xmlDocs) {
                });
            }
            adminModel.curSize += 10;
            adminView.renderJobs(adminModel.curSize);
        }
    };

    var octopus = {
        init: async function () {
            await adminView.init();
        },
        getJobsData: function (from, to, callbackMethod) {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/Jobs";
            let newUrl = url + "/" + from + "/" + to;
            sendGetRequest("GET", newUrl, null, function (response) {
                // render list location
                callbackMethod(response.responseXML);
            });
        },
        deleteJob: function (id) {
            var url = "http://localhost:8084/SalaryRecSystem/webresources/Jobs/" + id;
            sendGetRequest("DELETE", url, null, function () {
                alert("Delete successfully");
                location.reload();
            });
        },
        getxmlJobs: function () {
            return adminModel.xmlJobs;
        },
    };

    octopus.init();
}());

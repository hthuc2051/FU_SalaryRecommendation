
function loadXML(filePath) {
    xmlHttp = getXMLHttpObject();
    if (xmlHttp == null) {
        alert('Browser not supprt AJAX');
        return;
    }
    xmlHttp.open("GET", filePath, false);
    xmlHttp.send(null);
    return xmlHttp.responseXML;
}


function getXMLHttpObject() {
    var xmlHttp = null;
    try {
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        try {
            new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}

function sendGetRequest(url, param, responseCallback) {
    var xhttp = getXMLHttpObject();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            responseCallback(this);
        }
    };
    if (param !== null) {
        url = url + "?" + getParam(param);
    }
    xhttp.open("GET", url, true);
    xhttp.send();
}


function getParam(param) {
    var paramsStr = "";

    for (var prop in param) {
        if (isArray(param[prop])) {
            paramsStr = paramsStr + getParamArray(prop, param[prop]);
        } else {
            paramsStr = paramsStr + prop + "=" + param[prop] + "&";
        }
    }

    if (paramsStr[paramsStr.length - 1] === '&') {
        paramsStr = paramsStr.substring(0, paramsStr.length - 1);
    }

    return paramsStr;
}

function getParamArray(key, arrayValue) {
    var paramArray = "";

    arrayValue.forEach(function (item, index) {
        paramArray = paramArray + key + "=" + item + "&";
    });

    return paramArray;
}

function isArray(value) {
    return value && typeof value === 'object' && value.constructor === Array;
}

function formatMilionNum (num) {

    // Nine Zeroes for Billions
    return Math.abs(Number(num)) >= 1.0e+9

    ? Math.abs(Number(num)) / 1.0e+9 + "B"
    // Six Zeroes for Millions 
    : Math.abs(Number(num)) >= 1.0e+6

    ? Math.abs(Number(num)) / 1.0e+6 + "M"
    // Three Zeroes for Thousands
    : Math.abs(Number(num)) >= 1.0e+3

    ? Math.abs(Number(num)) / 1.0e+3 + "K"

    : Math.abs(Number(num));
}

function hasingString(s) {
        let mod = 1000000007;
        let base = 30757; // random prime number
        let hasValue = 0;
        for (let i = 0; i < s.length; i++) {
            hasValue =  ((hasValue * base +  s.charCodeAt(i)) % mod);
        }
        console.log(hasValue);
        return hasValue;
    }
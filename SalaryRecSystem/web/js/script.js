



var isFinished = false;

function loadContent(i) {
    var content = document.getElementsByClassName("insinde-content");
    var icon = document.getElementsByClassName("icon");
    if (i === 1) {
        content[0].style.transform = "translateX(0px)";
        icon[0].style.filter = "opacity(100%)";
        icon[1].style.filter = "opacity(50%)";
        icon[2].style.filter = "opacity(50%)";
    } else if (i === 2) {
        content[0].style.transform = "translateX(-1190px)";
        icon[0].style.filter = "opacity(50%)";
        icon[1].style.filter = "opacity(100%)";
        icon[2].style.filter = "opacity(50%)";

    } else if (i === 3) {
        content[0].style.transform = "translateX(-2380px)";
        icon[0].style.filter = "opacity(50%)";
        icon[1].style.filter = "opacity(50%)";
        icon[2].style.filter = "opacity(100%)";
        isFinished = true;
    }
}

function calculate() {

}

function openSkillsBox() {
    let elementSkills = document.getElementById("selected-skills");
    let elementBtnDropDown = document.getElementById("btn-dropdown");
    if (!elementSkills.classList.contains('select-pure__select--opened')) {
        elementSkills.classList.add('select-pure__select--opened');
        elementBtnDropDown.style.transform = 'rotate(180deg)';
    } else {
        elementSkills.classList.remove('select-pure__select--opened');
        elementBtnDropDown.style.transform = 'rotate(0)';
    }
}

//async function selectSkill(id) {
//    let arr = document.getElementsByClassName('select-pure__option');
//    let selected_label = document.getElementById('select-pure__label');
//    let input_stage = document.getElementsByClassName('input-stage')[0];
//
//    if (arr !== null) {
//        for (let i = 0; i < arr.length; i++) {
//            let element = arr[i];
//            let key = element.getAttribute('data-value');
//            let value = element.textContent;
//            if (key === id) {
//                // disable selected
//                element.classList.add('select-pure__option--selected');
//
//                // create new tag for label selected
//                let newSkillAppend = document.createElement('span');
//                newSkillAppend.classList.add('select-pure__selected-label');
//                newSkillAppend.innerHTML = value;
//                selected_label.appendChild(newSkillAppend);
//                // create remove button
//                let removeBtn = document.createElement('i');
//                removeBtn.classList.add('fa');
//                removeBtn.classList.add('fa-times');
//                removeBtn.classList.add('removeBtn');
//                removeBtn.setAttribute('data-value', id);
//                removeBtn.onclick = function () {
//                    var paramId = id; // Méo biết vì sao mà truyền id vô k đc mà tạo mới lại đc ! Câu hỏi cần giải đáp :(
//                    removeSelectedSkill(id);
//                };
//                newSkillAppend.appendChild(removeBtn);
//
//                // create skill dropdown
//                let dropdown_element = document.createElement('div');
//                dropdown_element.classList.add('app-cover');
//                dropdown_element.setAttribute('id', id);
//
//                // child of skill dropdown
//                // add checkbox
//                let checkBox = document.createElement('input');
//                checkBox.setAttribute('type', 'checkbox');
//                checkBox.classList.add('options-view-button');
//
//                // add button div
//                let select_button = document.createElement('div');
//                select_button.classList.add('select-button');
//                select_button.classList.add('brd');
//
//                // add label
//                let dropdown_label = document.createElement('div');
//                dropdown_label.classList.add('selected-label');
//                dropdown_label.setAttribute('id', 'selected-' + id);
//
//                //child of label
//                let selected_span = document.createElement('span');
//                selected_span.classList.add('selected-value');
//                selected_span.setAttribute('dropdown-id', id);
//                selected_span.innerHTML = value + ' - Select year of experience';
//                dropdown_label.appendChild(selected_span);
//
//                select_button.appendChild(dropdown_label);
//
//                // dropdown icon
//                let icon_div = document.createElement('div');
//                icon_div.classList.add('chevrons');
//
//                // icon
//                let icon_up = document.createElement('i');
//                icon_up.classList.add('fa');
//                icon_up.classList.add('fa-angle-up');
//
//                let icon_down = document.createElement('i');
//                icon_down.classList.add('fa');
//                icon_down.classList.add('fa-angle-down');
//                icon_div.appendChild(icon_up);
//                icon_div.appendChild(icon_down);
//
//                select_button.appendChild(icon_div);
//                // Add options
//
//                let options = await generateOptions(id, value);
//
//                dropdown_element.appendChild(checkBox);
//                dropdown_element.appendChild(select_button);
//                dropdown_element.appendChild(options);
//                input_stage.appendChild(dropdown_element);
//            }
//        }
//    }
//}
//async function generateOptions(key, name) {
//    let options = document.createElement('div');
//    options.setAttribute('id', 'options');
//    for (let i = 0; i < expLevelArr.length; i++) {
//        let option = document.createElement('div');
//        option.classList.add('option');
//        option.onclick = function () {
//            selectExpLevel(key, name, expLevelArr[i]);
//        };
//        let radio_1 = document.createElement('input');
//        radio_1.setAttribute('type', 'radio');
//        radio_1.setAttribute('name', 'platform');
//        radio_1.classList.add('s-c');
//        radio_1.classList.add('top');
//
//        let radio_2 = document.createElement('input');
//        radio_2.setAttribute('type', 'radio');
//        radio_2.setAttribute('name', 'platform');
//        radio_2.classList.add('s-c');
//        radio_2.classList.add('bottom');
//
//        let img_option = document.createElement('img');
//        img_option.src = './assets/quality.png';
//
//        let option_label = document.createElement('span');
//        option_label.classList.add('label');
//        option_label.innerHTML = expLevelArr[i] + ' year';
//
//        option.appendChild(radio_1);
//        option.appendChild(radio_2);
//        option.appendChild(img_option);
//        option.appendChild(option_label);
//
//        options.appendChild(option);
//    }
//    return options;
//}
//function removeSelectedSkill(id) {
//    console.log(id);
//    let arr = document.getElementsByClassName('select-pure__option');
//    let selected_label = document.getElementById('select-pure__label');
//    let arrBtn = document.getElementsByClassName('removeBtn');
//    let arrLabel = document.getElementsByClassName('select-pure__selected-label');
//    let input_stage = document.getElementsByClassName('input-stage')[0];
//    let yearExp_dropdown = document.getElementById(id);
//    if (yearExp_dropdown) {
//        input_stage.removeChild(yearExp_dropdown);
//    }
//    for (let i = 0; i < arr.length; i++) {
//        let element = arr[i];
//        let key = element.getAttribute('data-value');
//        if (key === id) {
//            // disable selected
//            element.classList.remove('select-pure__option--selected');
//            for (let j = 0; j < arrBtn.length; j++) {
//                let labelElement = arrBtn[j];
//                let key = labelElement.getAttribute('data-value');
//                if (key === id) {
//                    // remove from list
//                    selected_label.removeChild(arrLabel[j]);
//                }
//            }
//        }
//    }
//    if (selectedArr.length > 0) {
//        for (var i = 0; i < selectedArr.length; i++) {
//            if (selectedArr[i].id === id) {
//                selectedArr.pop(selectedArr[i]);
//            }
//        }
//    } else {
//        selectedArr.push(selectedArr[0]);
//    }
//}

function onSearch(searchValue) {
    let arr = document.getElementsByClassName('select-pure__option');
    for (let i = 0; i < arr.length; i++) {
        let element = arr[i];
        let content = element.textContent.toUpperCase();
        if (content.includes(searchValue.toUpperCase())) {
            element.classList.remove('select-pure__option--hidden');
        } else {
            element.classList.add('select-pure__option--hidden');
        }
    }
};

//function selectExpLevel(key, name, expLevel) {
//    let options = document.getElementById('options');
//    console.log(options);
//    let selected_label = document.getElementById('selected-' + key);
//    selected_label.firstElementChild.innerHTML = name + " - " + yearInput + " years of exp";
//    let obj = {
//        id: key,
//        skill: name,
//        expLevel: expLevel,
//    };
//    if (selectedArr.length > 0) {
//        for (var i = 0; i < selectedArr.length; i++) {
//            if (selectedArr[i].id !== obj.id) {
//                selectedArr.push(obj);
//            }
//        }
//    } else {
//        selectedArr.push(obj);
//    }
//
//    console.log(obj);
//    // var radios = document.getElementsByName('platform');
//    // for (var i = 0, length = radios.length; i < length; i++) {
//    //     if (radios[i].checked) {
//    //         // do whatever you want with the checked radio
//    //         // alert(radios[i].value);
//    //         // only one radio can be logically checked, don't check the rest
//    //         break;
//    //     }
//    // }
//}

var pos = 0; // vị trí hiện hành !
var cur = 0;
function pre() {
    if (pos == 0)
        return;
    pos = pos + 1350;
    cur--;
    var back = document.getElementById("trong");
    back.style.transform = "translateX(" + pos + "px)";
    back.style.transition = "all 0.5s";

}
function next() {
    if (pos === -6750)
        return;
    cur++;
    pos = pos - 1350;
    var go = document.getElementById("trong");
    go.style.transform = "translateX(" + pos + "px)";
    go.style.transition = "all 0.5s";

}

//function on() {
//    document.getElementById("overlay").style.display = "block";
//}
//
//function off() {
//    document.getElementById("overlay").style.display = "none";
//}
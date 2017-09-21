// 引入外部js
// 方式1
document.scripts[0].src = "../static/js/cfcasip.min.js";

// 方式2
// document.write('<script language="javascript" src="../static/js/cfcasip.min.js"></script>');

var completeKeyboard = null;
var numberKeyboard = null;


function doneCallback(sipBoxId) {
    log(sipBoxId + "  done!");
}
function inputChangeCallback(sipBoxId, type, length) {
    log(sipBoxId + "  " + (type == 1 ? "INSERT" : "DELETE") + " currentLength:" + length);
}


function initInput() {
    log("userAgent: " + window.navigator.userAgent);
    initCompleteKeyboard();
    initNumberKeyboard();
    initSipBoxComplete("SIPBox1");
    initSipBoxNum("SIPBox3");
}

function initCompleteKeyboard() {
    if (completeKeyboard == null) {
        completeKeyboard = new CFCAKeyboard(KEYBOARD_TYPE_COMPLETE);
    }
    completeKeyboard.bindInputBox("SIPBox1");
    completeKeyboard.setDoneCallback(doneCallback);
    completeKeyboard.setInputChangeCallback(inputChangeCallback);
    completeKeyboard.hideKeyboard();
}

function initSipBoxComplete(sipboxId) {
    var sipBox = document.getElementById(sipboxId);
    setUpEvent(sipBox, "focus", function (event) {
        sipBox.blur();
        completeKeyboard.bindInputBox(sipboxId);
        var serverRandom = "MTIzNDU2Nzg5MDk4NzY1NA=="; // 写死就行
        if (CFCA_OK != completeKeyboard.setServerRandom(serverRandom, sipboxId)) alert("setServerRandom error");
        if (numberKeyboard.isShowing()) {
            numberKeyboard.hideKeyboard();
        }
        completeKeyboard.showKeyboard();
    });
}

function initNumberKeyboard() {
    if (numberKeyboard == null) {
        numberKeyboard = new CFCAKeyboard(KEYBOARD_TYPE_NUMBER);
    }
    numberKeyboard.bindInputBox("SIPBox3");
    numberKeyboard.setDoneCallback(doneCallback);
    numberKeyboard.setInputChangeCallback(inputChangeCallback);
    numberKeyboard.hideKeyboard();
}


function initSipBoxNum(sipboxId) {
    var sipBox = document.getElementById(sipboxId);
    setUpEvent(sipBox, "focus", function (event) {
        sipBox.blur();
        numberKeyboard.bindInputBox(sipboxId);
        var serverRandom = "MTIzNDU2Nzg5MDk4NzY1NA==";
        if (CFCA_OK != numberKeyboard.setServerRandom(serverRandom, sipboxId)) alert("setServerRandom error");
        if (completeKeyboard.isShowing()) {
            completeKeyboard.hideKeyboard();
        }
        numberKeyboard.showKeyboard();
    });
}


/**
 * 初始化属性
 * @param sipboxId          绑定的输入框ID   （必填）
 * @param completeOrNum     (int) 0 全键盘 1 数字键盘 （必填）
 * @param isEncrypted       (int)是否进行加密 0 加密 1 不加密  （默认为0）
 * @param minLength         (int)最小输入长度 （默认为6）
 * @param maxLength         (int)最大输入长度 （默认为8）
 * @param randomType        (int)乱序类型 0 不乱序 1 数字乱序 2 全乱序 （默认为0）
 * @param matchRegex        (string)匹配正则表达式，为空即不检测是否匹配
 */
function setProperty(sipboxId, completeOrNum, randomType, matchRegex, isEncrypted, minLength, maxLength) {

    console.log(sipboxId);
    console.log(completeOrNum);
    console.log(randomType);
    console.log(isEncrypted);
    console.log(minLength);
    console.log(maxLength);
    console.log(matchRegex);

    var keyboard = completeOrNum === 0 ? completeKeyboard : numberKeyboard;
    keyboard.bindInputBox(sipboxId);
    // 服务端随机数，需加载页面时传入(需与服务端jar取同一个) 无须修改
    var serverRandom = "MTIzNDU2Nzg5MDk4NzY1NA==";
    if (CFCA_OK != keyboard.setServerRandom(serverRandom, sipboxId)) alert("setServerRandom error");
    // 加密类型 sm2
    var cipherType = CIPHER_TYPE_SM2;
    if (CFCA_OK != keyboard.setCipherType(cipherType, sipboxId)) alert("setCipherType error");
    // 输出类型 原文加密输出（默认）
    var outputType = OUTPUT_TYPE_ORIGINAL;
    if (CFCA_OK != keyboard.setOutputType(outputType, sipboxId)) alert("setOutputType error");

    if (isEncrypted === 1) {
        if (CFCA_OK != keyboard.setEncryptState(false, sipboxId)) alert("setEncryptState error");
    }
    if (minLength !== 6) {
        if (CFCA_OK != keyboard.setMinLength(minLength, sipboxId)) alert("setMinLength error");
    }
    if (maxLength !== 8) {
        if (CFCA_OK != keyboard.setMaxLength(maxLength, sipboxId)) alert("setMaxLength error");
    }
    // 是否乱序
    if (CFCA_OK != keyboard.setRandomType(randomType, sipboxId)) alert("setRandomType error");

    if (matchRegex != null && matchRegex !=''){
        if (CFCA_OK != keyboard.setMatchRegex(matchRegex, sipboxId)) alert("setMatchRegex error");
    }
}

/**
 * 获取加密结果 根据需求自行修改
 * @param sipboxId
 * @param completeOrNum     (int) 0 全键盘 1 数字键盘 （必填）
 */
function getEncrypt(sipboxId, completeOrNum) {
    var keyboard = completeOrNum === 1 ? completeKeyboard : numberKeyboard;
    var resultTextarea = document.getElementById("encryptedResult");
    resultTextarea.value = "";
    var encryptedInputValue = keyboard.getEncryptedInputValue(sipboxId);
    alert(encryptedInputValue)
    var errorCode = keyboard.getErrorCode(sipboxId).toString(16);
    if (errorCode != CFCA_OK) {
        resultTextarea.value += "加密输入数据错误: 0x" + errorCode + "\n";
        return;
    } else {
        resultTextarea.value += "加密输入数据: " + encryptedInputValue + "\n";
    }
    var encryptedClientRandom = keyboard.getEncryptedClientRandom(sipboxId);
    alert(encryptedClientRandom)
    errorCode = keyboard.getErrorCode(sipboxId).toString(16);
    if (errorCode != CFCA_OK) {
        resultTextarea.value += "加密客户端随机数错误: 0x" + errorCode + "\n";
        return;
    } else {
        resultTextarea.value += "加密客户端随机数: " + encryptedClientRandom + "\n";
    }

    return [encryptedInputValue, encryptedClientRandom];
}


// 获取版本号
function getVersion() {
    alert("Version: " + getCFCAKeyboardVersion());
}

// 点击空白隐藏键盘事件
function setUpEvent(elem, eventType, handler) {
    return (elem.attachEvent ? elem.attachEvent("on" + eventType, handler)
        : ((elem.addEventListener) ? elem.addEventListener(eventType, handler, false) : null));
}

setUpEvent(document, "touchstart", function (e) {
    var elem = e.srcElement || e.target;
    var noNeedHideIds = ["CompleteKeyboard", "NumberKeyboard",
        "SIPBox1", "SIPBox3",
        "clearSIPBox1", "clearSIPBox3",
        "getSIPBox1Value", "getSIPBox3Value",
        "checkInputValueMatch", "getVersion"
    ];
    while (elem) {
        if (noNeedHideIds.indexOf(elem.id) !== -1) {
            return;
        }
        elem = elem.parentNode;
    }
    completeKeyboard.hideKeyboard();
    numberKeyboard.hideKeyboard();
});

// 检测两个输入框内容是否相等
function checkEqual(sipboxId1, sipboxId2) {
    if (completeKeyboard.checkInputValueMatch(sipboxId1, sipboxId2)) {
        alert('SIPBox1与SIPBox3输入内容一致!');
    } else {
        alert('SIPBox1与SIPBox3输入内容不一致!');
    }
}


function getKeyboard(sipboxId) {
    if (sipboxId === "SIPBox1") {
        return completeKeyboard;
    } else {
        return numberKeyboard;
    }
}


function clearInput(sipboxId) {
    var keyboard = getKeyboard(sipboxId);
    keyboard.clearInputValue(sipboxId);
}


function checkRegex(sipboxId) {
    if (completeKeyboard.checkMatchRegex(sipboxId)) {
        alert("正则表达式匹配");
    } else {
        alert("正则表达式不匹配");
    }
}

function checkEqual(sipboxId1, sipboxId2) {
    if (completeKeyboard.checkInputValueMatch(sipboxId1, sipboxId2)) {
        alert('SIPBox1与SIPBox3输入内容一致!');
    } else {
        alert('SIPBox1与SIPBox3输入内容不一致!');
    }
}
// 方式1
document.scripts[0].src = "../static/js/cfcasip.min.js";

function log(msg) {
    // var p = document.getElementById("log");
    var tmp = "";
    for (var i = 0; i < msg.length; i += 30) {
        tmp += msg.substr(i, 30) + "\n";
    }
    console.log(tmp);
    alert(tmp)
    // p.innerHTML = tmp + p.innerHTML;
}

var completeKeyboard = null;
var numberKeyboard = null;
// 设置完成键回调
function doneCallback(sipBoxId) {
    console.log(sipBoxId + "  done!");
}

//.设置输入框内容发生变动回调
function inputChangeCallback(sipBoxId, type, length) {
    console.log(sipBoxId + "  " + (type == 1 ? "INSERT" : "DELETE") + " currentLength:" + length);
}

// 初始化全键盘
function initInputComplete(sipBoxId) {
    alert(sipBoxId)
    log("userAgent: " + window.navigator.userAgent);
    initCompleteKeyboard(sipBoxId);
    initSipBoxComplete(sipBoxId);
}

// 初始化数字键盘
function initInputNumber(sipBoxId) {
    log("userAgent: " + window.navigator.userAgent);
    initNumberKeyboard(sipBoxId);
    initSipBoxNum(sipBoxId);
}


function initCompleteKeyboard(sipBoxId) {
    alert(sipBoxId)
    debugger;
    if (completeKeyboard == null) {
        alert(sipBoxId)
        completeKeyboard = new CFCAKeyboard(KEYBOARD_TYPE_COMPLETE);
    }
    completeKeyboard.bindInputBox(sipBoxId);
    completeKeyboard.setDoneCallback(doneCallback);
    completeKeyboard.setInputChangeCallback(inputChangeCallback);
    completeKeyboard.hideKeyboard();
    alert(sipBoxId)
}


function initNumberKeyboard(sipBoxId) {
    if (numberKeyboard == null) {
        numberKeyboard = new CFCAKeyboard(KEYBOARD_TYPE_NUMBER);
    }
    numberKeyboard.bindInputBox(sipBoxId);
    numberKeyboard.setDoneCallback(doneCallback);
    numberKeyboard.setInputChangeCallback(inputChangeCallback);
    numberKeyboard.hideKeyboard();
}


function initSipBoxComplete(sipboxId) {
    var sipBox = document.getElementById(sipboxId);
    setUpEvent(sipBox, "focus", function (event) {
        sipBox.blur();
        completeKeyboard.bindInputBox(sipboxId);
        var serverRandom = document.getElementById("serverRandom").value; // 写死就行 TODO
        if (CFCA_OK != completeKeyboard.setServerRandom(serverRandom, sipboxId)) alert("setServerRandom error");
        if (numberKeyboard.isShowing()) {
            numberKeyboard.hideKeyboard();
        }
        completeKeyboard.showKeyboard();
    });
}

function initSipBoxNum(sipboxId) {
    var sipBox = document.getElementById(sipboxId);
    setUpEvent(sipBox, "focus", function (event) {
        sipBox.blur();
        numberKeyboard.bindInputBox(sipboxId);
        var serverRandom = document.getElementById("serverRandom").value; // 写死就行 TODO
        if (CFCA_OK != numberKeyboard.setServerRandom(serverRandom, sipboxId)) alert("setServerRandom error");
        if (completeKeyboard.isShowing()) {
            completeKeyboard.hideKeyboard();
        }
        numberKeyboard.showKeyboard();
    });
}

/**
 * @param sipboxId          绑定的输入框ID   （必填）
 * @param completeOrNum     (int) 0 全键盘 1 数字键盘 （必填）
 * @param isEncrypted       (int)是否进行加密 0 加密 1 不加密  （默认为0）
 * @param minLength         (int)最小输入长度 （默认为6）
 * @param maxLength         (int)最大输入长度 （默认为8）
 * @param randomType        (int)乱序类型 0 不乱序 1 数字乱序 2 全乱序 （默认为0）
 */
function setProperty(sipboxId, completeOrNum, isEncrypted, minLength, maxLength, randomType) {

    var keyboard = completeOrNum === 1 ? completeKeyboard : numberKeyboard;
    keyboard.bindInputBox(sipboxId);
    console.log(keyboard);
    // 服务端随机数，需加载页面时传入(需与服务端jar取同一个)
    var serverRandom = "MTIzNDU2Nzg5MDk4NzY1NA==";
    if (CFCA_OK != keyboard.setServerRandom(serverRandom, sipboxId)) alert("setServerRandom error");
    // 加密类型 sm2
    var cipherType = CIPHER_TYPE_SM2;
    if (CFCA_OK != keyboard.setCipherType(cipherType, sipboxId)) alert("setCipherType error");
    // 输出类型 原文加密输出（默认）
    var outputType = OUTPUT_TYPE_ORIGINAL;
    if (CFCA_OK != keyboard.setOutputType(outputType, sipboxId)) alert("setOutputType error");

    if (isEncrypted){
        if (CFCA_OK != keyboard.setEncryptState(isEncrypted, sipboxId)) alert("setEncryptState error");
    }
    if (minLength){
        if (CFCA_OK != keyboard.setMinLength(minLength, sipboxId)) alert("setMinLength error");
    }
    if (maxLength){
        if (CFCA_OK != keyboard.setMaxLength(maxLength, sipboxId)) alert("setMaxLength error");
    }

    // 是否乱序
    var randomType = randomType === 0 ? KEYBOARD_DISORDER_NONE : (randomType === 1 ? KEYBOARD_DISORDER_ONLY_DIGITAL : KEYBOARD_DISORDER_ALL);

    if (CFCA_OK != keyboard.setRandomType(randomType, sipboxId)) alert("setRandomType error");

//        var matchRegex = document.getElementById("matchRegex").value;
//        if(CFCA_OK != keyboard.setMatchRegex(matchRegex, sipboxId)) alert("setMatchRegex error");
}


function getKeyboard(sipboxId) {
    if (sipboxId === "SIPBox1") {
        return completeKeyboard;
    } else {
        return numberKeyboard;
    }
}
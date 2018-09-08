function addToLog(msg) {
    var log = document.getElementById("log");
    log.textContent = msg + "\n" + log.textContent; //Todo не уверен что нужно использовать textContent,но вроде работает
}
function addAuthor(){
    var formData = {
        firstname :  $("#inputFirstname").val(),
        lastname :  $("#inputLastname").val()
    };
    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/authors/insert",
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            $("#inputFirstname").innerHTML = ""; //Todo поля не очищаются
            $("#inputLastname").innerHTML = "";

            addToLog("Автор добавлен!");
            addAuthorToTable(result);
        },
        error : function(e) {
            addToLog(e.responseText);
        }
    });
}

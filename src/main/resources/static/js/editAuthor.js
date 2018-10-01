function editAuthor(id){
    var formData = {
        firstname : $("#inputFirstname" + id).val(),
        lastname : $("#inputLastname" + id).val()
    }
    $.ajax({
        type : "post",
        contentType : "application/json",
        url : "/authors/save/" + id,
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            addToLog("Автор обновлен!");
        },
        error : function(e) {
            addToLog(e.responseText);
        }
    });
}
function editAuthor(_id){
    var formData = {
        id : _id,
        firstname : $("#inputFirstname" + _id).val(),
        lastname : $("#inputLastname" + _id).val()
    }
    $.ajax({
        type : "post",
        contentType : "application/json",
        url : "/authors/save",
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            console.log("success edit author!");
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });
}
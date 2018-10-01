function getAuthors() {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/authors",
        dataType: "json",
        success: function (result) {
            addToLog("Запрашиваем список авторов");
            createAuthorsTable(result);
            addToLog("Список авторов успешно загружен!");
        },
        error: function (err) {
            addToLog("Ошибка " + err);
        }
    });
}

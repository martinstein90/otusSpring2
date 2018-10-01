$(document).ready(function () {
    $("#addAuthor").submit(function (event) {
        event.preventDefault();
        addAuthor();
    });

    console.log("ready");
    getAuthors();
});
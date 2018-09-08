function createAuthorsTable(authorsData) {
    var authorsTableDiv = document.getElementById("authorsTableDiv");

    var table = document.createElement("table");
    table.setAttribute("id", "authorsTable");
    var thead = createHeadTable();
    table.appendChild(thead);

    var tbody = document.createElement("tbody");
    authorsData.forEach(function (author) {
        var tr = createRowByAuthor(author);
        tbody.appendChild(tr);
    });
    table.appendChild(tbody);

    authorsTableDiv.appendChild(table);
}

function createHeadTable() {
    var thead = document.createElement("thead");

    var tr = document.createElement("tr");
    var thFirstname = document.createElement("th");
    thFirstname.innerHTML = "Firstname";
    tr.appendChild(thFirstname);
    var thLastname = document.createElement("th");
    thLastname.innerHTML = "Lastname";
    tr.appendChild(thLastname);
    var thAction = document.createElement("th");
    thAction.innerHTML = "Action";
    tr.appendChild(thAction);
    thead.appendChild(tr);

    return thead;
}

function createRowByAuthor(author) {
    var tr = document.createElement("tr");

    var tdFistname = document.createElement("td");
    var inFistname = document.createElement("input");
    inFistname.setAttribute("type", "text");
    inFistname.setAttribute("id", "inputFirstname" + author["id"]);
    inFistname.setAttribute("value", author["firstname"]);
    tdFistname.appendChild(inFistname);
    tr.appendChild(tdFistname);

    var tdLastname = document.createElement("td");
    var inLastname = document.createElement("input");
    inLastname.setAttribute("type", "text");
    inLastname.setAttribute("id", "inputLastname" + author["id"]);
    inLastname.setAttribute("value", author["lastname"]);
    tdLastname.appendChild(inLastname);
    tr.appendChild(tdLastname);

    var tdButton = document.createElement("td");
    var button = document.createElement("button");
    var textButton = document.createTextNode("Edit");
    button.appendChild(textButton);
    button.onclick = function(){
        editAuthor(author["id"]);
    };
    tdButton.appendChild(button);
    tr.appendChild(tdButton);

    return tr;
}

function addAuthorToTable(author) {
    var tbody = document.getElementsByTagName("tbody");
    var tr = createRowByAuthor(author);
    tbody.item(0).appendChild(tr);
}
"use strict"
function toggleClass(element) {
    // element = document.getElementById(element)
    let toggledElement = document.getElementById(element.getAttribute("toggledElement"))
    let toggledClass = element.getAttribute("toggledClass")
    if (toggledElement.classList.contains(toggledClass)) {
        toggledElement.classList.remove(toggledClass)
    } else  {
        toggledElement.classList.add(toggledClass)
    }
}

/* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function addTransactionRow() {
    let table = document.getElementById("itemTransaction").getElementsByTagName("tbody")[0]
    let row = table.insertRow()
    // Column1
    let cell1 = row.insertCell();
    // <select name="item">
    //     <option th:each="item : ${allItems}" th:value="${item.id}" th:text="${item.name}"></option>
    // </select>
    let selectElement = document.createElement("select")

    let hiddenElement = document.getElementById("availableItemsElement")
    for (let i = 0; i < hiddenElement.length; i++) {
        let opt = document.createElement("option")
        opt.value = hiddenElement[i].value
        opt.innerHTML = hiddenElement[i].innerHTML
        selectElement.appendChild(opt)
    }
    selectElement.required = true
    selectElement.name = "item"
    // <input hidden name="id" type="number" value="0">
    let idElement = document.createElement("input")
    idElement.hidden = true
    idElement.name = "transactionID"
    idElement.type = "number"
    idElement.value = "0"
    cell1.appendChild(selectElement)
    cell1.appendChild(idElement)

    // <input name="price" type="text">
    let cell2 = row.insertCell()
    let priceElement = document.createElement("input")
    priceElement.type = "text"
    priceElement.placeholder = "Price"
    priceElement.required = true
    priceElement.name = "price"
    cell2.appendChild(priceElement)

    // <input name="price" type="text">
    let cell3 = row.insertCell()
    let quantityElement = document.createElement("input")
    quantityElement.type = "text"
    quantityElement.placeholder = "Quantity"
    quantityElement.required = true
    quantityElement.name = "quantity"
    cell3.appendChild(quantityElement)

    // <button onclick="return removeTransactionRow()" type="button" class="btn btn-outline-warning btn-sm">Remove Row</button>
    let cell4 = row.insertCell()
    let removeRowBtn = document.createElement("button")
    // removeRowElement.onclick = "return removeTransactionRow()"
    removeRowBtn.onclick = function () {removeTransactionRow(removeRowBtn)}
    removeRowBtn.type = "button"
    removeRowBtn.classList.add("btn")
    removeRowBtn.classList.add("btn-outline-warning")
    removeRowBtn.classList.add("btn-sm")
    removeRowBtn.textContent = "Remove Row"
    cell4.appendChild(removeRowBtn)

    return false;
}

function removeTransactionRow(btn) {
    let tableRow = btn.parentNode.parentNode
    console.log(tableRow)
    let table = document.getElementById("itemTransaction")
    table.deleteRow(tableRow.rowIndex)
}
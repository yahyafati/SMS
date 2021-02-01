"use strict"
function toggleClass(element) {
    let toggledElement = document.getElementById(element.getAttribute("toggledElement"))
    let toggledClass = element.getAttribute("toggledClass")
    toggledElement.classList.toggle(toggledClass)
}

function transactionTyping() {
    console.log("Typing");
    const table = document.getElementById("itemTransaction").getElementsByTagName("tbody")[0];
    let total = 0.0;
    for (let i = 0, row; row = table.rows[i]; i++) {
        let price = 0.0, quantity = 0.0;
        for (let j = 0, col; col = row.cells[j]; j++) {
            const inputItem = col.children[0]
            if (inputItem.getAttribute("name") === "price") {
                price = parseFloat(inputItem.value);
            } else if (inputItem.getAttribute("name") === "quantity") {
                quantity = parseInt(inputItem.value);
            }
        }
        total += price*quantity;
    }
    const totalField = document.getElementById("totalAmountField")
    const paidField = document.getElementById("paidAmountField")
    const remainingField = document.getElementById("remainingAmountField")
    totalField.value = total;
    paidField.value = total;
    remainingField.value = 0;
}

/**
 * @param {Element} element
 */
function amountPaidChanged(element) {
    const totalField = document.getElementById("totalAmountField")
    const paidField = document.getElementById("paidAmountField")
    const remainingField = document.getElementById("remainingAmountField")
    if(element.id === "remainingAmountField") {
        paidField.value = totalField.value - remainingField.value
    } else if(element.id === "paidAmountField") {
        remainingField.value = totalField.value - paidField.value
    }
}

function addTransactionRow() {
    let table = document.getElementById("itemTransaction").getElementsByTagName("tbody")[0]
    let row = table.insertRow()
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
    selectElement.classList.add("form-select")
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
    priceElement.type = "number"
    priceElement.placeholder = "Price"
    priceElement.required = true
    priceElement.name = "price"
    priceElement.autocomplete = "off"
    priceElement.step = "any"
    priceElement.oninput = transactionTyping;
    priceElement.classList.add("input-text")
    cell2.appendChild(priceElement)

    // <input name="price" type="text">
    let cell3 = row.insertCell()
    let quantityElement = document.createElement("input")
    quantityElement.type = "number"
    quantityElement.oninput = transactionTyping;
    quantityElement.placeholder = "Quantity"
    quantityElement.required = true
    quantityElement.name = "quantity"
    quantityElement.autocomplete = "off"
    quantityElement.classList.add("input-text")
    cell3.appendChild(quantityElement)

    // <button onclick="return removeTransactionRow()" type="button" class="btn btn-outline-warning btn-sm">Remove Row</button>
    let cell4 = row.insertCell()
    let removeRowBtn = document.createElement("button")
    // removeRowElement.onclick = "return removeTransactionRow()"
    removeRowBtn.onclick = function () {removeTransactionRow(removeRowBtn)}
    removeRowBtn.type = "button"
    removeRowBtn.classList.add("my-btn")
    removeRowBtn.classList.add("my-btn-sm")
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
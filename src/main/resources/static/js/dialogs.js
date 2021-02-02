"use strict"
/**
 * @param {String} HTML representing a single element
 * @return {Element}
 */
function htmlToElement(html) {
    let template = document.createElement('template');
    html = html.trim(); // Never return a text node of whitespace as the result
    template.innerHTML = html;
    return template.content.firstChild;
}

function createDialog(message, type,
                      ok_button=true, cancel_button=true,
                      ok_text="Okay", cancel_text="Cancel",
                      ok_action = defaultDialogButtonClick, cancel_action = defaultDialogButtonClick) {
    const dialog = document.createElement("div");
    dialog.classList.add("dialog")

    const dialogHead = document.createElement("div")
    dialogHead.classList.add("head")
    const errorIcon = '<span class="dialog-icon"><i class="fas fa-bomb"></i></span>'
    dialogHead.appendChild(htmlToElement(errorIcon))
    const dialogTitle = '<h2 class="dialog-title">' + type + '</h2>\n';
    dialogHead.appendChild(htmlToElement(dialogTitle))
    dialog.appendChild(dialogHead);

    const dialogBody = document.createElement("div");
    dialogBody.classList.add("dialog-body")
    dialogBody.innerHTML = message
    dialog.appendChild(dialogBody)

    const dialogButtons = document.createElement("div")
    dialogButtons.classList.add("dialog-buttons")
    if (ok_button) {
        const btn_okay = document.createElement("button")
        btn_okay.classList.add("my-btn")
        btn_okay.onclick = ev => ok_action();
        btn_okay.innerHTML = ok_text
        dialogButtons.appendChild(btn_okay)
    }
    if (cancel_button) {
        const btn_cancel = document.createElement("button")
        btn_cancel.classList.add("my-btn")
        btn_cancel.onclick = ev => cancel_action()
        btn_cancel.innerHTML = cancel_text
        dialogButtons.appendChild(btn_cancel)
    }
    dialog.appendChild(dialogButtons)
    return dialog
}

function createErrorDialog(message) {
    return createDialog(message, "Error", true, false)
}

function createSuccessDialog(message) {
    return createDialog(message, "Success", true, false)
}

function createConfirmDialog(message, yes_action, no_action) {
    return createDialog(message, "Confirm", true, true, "Yes", "No", yes_action, no_action)
}

function openDialog(dialog) {
    let dimmer = createDimmer();

    let dialogType = dialog.getAttribute("dialog-type")
    if (dialogType === "error") {
        dialog.remove()
        dialog = createErrorDialog(dialog.innerHTML)
    } else if (dialogType === "confirm") {
        let goto = dialog.getAttribute("goto")
        dialog.remove()
        dialog = createConfirmDialog(dialog.innerHTML,
            () => openLink(goto), () =>  defaultDialogButtonClick())
    } else if (dialogType === "success") {
        dialog.remove()
        dialog = createSuccessDialog(dialog.innerHTML)
    }
    dimmer.appendChild(dialog)
    document.body.appendChild(dimmer)
}

function openConfirmDialog(element) {
    let message = element.getAttribute("message");
    let dialog = document.createElement("div")
    dialog.classList.add("dialog")
    dialog.id = "dialog-popup"
    dialog.setAttribute("dialog-type", "confirm")
    dialog.setAttribute("goto", element.getAttribute("goto"))
    dialog.innerHTML = message
    openDialog(dialog)
}

function openLink(url) {
    window.location = url;
}

function createDimmer() {
    let dimmer = document.createElement("div");
    dimmer.id = "dimmer-div";
    return dimmer;
}

function defaultDialogButtonClick() {
    let dimmer = document.getElementById("dimmer-div")
    dimmer.remove();
}

document.addEventListener("DOMContentLoaded", ()=>{
    let dialog = document.getElementById("dialog-popup")
    if (dialog != null) {
        openDialog(dialog);
    }
}, false)



function dummy() {
    // alert("It works")
    console.log("It works")
}
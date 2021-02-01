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
                      ok_action = "defaultOkayClick(this)", cancel_action="defaultCancelClick(this)") {
    let htmlDoc = '<div class="dialog">\n' +
        '  <div class="head">\n' +
        '  <span class="dialog-icon"><i class="fas fa-bomb"></i></span> ' +
        // '    <img class="dialog-icon" src="/images/placeholder/error.png" alt="dialog icon" />\n' +
        '    <h2 class="dialog-title">' + type +
        '</h2>\n' +
        '  </div>\n' +
        '  <div class="dialog-body">\n' + message +
        '  </div>\n' +
        '  <div class="dialog-buttons">\n' +
        (ok_button ? '    <button onclick="' + ok_action + '" class="dialog-btn">' + ok_text + '</button>\n' : '') +
        (cancel_button ? '    <button onclick="' + cancel_action + '" class="dialog-btn">' + cancel_text +'</button>\n' : '') +
        '  </div>\n' +
        '</div>'
    return htmlToElement(htmlDoc);
}

function createErrorDialog(message) {
    return createDialog(message, "Error", true, false)
}

function createConfirmDialog(message, yes_action, no_action) {
    return createDialog(message, "Confirm", true, true, "Yes", "No", yes_action, no_action)
}

function openDialog(dialog) {
    let dimmer = createDimmer();

    let dialogType = dialog.getAttribute("dialog-type")
    console.log(dialogType);
    if (dialogType === "error") {
        dialog.innerHTML = createErrorDialog(dialog.innerHTML).innerHTML
    } else if (dialogType === "confirm") {
        let yes_action = dialog.getAttribute("goto")
        yes_action = "openLink(" + yes_action + ")"
        let no_action = "defaultOkayClick()"
        dialog.innerHTML = createConfirmDialog(dialog.innerHTML, yes_action, no_action).innerHTML
    }
    let body = document.getElementsByTagName("body")[0]
    dimmer.prepend(dialog)
    body.prepend(dimmer)
}

function openLink(url) {
    window.location = url;
}

function createDimmer() {
    let dimmer = document.createElement("div");
    dimmer.id = "dimmer-div";
    return dimmer;
}

function defaultOkayClick() {
    let dimmer = document.getElementById("dimmer-div")
    dimmer.remove();
}

let dialog = document.getElementById("dialog-popup")
if (dialog != null) {
    openDialog(dialog);
}
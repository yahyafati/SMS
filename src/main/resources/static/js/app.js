/**
 * @param {Element} element
 */
function toggleClass(element) {
    console.log("Toggling Element")
    const toggledElement = document.getElementById(element.getAttribute("toggledElement"))
    const toggledClass = element.getAttribute("toggledClass")
    if (toggledElement.classList.contains(toggledClass)) {
        toggledElement.classList.remove(toggledClass)
    } else {
        toggledElement.classList.add(toggledClass)
    }
}
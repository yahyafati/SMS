
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
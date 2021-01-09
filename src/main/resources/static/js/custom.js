
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
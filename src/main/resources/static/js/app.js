/**
 * @param {Element} element
 */
function toggleClass(element) {
    console.log("Toggling Element")
    const toggledElement = document.getElementById(element.getAttribute("toggledElement"))
    const toggledClass = element.getAttribute("toggledClass")
    toggledElement.classList.toggle(toggledClass)
    // if (toggledElement.classList.contains(toggledClass)) {
    //     toggledElement.classList.remove(toggledClass)
    // } else {
    //     toggledElement.classList.add(toggledClass)
    // }
}

function documentLoaded() {
    const dashboard = document.getElementById("dashboard");
    if (dashboard != null) {
        loadGradients(dashboard)
    }
}

/**
 * @param {Element} element
 */
function loadGradients(element) {
    console.log("Loading Gradients");
    const gradients = [
        "linear-gradient(to right, #4facfe 0%, #00f2fe 100%)",
        "linear-gradient(to right, #43e97b 0%, #38f9d7 100%)",
        "linear-gradient(to right, #fa709a 0%, #fee140 100%)",
        // "radial-gradient(circle 248px at center, #16d9e3 0%, #30c7ec 47%, #46aef7 100%)",
        "linear-gradient(120deg, #a6c0fe 0%, #f68084 100%)",
        "linear-gradient(to top, #0ba360 0%, #3cba92 100%)",
        "linear-gradient(180deg, #2af598 0%, #009efd 100%)"
    ];

    let childNodes = Array.from(element.childNodes);
    childNodes = childNodes.filter(node => node.nodeName !== "#text");

    childNodes.forEach((board, i) => {
        board.style.backgroundImage = gradients[i];
    })
}
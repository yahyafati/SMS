/**
 * @param {Element} element
 */
function toggleClass(element) {
    console.log("Toggling Element")
    const toggledElement = document.getElementById(element.getAttribute("toggledElement"))
    const toggledClass = element.getAttribute("toggledClass")
    toggledElement.classList.toggle(toggledClass)
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
        "linear-gradient(180deg, #2af598 0%, #009efd 100%)",
        "linear-gradient(to top, #a8edea 0%, #fed6e3 100%)",
        "linear-gradient(to right, #43e97b 0%, #38f9d7 100%)",
        "linear-gradient(120deg, #f093fb 0%, #f5576c 100%)",
        "linear-gradient(to top, #fbc2eb 0%, #a6c1ee 100%)",
        "linear-gradient(to right, #ff8177 0%, #ff867a 0%, #ff8c7f 21%, #f99185 52%, #cf556c 78%, #b12a5b 100%)",
        "linear-gradient(45deg, #ff9a9e 0%, #fad0c4 99%, #fad0c4 100%)"
    ];

    let childNodes = Array.from(element.children);

    childNodes = childNodes.filter(node => node.classList.contains("dashboard-item"));
    // let indexArray = []
    childNodes.forEach((board, i) => {
        // let k = Math.trunc((Math.random() * 10000) % gradients.length);
        // while (indexArray.includes(k)) {
        //     k = Math.trunc((Math.random() * 10000) % gradients.length);
        // }
        // indexArray.push(k);
        // console.log(k)
        board.style.backgroundImage = gradients[i];
    })
}
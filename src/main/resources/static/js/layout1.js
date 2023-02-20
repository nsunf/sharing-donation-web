window.onload = () => {
	// setLayout();
}

// window.addEventListener("resize", setLayout);

function setLayout() {
	const header = document.querySelector("header");
	const footer = document.querySelector("footer");

	const content = document.querySelector(".page-wrapper");
	
	const headerHeight = header.clientHeight;
	const footerHeight = footer.clientHeight;

	content.style.minHeight = window.innerHeight - (headerHeight + footerHeight) + "px";
}

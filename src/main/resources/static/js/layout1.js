window.onload = () => {
	setLayout();
}

function setLayout() {
	const header = document.querySelector("header");
	const footer = document.querySelector("footer");

	const content = document.querySelector(".content");
	
	const headerHeight = header.clientHeight;
	const footerHeight = footer.clientHeight;

	content.style.minHeight = window.innerHeight - (headerHeight + footerHeight) + "px";
}

const imgInput = document.querySelector(".img-input");
const previewList = document.querySelector(".img-preview-list");

function imgCard(src) {
	const element = document.createElement("div");
	element.classList.add("col");
	element.innerHTML = `<img class="preview-img" src="${src}" alt="img-preview">`;
	return element;
}

imgInput.addEventListener("change", e => {
	const files = imgInput.files;
	previewList.innerHTML = "";
	
	for (let i = 0; i < files.length; i++) {
		const reader = new FileReader();

		reader.onload = function(r) {
			previewList.appendChild(imgCard(r.target.result));
		}
		
		reader.readAsDataURL(files[i]);
	}
	console.log(imgInput.files);
});
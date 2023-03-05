class ImgInputPreview {
	imgInput;
	previewList;
	imgInputEdited;
	tmpFiles;

	constructor() {
		this.imgInput = document.querySelector(".img-input");
		this.previewList = document.querySelector(".img-preview-list");
		this.imgInputEdited = false;
		
		this.tmpFiles = new DataTransfer();
		
		this.imgInput.addEventListener("change", e => {
			const files = this.imgInput.files;
			
			if (files.length > 10) {
				alert("최대 10개의 이미지를 첨부할 수 있습니다.");
				this.imgInput.value = null;
				return;
			}
			
			Array.from(files).forEach(f => {
				this.tmpFiles.items.add(f);
			})
			
			this.imgInput.files = this.tmpFiles.files;

			this.showPreview(this.imgInput.files);
		});
	}

	createImgCard(src, idx) {
		const newDiv = document.createElement("div");
		newDiv.classList.add("col");
		newDiv.style.cssText = `
			position: relative;
		`;
		
		const imgEl = document.createElement("img");
		imgEl.classList.add("preview-img");
		imgEl.src = src;
		imgEl.alt = "img-preview";
		newDiv.appendChild(imgEl);
		
		const delBtn = document.createElement("div");
		delBtn.innerText = "x";
		delBtn.style.cssText = `
			display: block; 
			color: white; 
			background: black; 
			width: 32px; 
			height: 32px;
			border-radius: 50%;
			text-align: center;
			position: absolute;
			right: 0;
			top: 0;
			transform: translate(0, -50%);
			cursor: pointer;
		`;
		
		newDiv.appendChild(delBtn);
		
		delBtn.addEventListener("click", e => {
			this.deletePreview(idx);
		});
		return newDiv;
	}
	
	showPreview(files) {
		this.previewList.innerHTML = "";
		for (let i = 0; i < files.length; i++) {
			const reader = new FileReader();

			reader.onload = r => {
				this.previewList.appendChild(this.createImgCard(r.target.result, i));
			}
			
			reader.readAsDataURL(files[i]);
		}
	}
	
	deletePreview(idx) {
		let df = new DataTransfer();
		Array.from(this.imgInput.files)
			.filter((f, i) => i != idx)
			.forEach(f => df.items.add(f));
			
		this.imgInput.files = df.files;
		this.tmpFiles = df;

		this.showPreview(this.imgInput.files);
	}
}
new ImgInputPreview();

/*
const imgInput = document.querySelector(".img-input");
const previewList = document.querySelector(".img-preview-list");
let tmpFiles = new DataTransfer();

function imgCard(src) {
	const element = document.createElement("div");
	element.classList.add("col");
	element.innerHTML = `<img class="preview-img" src="${src}" alt="img-preview">`;
	return element;
}

imgInput.addEventListener("change", e => {
	e.preventDefault();
	const files = imgInput.files;

	tmpFiles = files;
	previewList.innerHTML = "";
	
	if (files.length > 10) {
		alert("최대 10개의 이미지를 첨부할 수 있습니다.");
		imgInput.value = null;
		return;
	}
	
	for (let i = 0; i < files.length; i++) {
		const reader = new FileReader();

		reader.onload = function(r) {
			previewList.appendChild(imgCard(r.target.result));
		}
		
		reader.readAsDataURL(files[i]);
	}
});
*/
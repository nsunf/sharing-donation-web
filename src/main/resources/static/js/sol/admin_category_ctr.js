const catImgPreview = document.getElementById("cat-img-preview");
const catImgInput = document.getElementById("cat-img-input");
const catNameInput = document.getElementById("cat_name");
const catIdInput = document.getElementById("cat_id");

function setStackImg() {
  const fileList = catImgInput.files;

  if (fileList.length == 0) {
    catImgInput.src = "";
  }

  readURL(catImgInput);
}

function readURL(input) {
  if (input.value) {
    const reader = new FileReader();

    reader.onload = e => {
      catImgPreview.src = e.target.result;
    };

    reader.readAsDataURL(input.files[0]);
  }
}

setStackImg();
catImgInput.addEventListener("change", setStackImg);


function setModalScrolling() {
  const isModalOpened = document.body.classList.contains("modal-open");
  document.documentElement.style.overflowY = isModalOpened ? "hidden" : "initial";
}

document.getElementById("add-cat-btn").addEventListener("click", () => {
		document.catForm.action = "/mypage/stacks/add";
		catImgInput.required = true;
		setModalScrolling();
	});

document.getElementById("cat-popup").addEventListener("hidden.bs.modal", () => {
	document.catForm.reset();
	catIdInput.value = null;
  catImgPreview.src = "";
  setModalScrolling();
});


const editStackBtnList = document.querySelectorAll(".edit-stack-btn");
const deleteStackBtnList = document.querySelectorAll(".delete-stack-btn");

editStackBtnList.forEach(btn => {
  btn.addEventListener("click", e => {
		document.catForm.action = "/mypage/stacks/update";
    catNameInput.value = btn.dataset.name;
    catImgPreview.src = btn.dataset.src;
    catIdInput.value = btn.dataset.stackId;
    catImgInput.required = false;
  });
});

deleteStackBtnList.forEach(btn => {
  btn.addEventListener("click", () => {
    location.href="/mypage/stacks/delete?id=" + btn.dataset.stackId;
  });
});
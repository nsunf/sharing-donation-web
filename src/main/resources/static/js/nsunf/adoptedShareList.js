
function getStoryDto(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	fetch("/story/sharingid/" + id, {
		method: "POST",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		}
	}).then(res => res.json())
	.then(data => {
		document.getElementById("storyModalContent").innerText = data.content;
		document.getElementById("storyRegTime").innerText = data.regTime;
		
		const sharingLinks = document.querySelectorAll(".sharing-link");
		sharingLinks.forEach(s => s.href = "/sharing/" + data.sharingId)

		const editStoryBtn = document.getElementById("editStoryBtn");
		if (data.sharingDone == 'Y') {
			editStoryBtn.disabled = true;
		} else {
			editStoryBtn.disabled = false;
			editStoryBtn.onclick = () => setStoryForm(data);
		}
	}).catch(e => {
		console.log(e);
	});
}

function setStoryForm(storyFormDto) {
	//const frm = document.storyFrm;
	const idInput = document.getElementById("storyId");
	const content = document.getElementById("storyFormModalContent");
	const regTime = document.getElementById("storyFormRegTime");
	const memberId = document.getElementById("memberId");
	const textLength = document.getElementById("textLength");

	idInput.value = storyFormDto.id;
	content.value = storyFormDto.content;
	regTime.innerText = storyFormDto.regTime;
	memberId.value = storyFormDto.memberId;
	
	textLength.innerText = content.value.length + "/300";
}

function setTextArea() {
	const storyTextArea = document.getElementById("storyFormModalContent");
	const textLength = document.getElementById("textLength");
	storyTextArea.addEventListener("keyup", e => {
		if (e.target.value.length > 300) {
			e.target.value = e.target.value.slice(0, 300);
		}

		textLength.innerText = e.target.value.length + "/300";
	});
}

window.addEventListener("load", () => {
	setTextArea();
});
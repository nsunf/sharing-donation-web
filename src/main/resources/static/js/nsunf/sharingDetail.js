
function toggleHeart(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	const likeEl = document.querySelector(".like");
	const likeCount = document.querySelector(".like-count");
	
	fetch("/sharing/heart/" + id, {
		method: "GET",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		}
	}).then(res => res.text())
	.then(data => {
		if (likeEl.classList.contains("like-false")) {
			likeEl.classList.remove("like-false");
			likeEl.classList.add("like-true");
		} else {
			likeEl.classList.remove("like-true");
			likeEl.classList.add("like-false");
		}
		likeCount.innerText = data;
	});
}

function setTextArea() {
	const storyTextArea = document.getElementById("content");
	const textLength = document.getElementById("textLength");
	storyTextArea.addEventListener("keyup", e => {
		if (e.target.value.length > 300) {
			e.target.value = e.target.value.slice(0, 300);
		}

		textLength.innerText = e.target.value.length + "/300";
	});
}

function addStory() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	const sharingIdInput = document.querySelector(".modal #sharingId");
	const memberIdInput = document.querySelector(".modal #memberId");
	const contentInput = document.querySelector(".modal #content");
	
	fetch("/story/new", {
		method: "POST",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		},
		body: JSON.stringify({
			sharingId: sharingIdInput.value,
			memberId: memberIdInput.value,
			content: contentInput.value
		})
	}).then(res => res.json())
	.then(data => {
		const storyId = data;
		if (data != null) {
			alert("사연이 등록되었습니다.")
			location.reload();
		} else {
			alert("사연이 등록되지 않았습니다.")
		}
	}).catch(e => {
		console.log(e);
	})
}

function closeModal() {
	const closeBtn = document.querySelector(".btn-close");
	closeBtn.click();
}

function disableRegistStoryBtn() {
	const btn = document.getElementById("registStoryBtn");
	btn.disabled = true;
	btn.dataset.bsTarget = null;
	btn.dataset.bsToggle = null;
}

window.addEventListener("load", () => {
	setTextArea();
});

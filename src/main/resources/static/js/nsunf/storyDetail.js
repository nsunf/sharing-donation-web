const editBtn = document.getElementById("editBtn");
const chooseBtn = document.getElementById("chooseBtn");
const delBtn = document.getElementById("delBtn");

function onClickCheckBox() {
	const checkBox = document.querySelectorAll("input[name='storyCheckList']:checked");
	
	const sharingStatus = document.getElementById("sharingStatus");
	
	if (sharingStatus.value == '완료') return;
	
	if (checkBox.length == 0) {
		editBtn.disabled = true;
		chooseBtn.disabled = true;
		delBtn.disabled = true;
	} else if (checkBox.length == 1) {
		editBtn.disabled = false;
		chooseBtn.disabled = false;
		delBtn.disabled = false;
	} else {
		editBtn.disabled = true;
		chooseBtn.disabled = true;
		delBtn.disabled = false;
	}
}

editBtn.addEventListener("click", e => {
	const checkedStoryId = document.querySelector("input[name='storyCheckList']:checked").value;
	setStoryModal(checkedStoryId);
});

chooseBtn.addEventListener("click", e => {
	if (!confirm("사연을 채택하시겠습니까?")) return; 
	const checkedStoryId = document.querySelector("input[name='storyCheckList']:checked").value;
	adoptStory(checkedStoryId);
});

delBtn.addEventListener("click", e => {
	const checkedInputs = document.querySelectorAll("input[name='storyCheckList']:checked");
	const checkedStoryIdList = Array.from(checkedInputs).map(el => el.value);
	if (!confirm(checkedStoryIdList.length + "개의 사연을 삭제하시겠습니까?")) return;
	deleteStories(checkedStoryIdList);
});


function setStoryModal(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	fetch("/story/" + id, {
		method: "POST",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		}
	}).then(res => res.json())
	.then(data => {
		document.getElementById("authorName").value = data.memberName;
		document.getElementById("storyContent").value = data.content;
		document.getElementById("storyId").value = data.id;
	}).catch(e => {
		console.log(e);
	});
}

function adoptStory(storyId) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	fetch("/story/adopt/" + storyId, {
		method: "POST",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		}
	}).then(res => {
		location.reload();
	})
	.catch(e => {
		console.log(e);
	});
}

function deleteStories(storyIdList) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	fetch("/story/delete/", {
		method: "POST",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		},
		body: JSON.stringify({
			storyIdList
		})
	}).then(res => {
		location.reload();
	})
	.catch(e => {
		console.log(e);
	});
}

onClickCheckBox();

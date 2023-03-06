
function toggleHeart(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	const likeEl = document.querySelector(".like");
	const likeCount = document.querySelector(".like-count");
	
	fetch("/sharing_board/heart/" + id, {
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
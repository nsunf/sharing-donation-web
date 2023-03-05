
function getStoryDto(id) {
	console.log(id);
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
		console.log(data);
		document.getElementById("storyModalContent").innerText = data.content;
		document.getElementById("editStoryBtn").disabled = data.done == 'Y';
	}).catch(e => {
		console.log(e);
	});
}
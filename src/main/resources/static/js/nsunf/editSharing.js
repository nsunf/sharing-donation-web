function deleteSharing(id) {
	if (!confirm("신청을 삭제하시겠습니까?")) return;
	location.href="/sharing/delete/" + id;
}

function approveSharing(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	if (!confirm("신청을 승인하시겠습니까?")) return;
	
	fetch("/sharing/admin/approve", {
		method: "POST",
		headers: {
				'header': header,
				'X-Requested-With': "XMLHttpRequest",
				"Content-Type": "application/json",
				'X-CSRF-Token': token
		},
		body: JSON.stringify({
			sharingIdList: [id + ""]
		})
	}).then(res => {
		location.reload();
	});
}

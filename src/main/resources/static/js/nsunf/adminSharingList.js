const selectAllBtn = document.querySelector(".select-all-btn");
const sharings = document.querySelectorAll(".sharing");

selectAllBtn.addEventListener("click", () => {
	let isChecked = false;
	
	for (let i = 0; i < sharings.length; i++) {
		if (sharings[i].checked == true) {
			isChecked = true;
			break;
		}
	}

	sharings.forEach(s => {
		s.checked = !isChecked;
		selectAllBtn.checked = !isChecked;
	})
});

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

const approveBtn = document.getElementById("approve-btn");
const deleteBtn = document.getElementById("delete-btn");

approveBtn.addEventListener("click", () => {
	const selectedSharings = Array.prototype.slice.call(sharings).filter(s => s.checked == true).map(s => s.value);
	if (selectedSharings.length > 0) {
		if (!confirm(selectedSharings.length + "개의 항목을 승인하시겠습니까?")) return;
		fetch("/sharing/admin/approve", {
			method: "POST",
			headers: {
					'header': header,
					'X-Requested-With': "XMLHttpRequest",
					"Content-Type": "application/json",
					'X-CSRF-Token': token
			},
			body: JSON.stringify({
				sharingIdList: selectedSharings
			})
		}).then(res => {
			location.reload();
		});
	}
})

deleteBtn.addEventListener("click", () => {
	const selectedSharings = Array.prototype.slice.call(sharings).filter(s => s.checked == true).map(s => s.value);
	if (selectedSharings.length > 0) {
		if (!confirm(selectedSharings.length + "개의 항목을 삭제하시겠습니까?")) return;
			fetch("/sharing/admin/delete", {
				method: "POST",
				headers: {
						'header': header,
						'X-Requested-With': "XMLHttpRequest",
						"Content-Type": "application/json",
						'X-CSRF-Token': token
				},
				body: JSON.stringify({
					sharingIdList: selectedSharings
				})
			}).then(res => {
				location.reload();
			});
	}
})
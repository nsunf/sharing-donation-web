const sharingLink = document.getElementById("sharingDetailLink");
const storyLink = document.getElementById("storyDetailLink");

function setModalLinks(sharingId) {
	
	sharingLink.href = "/sharing/" + sharingId;
	storyLink.href = "/story/sharing-id/" + sharingId;
}

window.addEventListener("load", () => {
	document.getElementById("sharingModal").addEventListener("hidden.bs.modal", () => {
		sharingLink.href = "#";
		storyLink.href = "#";
	});
});

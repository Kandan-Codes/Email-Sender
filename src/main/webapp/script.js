
const popup = document.getElementById("patternPopup");
const overlay = document.createElement("div");
overlay.className = "overlay";
document.body.appendChild(overlay);

function openPatternPopup() {
	popup.style.display = "block";
	overlay.style.display = "block";
}

function closePatternPopup() {
	popup.style.display = "none";
	overlay.style.display = "none";
}

function showSuccessPopup() {
    document.getElementById("successPopup").style.display = "flex";
}

function closeSuccessPopup() {
    document.getElementById("successPopup").style.display = "none";
}

function showWarningPopup() {
	document.getElementById("warningPopup").style.display = "flex";
}

function closeWarningPopup() {
	document.getElementById("warningPopup").style.display = "none";
}

window.onload = function () {
	const params = new URLSearchParams(window.location.search);
	if(params.get("status") === "success") {
		showSuccessPopup();
	} else if(params.get("status") === "warning") {
		showWarningPopup();
	}
};
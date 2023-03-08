const mapSvg = document.getElementById("map-svg");
const outlineEls = document.querySelectorAll(".OUTLINE");
const textEls = document.querySelectorAll(".TEXT");

mapSvg.addEventListener("mouseover", el => {
  if (el.target === mapSvg) resetHover();
})

outlineEls.forEach((outline, i) => {
  const textEl = textEls[i];
  outline.addEventListener("mouseover", () => {
    resetHover();
    outline.classList.add("OUTLINE--hover");
    textEl.classList.add("TEXT--hover");
  })

  textEl.addEventListener("mouseover", () => {
    resetHover();
    outline.classList.add("OUTLINE--hover");
    textEl.classList.add("TEXT--hover");
  })

  outline.addEventListener("click", () => {
	  location.href="/sharing/area/" + outline.dataset.gu;
  })

  textEl.addEventListener("click", () => {
	  location.href="/sharing/area/" + textEl.dataset.gu;
  })
});

textEls.forEach(el => {
	const tmp = el.textContent;
	if (el.dataset.count != null) {
		el.innerHTML = tmp + '<tspan>' + el.dataset.count + '</tspan>'; 
	}
});

function resetHover() {
  outlineEls.forEach(el => {
    el.classList.remove("OUTLINE--hover");
  })
  textEls.forEach(el => {
    el.classList.remove("TEXT--hover");
  })
}
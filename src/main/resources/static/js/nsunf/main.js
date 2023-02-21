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
    alert("outline clicked");
  })

  textEl.addEventListener("click", () => {
    alert("text clicked");
  })
});


function resetHover() {
  outlineEls.forEach(el => {
    el.classList.remove("OUTLINE--hover");
  })
  textEls.forEach(el => {
    el.classList.remove("TEXT--hover");
  })
}
function setAutoMinHeight(target) {
  const header = document.querySelector("header");
  const footer = document.querySelector("footer");

  const headerHeight = header.clientHeight;
  // const footerHeight = footer.clientHeight;

  let calcedHeight = window.innerHeight - headerHeight;
  if (window.innerHeight <= 767) calcedHeight += 64;

  console.log(calcedHeight)
  target.style.minHeight = calcedHeight + "px";
  target.style.height = 0;
}

const target = document.querySelector(".auto-min-height");

window.addEventListener("resize", () => {
  setAutoMinHeight(target);
})

setAutoMinHeight(target);
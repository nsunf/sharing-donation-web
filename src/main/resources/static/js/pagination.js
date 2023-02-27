
function paging(page) {
	const search = location.search.slice(1);
	let queryMap = search.split("&").map(s => s.split("="));
	
	let needPage = true;
	
	for (let i = 0; i < queryMap.length; i++) {
		if (queryMap[i][0] == "page") {
			queryMap[i][1] = page;
			needPage = false;
		}
	}
	
	if (needPage)
		queryMap.push(["page", page]);
	
	let result = queryMap.map(arr => arr.reduce((lhs, rhs) => lhs + "=" + rhs));

	location.href = location.pathname + "?" + result.reduce((lhs, rhs) => lhs + "&" + rhs);
}

function searchResults() {

    let input, filter, table, tr, reportName, i, txtValue;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("dataTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        reportName = tr[i].getElementsByTagName("td")[0];
        if (reportName) {
            txtValue = reportName.textContent || reportName.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function updateLinksShown(selectedValue, referrer){
    console.log(selectedValue);
    console.log(referrer);

    var href = new URL(referrer);
    href.searchParams.set('linksShown', selectedValue);
    href.searchParams.set('page', 1);
    window.location.href = href.toString();
}

function updateUsersShown(selectedValue, referrer){
    console.log(selectedValue);
    console.log(referrer);

    var href = new URL(referrer);
    href.searchParams.set('usersShown', selectedValue);
    href.searchParams.set('page', 1);
    window.location.href = href.toString();
}

function updateClicksShown(selectedValue, referrer){
    console.log(selectedValue);
    console.log(referrer);

    var href = new URL(referrer);
    href.searchParams.set('clicksShown', selectedValue);
    href.searchParams.set('page', 1);
    window.location.href = href.toString();
}
const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

function rewrite(data) {
    datatableApi.clear().rows.add(data).draw();
}

function filterMeals() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: rewrite
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, rewrite);
}

$(function () {
    datatableApi = $("#meals").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});
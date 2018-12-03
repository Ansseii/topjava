const ajaxUrl = "ajax/admin/users/";
let datatableApi;

function enable(id) {
    let check = $("#" + id).is(":checked");
    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "enabled=" + check,
        success: function () {
            check ? successNoty("ENABLED") : successNoty("DISABLED");
        }
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
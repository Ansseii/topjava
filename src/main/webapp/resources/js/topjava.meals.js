const userAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: userAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: userAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": userAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === 'display') {
                            return dateTimeFormat(date)
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render": renderEditBtn

                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });

    $('#startDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#endDate').datetimepicker({
        format: 'Y-m-d',
        timepicker: false
    });
    $('#startTime').datetimepicker({
        format: 'H:i',
        datepicker: false
    });
    $('#endTime').datetimepicker({
        format: 'H:i',
        datepicker: false
    });
    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});
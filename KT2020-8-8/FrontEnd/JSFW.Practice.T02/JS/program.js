$(function () {
    $(".header").load("header.html");
    $(".menu").load("menu.html");
    $(".footer").load("footer.html");
    $(".content").load("home.html");
});

function clickHomeButton() {
    $(".content").load("home.html");
}

function clickAddButton() {
    $(".content").load("car.html");
}

function resetSaveCarForm(){
    $("#licensePlate").val("");
    $("#repairDate").val("");
    $("#customerName").val("");
    $("#catalog").val("");
    $("#carMaker").val("");
}

function clickSaveCarBtn(){
    var licensePlate=$("#licensePlate").val();
    var repairDate=$("#repairDate").val();    
    var car={
    licensePlate:$("#licensePlate").val(),
    stringRepairDate:$("#repairDate").val(),
    customerName:$("#customerName").val(),
    catalogs:$("#catalogs").val(),
    carMaker:$("#carMaker").val()
    }
    $.ajax({
        url: 'http://localhost:8080/api/v1/cars',
        type: 'POST',
        data: JSON.stringify(car), // body
        contentType: "application/json", // type of body (json, xml, text)
        // dataType: 'json', // datatype return
        success: function (data, textStatus, xhr) {
            showSuccessAlert();
            showAccessoryDetailBtn(licensePlate, repairDate);
            resetSaveCarForm();
        },
        error(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });

}

function showSuccessAlert() {
    $("#success-alert").fadeTo(2000, 500).slideUp(500, function () {
        $("#success-alert").slideUp(500);
    });
}

function showAccessoryDetailBtn(licensePlate, repairDate){
    $('#accessoryDetailBtn').show();
    $('#lilicensePlate').val(licensePlate);
    $('#lirepairDate').val(repairDate);
    getAccessoryDetail(licensePlate,repairDate);
}

function getAccessoryDetail(licensePlate,repairDate){
    $.get(`http://localhost:8080/api/v1/cars/${licensePlate}_${repairDate}/accessories`, function (data, status) {

        // error
        if (status == "error") {
            alert("Error when loading data");
            return;
        }

        // success
        // empty table before rebuild it
        $('ul').empty();
        $('#listGroupAccount').empty();

        accessories = data;
        fillCarAccessories(accessories);
    });
}

function fillCarAccessories(accessories){
    accessories.forEach(function(accessory) {
        $('tbody').append(
            '<tr>' +
            '<td>' + accessory.name + '</td>' +
            '<td>' + accessory.price + '</td>' +
            '<td>' + accessory.statusDamaged + '</td>' +
            '<td>' + accessory.repairStatus + '</td>' +
            '<td>' +
            '<a class="edit" title="Edit" data-toggle="tooltip" onclick="openUpdateModal(' + accessory.id + ')"><i class="material-icons">&#xE254;</i></a>' +
            '<a class="delete" title="Delete" data-toggle="tooltip" onClick="openConfirmDelete(' + accessory.id + ')"><i class="material-icons">&#xE872;</i></a>' +
            '</td>' +
            '</tr>')
    });
}

function clickAccessoryDetailBtn(){
    $(".content").load("accessoryDetail.html");
}

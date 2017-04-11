function validation(item, message) {
    item.oninvalid = function (e) {
        e.target.setCustomValidity('');
        if (!e.target.validity.valid) {
            e.target.setCustomValidity(message);
        } else if (e.target.validity.valueMissing) {
            e.target.setCustomValidity("field can't be empty");
        }
    };
    item.oninput = function (e) {
        e.target.setCustomValidity('');
    }
};
$("input.nameInput").each(function (i, item) {
    validation(item, "please enter valid name");
});
$("input.priceInput").each(function (i, item) {
    validation(item, "please enter valid price");
});
$("input[name=password]").each(function (i, item) {
    validation(item, "please enter valid password( at least 6 character)");

});
$("input[name=img_path]").each(function (i, item) {
    validation(item, "please enter valid img path");

});
$("input[name=departure_time]").each(function (i, item) {
    validation(item, "please enter valid date(yyyy-MM-ddTHH:mm)");

});
$("input[name=flight_time]").each(function (i, item) {
    validation(item, "please enter valid time(HH:mm)");

});

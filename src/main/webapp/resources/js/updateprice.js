function updatePrice() {
    if (avilablePlace == 0) {
        $('#total_price').text(0);
        return;
    }
    var currTime = new Date(Date.now());
    var avTime = availableTimeInPercents(time, currTime);
    var privilege = $('#privilege').is(':checked');
    var newPrice = privilege ? startPriceBusiness : startPrice;
    newPrice = newPrice * (1 + 1 - avTime);
    var withBaggage = $('#with_baggage').is(':checked');
    newPrice = newPrice * (withBaggage ? 2.5 : 1);
    var avilablePlaceInPercent = avilablePlace / flightPlace;
    newPrice = newPrice * (1 + 1 - avilablePlaceInPercent);

    $('#total_price').text(newPrice.toFixed(2));
};
function availableTimeInPercents(time, currTime) {

    var oldDate = new Date(time);
    oldDate.setMonth(oldDate.getMonth() - 3);

    var firstDiff = diffDate(currTime, time)

    if (firstDiff <= 0) {
        return 2;
    }
    var secDiff = diffDate(oldDate, time);
    firstDiff = Math.min(firstDiff, secDiff);
    return firstDiff / secDiff;

}
function diffDate(time1, time2) {
    var utc1 = Date.UTC(time1.getFullYear(),
        time1.getMonth(),
        time1.getDate(),
        time1.getHours(),
        time1.getMinutes());
    var utc2 = Date.UTC(time2.getFullYear(),
        time2.getMonth(),
        time2.getDate(),
        time2.getHours(),
        time2.getMinutes());

    return Math.floor((utc2 - utc1) / 1000 * 60);
};
$(document).ready(function () {
    $('#privilege').change(function () {
            updatePrice()
        }
    );
    $('#with_baggage').change(function () {
            updatePrice()
        }
    );
    setInterval(updatePrice(), 1000)

});
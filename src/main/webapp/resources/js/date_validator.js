$('#flight_input').submit(function () {
    var elem = $('#departure_time');
    var date = new Date(Date.parse(elem.val()));
    var shift = date.getTimezoneOffset() * 60 * 1000;
    date = new Date(date.getTime() + shift);

    elem.val(parseDate(date));
});
Number.prototype.padLeft = function (base, chr) {
    var len = (String(base || 10).length - String(this).length) + 1;
    return len > 0 ? new Array(len).join(chr || '0') + this : this;
};

function parseDate(d) {
    return [(d.getUTCFullYear()).padLeft(),
            (d.getUTCMonth() + 1).padLeft(),
            d.getUTCDate().padLeft()].join('-') +
        'T' +
        [d.getUTCHours().padLeft(),
            d.getUTCMinutes().padLeft()].join(':');
}
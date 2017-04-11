Number.prototype.padLeft = function (base, chr) {
    var len = (String(base || 10).length - String(this).length) + 1;
    return len > 0 ? new Array(len).join(chr || '0') + this : this;
}
function formatDate(d) {
    return [(d.getFullYear()).padLeft(),
            (d.getMonth() + 1).padLeft(),
            d.getDate().padLeft()].join('-') +
        ' ' +
        [d.getHours().padLeft(),
            d.getMinutes().padLeft()].join(':');
}
$(document).ready(function () {
    $('.date-time-out').each(function (index, item) {
        var date = new Date(Date.parse(item.innerHTML.trim()));
        item.innerHTML = formatDate(date);
    })

});

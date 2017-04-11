function logout() {
    $.post('/logout')
        .done(function(data, textStatus, jqXHR) {

            location.href = '/login.html';
        })
};
$(function() {
    $('#lang').find('li').click(function(e) {
        e.preventDefault();
        var languages = {
            'українська': 'ua',
            'english': 'en'
        };
        $.post('/lang', {lang: languages[$(this).text().trim().toLowerCase()]})
            .done(function() {
                location.reload();
            });
    })

    hoverSwap($("#lang"));
});


function hoverSwap($el) {
    var swap = function(e) {
        var $span = $el.find('span');
        var currentText = $span.text();
        $span.text($el.data('alt'));
        $el.data('alt', currentText);
    };

    $el.hover(swap, swap);
}

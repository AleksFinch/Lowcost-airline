function logout() {
    $.post('/logout')
        .done(function(data, textStatus, jqXHR) {

            location.href = '/login.html';
        })
};

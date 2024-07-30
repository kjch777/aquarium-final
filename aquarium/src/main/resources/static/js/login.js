$(document).ready(function() {
    $('#login-form').submit(function(event) {
        event.preventDefault();

        var memberId = $('#memberId').val();
        var memberPw = $('#memberPw').val();

        function validateUsername(memberId) {
            var re = /^[a-zA-Z0-9]{5,}$/;
            return re.test(memberId);
        }

        function validatePassword(memberPw) {
            var re = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
            return re.test(memberPw);
        }

        if (!validateUsername(memberId)) {
            alert('아이디는 5자 이상의 숫자와 영어(대소문자 가능)로 입력해주세요.');
            return;
        }

        if (!validatePassword(memberPw)) {
            alert('비밀번호는 8자 이상의 영어, 숫자, 특수문자(@$!%*#?&)를 포함해야 합니다.');
            return;
        }

        $.post('/login', { memberId: memberId, memberPw: memberPw })
            .done(function(data) {
                alert('로그인 성공!');
                window.location.href = "/"; // 로그인 성공 시 이동할 페이지
            })
            .fail(function(xhr, status, error) {
                $('#error-message').text('아이디 또는 비밀번호가 틀렸습니다.').show();
                $('#memberId').val('');
                $('#memberPw').val('');
            });
    });

    $('#find-memberId').click(function(event) {
        event.preventDefault();
        alert('아이디를 찾기 기능이 준비 중입니다.');
    });

    $('#find-memberPw').click(function(event) {
        event.preventDefault();
        alert('비밀번호를 찾기 기능이 준비 중입니다.');
    });

    $('#register').click(function(event) {
        event.preventDefault();
        window.location.href = "회원가입.html";
    });
});
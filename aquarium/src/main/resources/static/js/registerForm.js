
//아이디 중복검사
function idDupl(){

    var id = $("#memberId").val();
    var idRegExp = /^[a-z0-9A-Z]{5,50}$/;

    if(id === ""){
        $("#idDupl").text("아이디를 입력해주세요.").css("color", "red");
        return false;
    }
    else if(!idRegExp.test(id)){
        $("#idDupl").text("잘못된 아이디 방식입니다.").css("color", "red");
        return false;
    }
	$.ajax({
		url:"/checkId",
		type:"post",
        dataType:"json",
		data:{"memberId" : id},
        success: function(result){
			if(result == true){
				$("#idDupl").text("사용 가능한 아이디입니다.").css("color", "green");
				return true;
			}
			else{
				$("#idDupl").text("중복된 아이디입니다.").css("color", "red");
				return false;
			}
		}
	});
	
}

//비밀번호 일치 확인
function pwSame(){
        
    var password = $("#memberPw").val();
    var passwordConfirm = $("#memberPwConfirm").val(); 

    if(password === "" || passwordConfirm === ""){
        $("#pwSame").text("비밀번호를 모두 입력해주세요.").css("color", "red");
        return false;
    }
    else if(password != passwordConfirm){
        $("#pwSame").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        return false;
    }
    else{
        $("#pwSame").text("비밀번호가 일치합니다.").css("color", "green");
        return true;
    }
}

//이메일 인증
function sendEmail(){
    if (!$("#memberEmail").val()) {
    	$("#emailMessage").text("이메일을 입력해주세요.");
    	return;
    }
    $("#emailCheck").css("display","block");    
    $.ajax({
        url:"/mail",
        type:"post",
        dataType:"json",
        data:{"mail" : $("#memberEmail").val()},
        success: function(data){
            $("#emailMessage").text("인증번호가 발송되었습니다.");
            $("#Confirm").attr("value",data);
        }
    });
}

function checkEmail(){
    var number1 = $("#checkNumber").val();
    var number2 = $("#Confirm").val();

    if(number1 == number2){
        $("#emailConfirmMessage").text("인증번호가 일치합니다.").css("color", "green");
        return true;
    }else{
        $("#emailConfirmMessage").text("인증번호가 일치하지 않습니다.").css("color", "red");
        return false;
    }
}


$(document).ready(function(){

    //아이디 유효성 검사
    $("#memberId").on("input", function(){
 
        var idRegExp = /^[a-z0-9A-Z]{5,50}$/;
        var id = $("#memberId").val();

        if(id === ""){
            $("#validateIdMsg").text("");
            return false;
        }
        else if(!idRegExp.test(id)){
            $("#validateIdMsg")
            .text("아이디는 5자이상의 영어와 숫자의 조합으로 작성해주세요.").css("color", "red");
            return false;
        }
        else{
            $("#validateIdMsg").text("");
            return true;
        }
    });

    //비밀번호 유효성 검사
    $("#memberPw").on("input", function(){
        var password = $(this).val();
        var pwRegExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

        if(password === ""){
            $("#validatePwMsg").text("");
            return false;
        }
        else if(pwRegExp.test(password)){
            $("#validatePwMsg")
            .text("");
            return true;
        }
        else{
            $("#validatePwMsg")
            .text("비밀번호는 최소 8자 이상의 특수문자, 영어, 숫자 조합이어야 합니다.")
            .css("color","red");
            return false;
        }
    })

    //이름 유효성 검사
    $("#memberName").on("input",function(){

        var name = $(this).val();
        var nameRegExp = /^[가-힣]{2,5}$/;


        if(name === ""){
            $("#validateNameMsg").text("");
        }
        else if(!nameRegExp.test(name)){
            $("#validateNameMsg").text("부적절한 이름입니다.")
            .css("color", "red");
            return false;
        }
        else{
            $("#validateNameMsg").text("");
            return true;
        }
    })

    //폼 제출 유효성 검사
    $("#registerForm").submit(function(event) {
		
	//	var isIdValid = idDupl();
        var isPwValid = pwSame();
        var isEmailValid = checkEmail();
        var isNameValid = $("#memberName").val() !== "" && /^[가-힣]{2,5}$/.test($("#memberName").val()); // 이름 검증 추가
        var isPwRegExpValid = $("#memberPw").val() !== "" && /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test($("#memberPw").val());
        var isIdRegExpValid = $("#memberId").val() !== "" && /^[a-z0-9A-Z]{5,50}$/.test($("#memberId").val());
        
   //   console.log(isIdValid);
        console.log(isPwValid);
        console.log(isNameValid);
        console.log(isPwRegExpValid);
        console.log(isIdRegExpValid);

        // 모든 조건이 충족되었는지 확인
        if (isPwValid && isNameValid && isPwRegExpValid && isIdRegExpValid && isEmailValid) {

            // 모든 조건이 충족되었을 때 폼 제출 허용
            window.location.replace("/login");

        } else {
            
            // 하나라도 조건이 충족되지 않았을 때 폼 제출 막기
            event.preventDefault();
            alert("양식을 모두 올바르게 입력해주세요.");
        }
    })
})
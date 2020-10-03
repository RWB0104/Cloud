/**
 * 계정 페이지 스크립트
 *
 * @author RWB
 *
 * @since 2020.10.02 Fri 13:54
 */

/**
 * 페이지 로딩 이벤트 함수
 *
 * @returns {void}: 내부 동작 수행
 */
$(document).ready(function()
{
	init();
});

/**
 * 초기화 함수
 *
 * @returns {void}: 내부 동작 수행
 */
function init()
{
	$(".ui.form").form({
		fields: {
			email: {
				identifier: "id",
				rules: [
					{
						type: "empty",
						prompt: "아이디를 입력하세요."
					}
				]
			},
			password: {
				identifier: "password",
				rules: [
					{
						type: "empty",
						prompt: "비밀번호를 입력하세요."
					}
				]
			}
		},
		onSuccess: function(e)
		{
			e.preventDefault();

			let id = $(".ui.form").form("get value", "id");
			let password = $(".ui.form").form("get value", "password");

			login(id, password);
		}
	});
}

/**
 * 로그인 수행 함수
 */
function login(id, password)
{
	let data = {
		id: id,
		password: password
	};

	$.ajax({
		type: "POST",
		data: data,
		beforeSend: function()
		{
			$(".ui.submit").addClass("loading");
		},
		complete: function()
		{
			$(".ui.submit").removeClass("loading");
		},
		success: function(json)
		{
			let flag = json.flag;

			// 로그인 성공
			if(flag)
			{
				location.href = "/cloud/main";
			}

			// 로그인 실패
			else
			{
				let result = json.result;

				// 아이디가 일치하지 않을 경우
				if(result === 1)
				{
					$(".ui.form").form("add prompt", "id", null);

					addPrompt(json.description);
				}

				// 비밀번호가 일치하지 않을 경우
				else if(result === 2)
				{
					$(".ui.form").form("add prompt", "password", null);

					addPrompt(json.description);
				}

				// 기타 오류일 경우
				else
				{
					addPrompt(json.description);
				}
			}
		}
	});
}

/**
 * 프롬프트 표시 함수
 *
 * @param {String} text: 프롬프트 내용
 *
 * @returns {void}: 프롬프트 추가
 */
function addPrompt(text)
{
	let html = "<ul class=\"list\">\n";
	html += "<li>" + text + "</li>\n";
	html += "</ul>";

	$(".ui.form .ui.error").empty();
	$(".ui.form .ui.error").append(html);
	$(".ui.form .ui.error").show();
}
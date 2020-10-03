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
	getNav();
	getMedia(1);
});

function getNav()
{
	$.ajax({
		type: "GET",
		url: "/cloud/media/list/info",
		success: function(json)
		{
			let flag = json.flag;

			$(".ui.borderless.menu").empty();

			if(flag)
			{
				$(".ui.borderless.menu").empty();

				let min = json.result.min;
				let max = json.result.max;

				for(let i = min; i <= max; i++)
				{
					let html = "<a class=\"item\" href=\"javascript: getMedia(" + i + ")\">" + i + "</a>";

					$(".ui.borderless.menu").append(html);
				}
			}
		}
	});
}

/**
 * 로그인 수행 함수
 */
function getMedia(page)
{
	let url = "/cloud/media/list/" + page;

	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function()
		{
			// $(".ui.submit").addClass("loading");
		},
		complete: function()
		{
			// $(".ui.submit").removeClass("loading");
		},
		success: function(json)
		{
			let flag = json.flag;

			$(".ui.two.column.grid.container").empty();

			// 결고
			if(flag)
			{
				let index = (page - 1) * 10;

				for(let i = 0; i < json.result.length; i++)
				{
					let html = "<div class=\"column\">\n";
					html += "\t<div class=\"ui card\">\n";
					html += "\t\t<div class=\"image\">";
					html += "\t\t\t<video src=\"/cloud/media/view/" + (index + i + 1) + "\" width=\"100%\" controls preload=\"metadata\">\n";
					html += "\t\t</div>\n\n";
					html += "\t\t<div class=\"content\">";
					html += "\t\t\t<a class=\"description\">" + json.result[i].split('/').pop().split('\\').pop() + "</a>";
					html += "\t\t</div>";
					html += "\t</div>";
					html += "</div>";

					$(".ui.two.column.grid.container").append(html);
				}
			}

			else
			{

			}
		}
	});
}
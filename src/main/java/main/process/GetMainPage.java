package main.java.main.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.common.module.ProcessAdapter;

/**
 * 메인 함수
 * 
 * @author RWB
 *
 * @since 2020.10.03 Sat 11:59
 */
public class GetMainPage extends ProcessAdapter
{
	/**
	 * GetMainPage 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 객체
	 * @param [HttpServletResponse] response: HTTP 응답 객체
	 * 
	 * @return [void]: GetMainPage 초기화
	 */
	public GetMainPage(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 메인 페이지 리다이렉트 함수
	 * 
	 * @return [void]: 메인 페이지 리다이렉트
	 */
	public void getPage()
	{
		offCache();
		
		boolean check = isLogin();
		
		// 로그인 정보가 있을 경우
		if (check)
		{
			dispatch("/html/main/main.html");
		}
		
		// 로그인 정보가 없을 경우
		else
		{
			redirect("/cloud/login");
		}
	}
}
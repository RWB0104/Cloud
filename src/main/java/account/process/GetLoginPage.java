package main.java.account.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.common.module.ProcessAdapter;

/**
 * 로그인 페이지 프로세스 함수
 * 
 * @author RWB
 *
 * @since 2020.10.03 Sat 03:23
 */
public class GetLoginPage extends ProcessAdapter
{
	/**
	 * GetLoginPage 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 객체
	 * @param [HttpServletResponse] response: HTTP 응답 객체
	 * 
	 * @return [void]: GetLoginPage 초기화
	 */
	public GetLoginPage(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 로그인 페이지 리다이렉트 함수
	 * 
	 * @return [void]: 로그인 페이지 리다이렉트
	 */
	public void getPage()
	{
		offCache();
		
		boolean check = isLogin();
		
		// 로그인 정보가 있을 경우
		if (check)
		{
			redirect("/cloud/main");
		}
		
		// 로그인 정보가 없을 경우
		else
		{
			dispatch("/html/account/login.html");
		}
	}
}
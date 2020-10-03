package main.java.account.process;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.common.module.ProcessAdapter;

/**
 * 로그아웃 프로세스 함수
 * 
 * @author RWB
 *
 * @since 2020.10.03 Sat 12:14
 */
public class GetLogout extends ProcessAdapter
{
	/**
	 * GetLogout 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 객체
	 * @param [HttpServletResponse] response: HTTP 응답 객체
	 * 
	 * @return [void]: GetLogout 초기화
	 */
	public GetLogout(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 로그아웃 수행 함수
	 * 
	 * @return [void]: 로그아웃
	 */
	public void getOut()
	{
		Cookie cookie = getCookie("token");
		cookie.setValue("");
		cookie.setPath("/cloud");
		cookie.setMaxAge(-1);
		
		response.addCookie(cookie);
		
		redirect("/cloud/login");
	}
}
package main.java.common.util;

import javax.servlet.http.Cookie;

/**
 * 쿠키 관리 클래스
 * 
 * @author RWB
 *
 * @since 2020.10.03 Sat 03:16
 */
public class CookieManager
{
	private Cookie[] cookies;
	
	/**
	 * CookieManager 생성자
	 * 
	 * @param [Cookie[]] cookies: Cookie 객체 배열
	 * 
	 * @return [void]: Cookie 객체 배열에 값 할당
	 */
	public CookieManager(Cookie[] cookies)
	{
		this.cookies = cookies;
	}
	
	/**
	 * 쿠키 탐색 및 반환 함수
	 * 
	 * @param [String] name: 쿠키 이름
	 * 
	 * @return [Cookie] cookie: 쿠키 객체
	 */
	public Cookie getCookie(String name)
	{
		Cookie cookie = null;
		
		for (Cookie ck : cookies)
		{
			// 이름이 일치할 경우
			if (ck.getName().equals(name))
			{
				cookie = ck;
			}
		}
		
		return cookie;
	}
}
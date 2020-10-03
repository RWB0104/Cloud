package main.java.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * HTTP 통신 필터 클래스
 * 
 * @author RWB
 *
 * @since 2020.10.01 Thu 02:29
 */
@WebFilter(description = "공통 필터", urlPatterns = { "/*" })
public class CommonFilter implements Filter
{
	/**
	 * Filter 생성자 함수
	 * 
	 * @return [void]: Filter 초기화
	 */
	public CommonFilter()
	{
		// No
	}
	
	/**
	 * 필터 제거 함수
	 * 
	 * @return [void]: 동작 없음
	 */
	public void destroy()
	{
		// No
	}
	
	/**
	 * 필터 동작 함수
	 * 
	 * @param [ServletRequest] request: 서블릿 호출 객체
	 * @param [ServletResponse] response: 서블릿 응답 객체
	 * @param [FilterChain] chain: FilterChain 객체
	 * 
	 * @throws IOException: 데이터 입출력 예외
	 * @throws ServletException: 서블릿 동작 예외
	 * 
	 * @return [void]: 필터 적용
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		request.setCharacterEncoding("UTF-8");
		
		chain.doFilter(request, response);
	}
	
	/**
	 * 필터 초기화 함수
	 * 
	 * @param [FilterConfig] fConfig: FilterConfig 객체
	 * 
	 * @throws ServletException: 서블릿 동작 예외
	 * 
	 * @return [void]: 동작 없음
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		// No
	}
	
}

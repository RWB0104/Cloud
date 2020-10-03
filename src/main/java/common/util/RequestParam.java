package main.java.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 호출 파라미터 조회 클래스
 * 
 * @author RWB
 *
 * @since 2020.09.30 Wed 13:39
 */
public class RequestParam
{
	private HttpServletRequest request;
	
	/**
	 * RequestParam 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 객체
	 * 
	 * @return [void]: RequestParam 객체에 request 할당
	 */
	public RequestParam(HttpServletRequest request)
	{
		this.request = request;
	}
	
	/**
	 * 파라미터 값 문자열 혹은 기본값 반환 함수
	 * 
	 * @param [String] key: 파라미터 키
	 * @param [String] def: 기본값
	 * 
	 * @return [String] parameter: 파라미터 값
	 */
	public String getStringOrDefault(String key, String def)
	{
		String parameter;
		
		// 파라미터 값 추출 시도
		try
		{
			parameter = request.getParameter(key);
		}
		
		// 오류
		catch (Exception e)
		{
			parameter = def;
		}
		
		return parameter;
	}
	
	/**
	 * 파라미터 값 int 혹은 기본값 반환 함수
	 * 
	 * @param [String] key: 파라미터 키
	 * @param [int] def: 기본값
	 * 
	 * @return [int] parameter: 파라미터 값
	 */
	public int getIntOrDefault(String key, int def)
	{
		int parameter;
		
		// 파라미터 값 추출 시도
		try
		{
			parameter = Integer.parseInt(request.getParameter(key));
		}
		
		// 오류
		catch (Exception e)
		{
			parameter = def;
		}
		
		return parameter;
	}
	
	/**
	 * 파라미터 값 float 혹은 기본값 반환 함수
	 * 
	 * @param [String] key: 파라미터 키
	 * @param [float] def: 기본값
	 * 
	 * @return [float] parameter: 파라미터 값
	 */
	public float getFloatOrDefault(String key, float def)
	{
		float parameter;
		
		// 파라미터 값 추출 시도
		try
		{
			parameter = Float.parseFloat(request.getParameter(key));
		}
		
		// 오류
		catch (Exception e)
		{
			parameter = def;
		}
		
		return parameter;
	}
	
	/**
	 * 파라미터 값 double 혹은 기본값 반환 함수
	 * 
	 * @param [String] key: 파라미터 키
	 * @param [double] def: 기본값
	 * 
	 * @return [double] parameter: 파라미터 값
	 */
	public double getDoubleOrDefault(String key, double def)
	{
		double parameter;
		
		// 파라미터 값 추출 시도
		try
		{
			parameter = Double.parseDouble(request.getParameter(key));
		}
		
		// 오류
		catch (Exception e)
		{
			parameter = def;
		}
		
		return parameter;
	}
	
	/**
	 * 파라미터 값 boolean 혹은 기본값 반환 함수
	 * 
	 * @param [String] key: 파라미터 키
	 * @param [boolean] def: 기본값
	 * 
	 * @return [boolean] parameter: 파라미터 값
	 */
	public boolean getBooleanOrDefault(String key, boolean def)
	{
		boolean parameter;
		
		// 파라미터 값 추출 시도
		try
		{
			parameter = Boolean.parseBoolean(request.getParameter(key));
		}
		
		// 오류
		catch (Exception e)
		{
			parameter = def;
		}
		
		return parameter;
	}
}
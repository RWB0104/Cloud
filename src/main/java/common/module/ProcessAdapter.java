package main.java.common.module;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import main.java.common.bean.KeyBean;
import main.java.common.util.Cipher;
import main.java.common.util.Util;

/**
 * Process 추상 클래스
 * 
 * @author RWB
 *
 * @since 2020.09.30 Wed 13:42
 */
public abstract class ProcessAdapter
{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	/**
	 * ProcessAdapter 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 함수
	 * @param [HttpServletResponse] response: HTTP 응답 함수
	 * 
	 * @return [void]: HTTP 객체 할당
	 */
	public ProcessAdapter(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 캐싱 기능 방지 함수
	 * 
	 * @return [void]: 캐싱 기능 방지
	 */
	protected void offCache()
	{
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 로그인 여부 반환 함수
	 * 
	 * @return [boolean] result: 로그인 여부
	 */
	protected boolean isLogin()
	{
		boolean result;
		
		// 로그인 여부 확인 시도
		try
		{
			Cookie cookie = getCookie("token");
			
			// 유효한 쿠키가 있을 경우
			if (cookie != null)
			{
				String jwt = cookie.getValue();
				
				Cipher cipher = Cipher.getInstance();
				
				KeyBean bean = cipher.getKey();
				
				Jws<Claims> jws = Jwts.parser().setSigningKey(bean.getPrivateKey()).parseClaimsJws(jwt);
				
				String id = jws.getBody().get("id").toString();
				
				// 아이디가 유효할 경우
				if (id != null)
				{
					result = true;
				}
				
				// 아이디가 유효하지 않을 경우
				else
				{
					throw new NullPointerException("유효하지 않은 JWT ID");
				}
			}
			
			// 유효한 쿠키가 없을 경우
			else
			{
				throw new NullPointerException("해당하는 쿠키 없음");
			}
		}
		
		// JWT 만료
		catch (ExpiredJwtException e)
		{
			Util.sysln(this.getClass(), "만료된 JWT");
			
			result = false;
		}
		
		// JWT 손상
		catch (MalformedJwtException e)
		{
			Util.sysln(this.getClass(), "손상된 JWT");
			
			result = false;
		}
		
		// 암호화 모듈 오류
		catch (SignatureException e)
		{
			Util.sysln(this.getClass(), "유효하지 않은 암호화 방식");
			
			result = false;
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(this.getClass(), e.getMessage());
			
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 쿠키 탐색 및 반환 함수
	 * 
	 * @param [String] name: 쿠키 이름
	 * 
	 * @return [Cookie] cookie: 쿠키 객체
	 */
	protected Cookie getCookie(String name)
	{
		Cookie[] cookies = request.getCookies();
		
		Cookie cookie = null;
		
		// 쿠키 모음이 존재할 경우
		if (cookies != null)
		{
			for (Cookie ck : cookies)
			{
				// 이름이 일치할 경우
				if (ck.getName().equals(name))
				{
					cookie = ck;
				}
			}
		}
		
		// 쿠키 모음이 존재하지 않을 경우
		else
		{
			cookie = null;
		}
		
		return cookie;
	}
	
	/**
	 * HTML 페이지 리다이렉트 함수
	 * 
	 * @param [String] path: HTML 경로
	 * 
	 * @return [void]: 페이지 리다이렉트
	 */
	protected void redirect(String path)
	{
		// 페이지 리다이렉트 시도
		try
		{
			response.sendRedirect(path);
		}
		
		// 데이터 입출력 오류
		catch (IOException e)
		{
			Util.sysln(this.getClass(), "HTML 파일 입출력 오류");
			
			e.printStackTrace();
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(this.getClass(), e.getClass().getSimpleName());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * HTML 페이지 연결 함수
	 * 
	 * @param [String] path: HTML 경로
	 * 
	 * @return [void]: 페이지 연결
	 */
	protected void dispatch(String path)
	{
		// 페이지 연결 시도
		try
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
		
		// 데이터 입출력 오류
		catch (IOException e)
		{
			Util.sysln(this.getClass(), "HTML 파일 입출력 오류");
			
			e.printStackTrace();
		}
		
		// 서블릿 동작 오류
		catch (ServletException e)
		{
			Util.sysln(this.getClass(), "RequestDispatcher 동작 오류");
			
			e.printStackTrace();
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(this.getClass(), e.getClass().getSimpleName());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 문자열 리스트 JsonArray 변환 및 반환 함수
	 * 
	 * @param [List<String>] list: 문자열 리스트
	 * 
	 * @return [JsonArray] array: JsonArray 객체
	 */
	protected JsonArray toStrJsonArray(List<String> list)
	{
		JsonArray array = new JsonArray();
		
		list.forEach(data -> array.add(data));
		
		return array;
	}
	
	/**
	 * Integer 리스트 JsonArray 변환 및 반환 함수
	 * 
	 * @param [List<Integer>] list: Integer 리스트
	 * 
	 * @return [JsonArray] array: JsonArray 객체
	 */
	protected JsonArray toIntegerJsonArray(List<Integer> list)
	{
		JsonArray array = new JsonArray();
		
		list.forEach(data -> array.add(data));
		
		return array;
	}
	
	/**
	 * Float 리스트 JsonArray 변환 및 반환 함수
	 * 
	 * @param [List<Float>] list: Float 리스트
	 * 
	 * @return [JsonArray] array: JsonArray 객체
	 */
	protected JsonArray toFloatJsonArray(List<Float> list)
	{
		JsonArray array = new JsonArray();
		
		list.forEach(data -> array.add(data));
		
		return array;
	}
	
	/**
	 * Double 리스트 JsonArray 변환 및 반환 함수
	 * 
	 * @param [List<Double>] list: Double 리스트
	 * 
	 * @return [JsonArray] array: JsonArray 객체
	 */
	protected JsonArray toDoubleJsonArray(List<Double> list)
	{
		JsonArray array = new JsonArray();
		
		list.forEach(data -> array.add(data));
		
		return array;
	}
	
	/**
	 * Boolean 리스트 JsonArray 변환 및 반환 함수
	 * 
	 * @param [List<Boolean>] list: Boolean 리스트
	 * 
	 * @return [JsonArray] array: JsonArray 객체
	 */
	protected JsonArray toBooleanJsonArray(List<Boolean> list)
	{
		JsonArray array = new JsonArray();
		
		list.forEach(data -> array.add(data));
		
		return array;
	}
	
	/**
	 * JsonObject 리스트 JsonArray 변환 및 반환 함수
	 * 
	 * @param [List<JsonObject>] list: JsonObject 리스트
	 * 
	 * @return [JsonArray] array: JsonArray 객체
	 */
	protected JsonArray toJOJsonArray(List<JsonObject> list)
	{
		JsonArray array = new JsonArray();
		
		list.forEach(data -> array.add(data));
		
		return array;
	}
}
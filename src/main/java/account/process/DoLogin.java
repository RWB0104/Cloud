package main.java.account.process;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import main.java.common.bean.ResultBean;
import main.java.common.exception.NotMatchedIdException;
import main.java.common.exception.NotMatchedPasswordException;
import main.java.common.module.ProcessAdapter;
import main.java.common.util.JWTManager;
import main.java.common.util.PropertyManager;
import main.java.common.util.Util;

/**
 * 로그인 함수
 * 
 * @author RWB
 *
 * @since 2020.10.02 Thu 17:01
 */
public class DoLogin extends ProcessAdapter
{
	/**
	 * DoLogin 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 객체
	 * @param [HttpServletResponse] response: HTTP 응답 객체
	 * 
	 * @return [void]: DoLogin 초기화
	 */
	public DoLogin(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 로그인 수행 및 결과 객체 반환 함수
	 * 
	 * @param [String] id: 아이디
	 * @param [String] password: 비밀번호
	 * 
	 * @return [ResultBean<Integer>] bean: Integer를 포함한 결과 객체
	 */
	public ResultBean<Integer> getResult(String id, String password)
	{
		ResultBean<Integer> bean = new ResultBean<Integer>();
		
		int result = 3;
		
		// 로그인 시도
		try
		{
			HashMap<String, String> map = PropertyManager.getProperty("account");
			
			String origin_id = map.get("id").toString();
			String origin_password = map.get("password").toString();
			
			Thread.sleep(500);
			
			// 아이디가 일치할 경우
			if (origin_id.equals(id))
			{
				boolean isChecked = BCrypt.checkpw(password, origin_password);
				
				// 비밀번호가 일치할 경우
				if (isChecked)
				{
					Cookie cookie = JWTManager.genJWT(id);
					
					response.addCookie(cookie);
					
					result = 0;
					
					bean.setFlag(true);
					bean.setTitle("Success");
					bean.setDescription("로그인 성공");
					bean.setResult(result);
				}
				
				// 비밀번호가 일치하지 않을 경우
				else
				{
					result = 2;
					
					throw new NotMatchedIdException("비밀번호 불일치");
				}
			}
			
			// 아이디가 일치하지 않을 경우
			else
			{
				result = 1;
				
				throw new NotMatchedPasswordException("아이디 불일치");
			}
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(this.getClass(), e.getClass().getSimpleName());
			
			e.printStackTrace();
			
			bean.setFlag(false);
			bean.setTitle(e.getClass().getSimpleName());
			bean.setDescription(e.getMessage());
			bean.setResult(result);
		}
		
		return bean;
	}
}
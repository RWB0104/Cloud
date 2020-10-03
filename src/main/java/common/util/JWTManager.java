package main.java.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.java.common.bean.KeyBean;

/**
 * JWT 관리 클래스
 * 
 * @author RWB
 * 
 * @since 2020.10.01 Thu 02:54
 */
public class JWTManager
{
	/**
	 * JWT 분석 함수
	 * 
	 * @param [String] jwt: JWT
	 * 
	 * @return [Jws<Claims>] jws: JWT 정보
	 */
	public static Jws<Claims> getClaim(String jwt)
	{
		Cipher cipher = Cipher.getInstance();
		
		KeyBean bean = cipher.getKey();
		
		Jws<Claims> jws = Jwts.parser().setSigningKey(bean.getPublicKey()).parseClaimsJws(jwt);
		
		return jws;
	}
	
	/**
	 * JWT 반환 함수
	 * 
	 * @param [String] id: 아이디
	 * 
	 * @return [Cookie] cookie: JWT가 담긴 쿠키
	 */
	public static Cookie genJWT(String id)
	{
		Cookie cookie;
		
		Date date = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 3);
		
		Date expire = calendar.getTime();
		
		Cipher cipher = Cipher.getInstance();
		
		KeyBean bean = cipher.getKey();
		
		// KeyBean이 유효할 경우
		if (bean != null)
		{
			HashMap<String, Object> header = new HashMap<String, Object>();
			header.put("alg", "RS512");
			header.put("typ", "JWT");
			
			HashMap<String, Object> claim = new HashMap<String, Object>();
			claim.put("iss", "io.jsonwebtoken");
			claim.put("iat", date);
			claim.put("id", id);
			
			String jwt = Jwts.builder().setHeader(header).setClaims(claim).setExpiration(expire).signWith(SignatureAlgorithm.RS512, bean.getPrivateKey()).compact();
			
			cookie = new Cookie("token", jwt);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(-1);
			cookie.setPath("/cloud");
		}
		
		// KeyBean이 유효하지 않을 경우
		else
		{
			cookie = null;
		}
		
		return cookie;
	}
}
package main.java.common.util;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 유틸 클래스
 * 
 * @author RWB
 *
 * @since 2020.10.03 Fri 03:15
 */
public class Util
{
	/**
	 * 현재 시간 반환 함수
	 * 
	 * @return {String} result: 현재 시간의 포맷 (1900-01-01 00:00:01)
	 */
	public static String now()
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String result = timeFormat.format(time);
		
		return result;
	}
	
	/**
	 * 현재 시간을 포함한 내용 콘솔 출력 (줄바꿈)
	 * 
	 * @param {Class<?>} cls: 클래스 객체
	 * @param {Object} obj: 표시할 내용
	 * 
	 * @return {Active}: ([1900-01-01 00:00:01] 내용) 형태로 콘솔 출력
	 */
	public static void sysln(Class<?> cls, Object obj)
	{
		StringBuilder builder = new StringBuilder();
		
		// 출력 동작
		try
		{
			builder.append("[");
			builder.append(now());
			builder.append("] ");
			builder.append(cls.getSimpleName());
			builder.append(": ");
			builder.append(obj);
			
			System.out.println(builder.toString());
		}
		
		// 예외 처리
		catch (Exception e)
		{
			builder.append("[");
			builder.append(now());
			builder.append("] ");
			builder.append(cls.getSimpleName());
			builder.append(": 표시할 수 없는 객체가 입력되었습니다.");
			
			System.out.println("[" + now() + "] 표시할 수 없는 객체가 입력되었습니다.");
		}
	}
	
	/**
	 * 웹 서비스의 WEB-INF 절대경로 반환 함수
	 * 
	 * @return [String] root: WEB-INF 절대경로
	 */
	public static String getINFPath()
	{
		ClassLoader classLoader = PropertyManager.class.getClassLoader();
		
		String root = classLoader.getResource("/").getPath();
		
		File file = new File(root);
		
		root = file.getParent();
		
		return root;
	}
}
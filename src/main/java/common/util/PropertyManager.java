package main.java.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Properties 매니저 클래스
 * 
 * @author RWB
 *
 * @since 2020.09.30 Wed 14:56
 */
public class PropertyManager
{
	/**
	 * 프로퍼티 값 반환 함수
	 * 
	 * @param [String] name: 프로퍼티 파일 이름
	 * 
	 * @return [HashMap<String, String>] map: 경로 Map 혹은 null
	 */
	public static HashMap<String, String> getProperty(String name)
	{
		HashMap<String, String> map;
		
		// 경로 조회 시도
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append(Util.getINFPath());
			builder.append(File.separator);
			builder.append(name);
			builder.append(".properties");
			
			FileInputStream stream = new FileInputStream(builder.toString());
			
			Properties properties = new Properties();
			properties.load(stream);
			
			map = new HashMap<String, String>();
			
			for (Object key : properties.keySet())
			{
				map.put(key.toString(), properties.getProperty(key.toString()));
			}
		}
		
		// 파일 누락
		catch (FileNotFoundException e)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("설정파일(");
			builder.append(name);
			builder.append(".properties)이 존재하지 않음");
			
			Util.sysln(PropertyManager.class, builder.toString());
			
			e.printStackTrace();
			
			map = null;
		}
		
		// 데이터 입출력 오류
		catch (IOException e)
		{
			Util.sysln(PropertyManager.class, "데이터 입출력 오류");
			
			e.printStackTrace();
			
			map = null;
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(PropertyManager.class, e.getClass().getSimpleName());
			
			e.printStackTrace();
			
			map = null;
		}
		
		return map;
	}
}
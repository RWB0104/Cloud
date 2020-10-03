package main.java.common.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import main.java.common.bean.ResultBean;

/**
 * 컨트롤러 추상 클래스
 * 
 * @author RWB
 *
 * @since 2020.10.01 Thu 01:41
 */
public abstract class ControllerAdapter
{
	@Context
	protected HttpServletRequest request;
	
	@Context
	protected HttpServletResponse response;
	
	/**
	 * 결과 객체 응답 문자열 변환 및 반환 함수
	 * 
	 * @param [ResultBean<?>] bean: 임의의 객체를 포함한 결과 객체
	 * 
	 * @return [String] result: 결과 문자열
	 */
	protected String responser(ResultBean<?> bean)
	{
		JsonObject object = new JsonObject();
		
		// 제네릭이 JsonElement 객체일 경우
		if (bean.getResult() instanceof JsonElement)
		{
			object.addProperty("flag", bean.isFlag());
			object.addProperty("title", bean.getTitle());
			object.addProperty("description", bean.getDescription());
			object.add("result", (JsonElement) bean.getResult());
		}
		
		// 제네릭이 Boolean 객체일 경우
		else if (bean.getResult() instanceof Boolean)
		{
			object.addProperty("flag", bean.isFlag());
			object.addProperty("title", bean.getTitle());
			object.addProperty("description", bean.getDescription());
			object.addProperty("result", (Boolean) bean.getResult());
		}
		
		// 제네릭이 Character 객체일 경우
		else if (bean.getResult() instanceof Character)
		{
			object.addProperty("flag", bean.isFlag());
			object.addProperty("title", bean.getTitle());
			object.addProperty("description", bean.getDescription());
			object.addProperty("result", (Character) bean.getResult());
		}
		
		// 제네릭이 Number 객체일 경우
		else if (bean.getResult() instanceof Number)
		{
			object.addProperty("flag", bean.isFlag());
			object.addProperty("title", bean.getTitle());
			object.addProperty("description", bean.getDescription());
			object.addProperty("result", (Number) bean.getResult());
		}
		
		else if (bean.getResult() instanceof String)
		{
			object.addProperty("flag", bean.isFlag());
			object.addProperty("title", bean.getTitle());
			object.addProperty("description", bean.getDescription());
			object.addProperty("result", (String) bean.getResult());
		}
		
		// 허용되지 않은 객체일 경우
		else
		{
			object.addProperty("flag", false);
			object.addProperty("title", "ClassCastException");
			object.addProperty("description", "JsonObject에 허용되지 않는 클래스");
			object.add("result", null);
		}
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		
		String result = gson.toJson(object);
		
		return result;
	}
}
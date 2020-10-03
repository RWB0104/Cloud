package main.java.media.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import main.java.common.bean.ResultBean;
import main.java.common.module.ControllerAdapter;
import main.java.media.process.GetMediaList;

/**
 * 미디어 리스트 컨트롤러
 * 
 * @author RWB
 *
 * @since 2020.09.30 Wed 23:14
 */
@Path("list")
public class List extends ControllerAdapter
{
	/**
	 * 리스트 출력 함수
	 * 
	 * @param [int] PAGE: 페이지 번호
	 * 
	 * @return [String] result: 응답 JSON
	 */
	@GET
	@Path("{page}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getList(@PathParam("page") final int PAGE, @QueryParam("keyword") final String KEYWORD)
	{
		String result;
		
		GetMediaList adapter = new GetMediaList(request, response);
		
		// 키워드가 없을 경우
		if (KEYWORD == null)
		{
			ResultBean<JsonArray> bean = adapter.getList(PAGE);
			
			result = responser(bean);
		}
		
		// 키워드가 있을 경우
		else
		{
			ResultBean<JsonArray> bean = adapter.getList(PAGE, KEYWORD);
			
			result = responser(bean);
		}
		
		return result;
	}
	
	/**
	 * 전체 리스트 출력 함수
	 * 
	 * @return [String] result: 응답 JSON
	 */
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getList()
	{
		GetMediaList adapter = new GetMediaList(request, response);
		
		ResultBean<JsonArray> bean = adapter.getAllList();
		
		String result = responser(bean);
		
		return result;
	}
	
	/**
	 * 페이지 정보 출력 함수
	 * 
	 * @return [String] result: 응답 JSON
	 */
	@GET
	@Path("info")
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfo()
	{
		GetMediaList adapter = new GetMediaList(request, response);
		
		ResultBean<JsonObject> bean = adapter.getInfo();
		
		String result = responser(bean);
		
		return result;
	}
}

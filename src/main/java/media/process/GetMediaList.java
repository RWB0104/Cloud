package main.java.media.process;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import main.java.common.bean.ResultBean;
import main.java.common.module.ProcessAdapter;
import main.java.common.util.PropertyManager;

/**
 * 미디어 리스트 조회 함수
 * 
 * @author RWB
 *
 * @since 2020.09.30 Wed 13:26
 */
public class GetMediaList extends ProcessAdapter
{
	private final int MAX_SHOW = 10;
	private final double MAX_PAGE = 10;
	
	/**
	 * GetMediaList 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 객체
	 * @param [HttpServletResponse] response: HTTP 응답 객체
	 * 
	 * @return [void]: ProcessAdapter 초기화
	 */
	public GetMediaList(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 페이지 정보 산출 및 결과 객체 반환 함수
	 * 
	 * @return [ResultBean<JsonObject>] bean: JsonObject를 포함한 결과 객체
	 */
	public ResultBean<JsonObject> getInfo()
	{
		ResultBean<JsonObject> bean = new ResultBean<JsonObject>();
		
		// 페이지 정보 산출 시도
		try
		{
			ResultBean<JsonArray> jsonBean = getAllList();
			
			JsonArray array = jsonBean.getResult();
			
			int total = array.size();
			
			int min = 1;
			int max = (int) Math.round(total / MAX_PAGE);
			
			// 최대 페이지가 최소 페이지보다 작을 경우
			if (max < min)
			{
				max = min;
			}
			
			JsonObject object = new JsonObject();
			object.addProperty("min", min);
			object.addProperty("max", max);
			object.addProperty("ref", MAX_PAGE);
			
			bean.setFlag(true);
			bean.setTitle("Success");
			bean.setDescription("페이지 정보 조회");
			bean.setResult(object);
		}
		
		// 오류
		catch (Exception e)
		{
			e.printStackTrace();
			
			bean.setFlag(false);
			bean.setTitle(e.getClass().getSimpleName());
			bean.setDescription(e.getMessage());
			bean.setResult(null);
		}
		
		return bean;
	}
	
	/**
	 * 페이지 별 리스트 산출 및 결과 객체 반환
	 * 
	 * @param [int] page: 페이지
	 * 
	 * @return [ResultBean<JsonArray>] bean: JsonArray를 포함한 결과 객체
	 */
	public ResultBean<JsonArray> getList(int page)
	{
		ResultBean<JsonArray> bean = new ResultBean<JsonArray>();
		
		// 페이지 별 리스트 산출 시도
		try
		{
			HashMap<String, String> map = PropertyManager.getProperty("media");
			
			String path = map.get("path");
			
			LinkedList<File> list = getAllList(path);
			list = paging(list, page);
			
			LinkedList<String> name = new LinkedList<String>();
			
			for (File file : list)
			{
				name.add(file.getPath());
			}
			
			JsonArray array = toStrJsonArray(name);
			
			bean.setFlag(true);
			bean.setTitle("Success");
			bean.setDescription("미디어 리스트 조회");
			bean.setResult(array);
		}
		
		// 오류
		catch (Exception e)
		{
			e.printStackTrace();
			
			bean.setFlag(false);
			bean.setTitle(e.getClass().getSimpleName());
			bean.setDescription(e.getMessage());
			bean.setResult(null);
		}
		
		return bean;
	}
	
	/**
	 * 페이지 별 키워드 검색 리스트 산출 및 결과 객체 반환
	 * 
	 * @param [int] page: 페이지
	 * @param [String] keyword: 키워드
	 * 
	 * @return [ResultBean<JsonArray>] bean: JsonArray를 포함한 결과 객체
	 */
	public ResultBean<JsonArray> getList(int page, String keyword)
	{
		ResultBean<JsonArray> bean = new ResultBean<JsonArray>();
		
		// 페이지 별 키워드 검색 리스트 산출 시도
		try
		{
			HashMap<String, String> map = PropertyManager.getProperty("media");
			
			String path = map.get("path");
			
			LinkedList<File> list = getAllList(path);
			LinkedList<File> searching = new LinkedList<File>();
			
			for (File file : list)
			{
				// 파일 이름이 키워드를 포함할 경우
				if (file.getName().contains(keyword))
				{
					searching.add(file);
				}
			}
			
			list = paging(searching, page);
			
			LinkedList<String> name = new LinkedList<String>();
			
			for (File file : list)
			{
				name.add(file.getPath());
			}
			
			JsonArray array = toStrJsonArray(name);
			
			bean.setFlag(true);
			bean.setTitle("Success");
			bean.setDescription("미디어 리스트 조회 [" + keyword + "]");
			bean.setResult(array);
		}
		
		// 오류
		catch (Exception e)
		{
			e.printStackTrace();
			
			bean.setFlag(false);
			bean.setTitle(e.getClass().getSimpleName());
			bean.setDescription(e.getMessage());
			bean.setResult(null);
		}
		
		return bean;
	}
	
	/**
	 * 전체 리스트 반환 함수
	 * 
	 * @return [ResultBean<JsonArray>] list: 전체 리스트
	 */
	public ResultBean<JsonArray> getAllList()
	{
		ResultBean<JsonArray> bean = new ResultBean<JsonArray>();
		
		// 페이지 별 키워드 검색 리스트 산출 시도
		try
		{
			HashMap<String, String> map = PropertyManager.getProperty("media");
			
			String path = map.get("path");
			
			LinkedList<File> list = getAllList(path);
			LinkedList<String> name = new LinkedList<String>();
			
			for (File file : list)
			{
				name.add(file.getPath());
			}
			
			JsonArray array = toStrJsonArray(name);
			
			bean.setFlag(true);
			bean.setTitle("Success");
			bean.setDescription("미디어 전체 리스트 조회");
			bean.setResult(array);
		}
		
		// 오류
		catch (Exception e)
		{
			e.printStackTrace();
			
			bean.setFlag(false);
			bean.setTitle(e.getClass().getSimpleName());
			bean.setDescription(e.getMessage());
			bean.setResult(null);
		}
		
		return bean;
	}
	
	/**
	 * 전체 리스트 반환 함수
	 * 
	 * @param [String] path: 키워드
	 * 
	 * @return [LinkedList<File>] list: 전체 리스트
	 */
	private LinkedList<File> getAllList(String path)
	{
		LinkedList<File> list = new LinkedList<File>();
		
		File root = new File(path);
		File[] files = root.listFiles();
		
		for (File file : files)
		{
			// 파일 객체가 폴더일 경우
			if (file.isDirectory())
			{
				getAllList(file.getPath());
			}
			
			// 파일 객체가 파일일 경우
			else
			{
				list.add(file);
			}
		}
		
		return list;
	}
	
	/**
	 * 리스트 페이징 및 결과 리스트 반환 함수
	 * 
	 * @param [LinkedList<File>] list: 대상 리스트
	 * @param [int] page: 페이지
	 * 
	 * @return [LinkedList<File>] list: 페이징 적용 리스트
	 */
	private LinkedList<File> paging(LinkedList<File> list, int page)
	{
		LinkedList<File> paging = new LinkedList<File>();
		
		int total = list.size();
		
		int min = 1;
		int max = (int) Math.round(total / MAX_PAGE);
		
		// 최대 페이지가 최소 페이지보다 작을 경우
		if (max < min)
		{
			max = min;
		}
		
		// 페이징 번호가 올바를 경우
		if (page >= min && page <= max)
		{
			int start = (page - 1) * MAX_SHOW;
			int end = (page * MAX_SHOW) - 1;
			
			// 마지막 순번이 데이터 전체값보다 클 경우
			if (end > total)
			{
				end = total - 1;
			}
			
			for (int i = start; i <= end; i++)
			{
				paging.add(list.get(i));
			}
		}
		
		return paging;
	}
}
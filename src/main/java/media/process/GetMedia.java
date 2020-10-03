package main.java.media.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

import main.java.common.bean.ResultBean;
import main.java.common.module.ProcessAdapter;
import main.java.common.util.PropertyManager;

/**
 * 미디어 조회 함수
 * 
 * @author RWB
 *
 * @since 2020.10.03 Sat 16:59
 */
public class GetMedia extends ProcessAdapter
{
	/**
	 * GetMedia 생성자 함수
	 * 
	 * @param [HttpServletRequest] request: HTTP 호출 함수
	 * @param [HttpServletResponse] response: HTTP 응답 함수
	 * 
	 * @return [void]: HTTP 객체 할당
	 */
	public GetMedia(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	public byte[] getView()
	{
		byte[] result;
		
		// 미디어 조회 시도
		try
		{
			String[] url = request.getRequestURL().toString().split("/");
			
			HashMap<String, String> map = PropertyManager.getProperty("media");
			
			String path = map.get("path");
			String idStr = url[url.length - 1];
			
			int id = Integer.parseInt(idStr);
			
			StringBuilder builder = new StringBuilder();
			builder.append(path);
			builder.append(File.separator);
			builder.append(idStr);
			
			GetMediaList adapter = new GetMediaList(request, response);
			
			ResultBean<JsonArray> bean = adapter.getAllList();
			
			JsonArray array = bean.getResult();
			
			String fileStr = array.get(id - 1).getAsString();
			
			File file = new File(fileStr);
			
			String name = file.getName();
			String[] names = name.split("\\.");
			String ext = names[names.length - 1];
			
			if (file.exists())
			{
				FileInputStream stream = new FileInputStream(file);
				
				result = stream.readAllBytes();
				stream.close();
				
				response.setHeader("Accept-Ranges", "bytes");
				response.setContentLength(result.length);
				response.setContentType("video/" + ext);
			}
			
			else
			{
				throw new FileNotFoundException("파일이 존재하지 않음");
			}
		}
		
		// 오류
		catch (Exception e)
		{
			result = null;
		}
		
		return result;
	}
}
package main.java.main.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import main.java.common.module.ControllerAdapter;
import main.java.main.process.GetMainPage;

/**
 * 메인 컨트롤러
 * 
 * @author RWB
 *
 * @since 2020.10.03 Sat 12:04
 */
@Path("")
public class Main extends ControllerAdapter
{
	/**
	 * 메인 페이지 리다이렉트 함수
	 * 
	 * @return [void]: 메인 페이지 리다이렉트
	 */
	@GET
	public void getPage()
	{
		GetMainPage adapter = new GetMainPage(request, response);
		adapter.getPage();
	}
}
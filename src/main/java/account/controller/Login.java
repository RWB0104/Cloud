package main.java.account.controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.account.process.DoLogin;
import main.java.account.process.GetLoginPage;
import main.java.account.process.GetLogout;
import main.java.common.bean.ResultBean;
import main.java.common.module.ControllerAdapter;

/**
 * 계정 컨트롤러
 * 
 * @author RWB
 *
 * @since 2020.10.02 Thu 14:03
 */
@Path("")
public class Login extends ControllerAdapter
{
	/**
	 * 로그인 페이지 리다이렉트 함수
	 * 
	 * @return [void]: 로그인 페이지 리다이렉트
	 */
	@GET
	@Path("")
	public void getPage()
	{
		GetLoginPage adapter = new GetLoginPage(request, response);
		adapter.getPage();
	}
	
	/**
	 * 로그인 수행 및 결과 출력 함수
	 * 
	 * @param [String] ID: 아이디
	 * @param [String] PASSWORD: 비밀번호
	 * 
	 * @return [String] result: 결과 JSON
	 */
	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String doLogin(@FormParam("id") final String ID, @FormParam("password") final String PASSWORD)
	{
		DoLogin adapter = new DoLogin(request, response);
		
		ResultBean<Integer> bean = adapter.getResult(ID, PASSWORD);
		
		String result = responser(bean);
		
		return result;
	}
	
	/**
	 * 로그아웃 수행 및 로그인 페이지 리다이렉트 함수
	 * 
	 * @return [void]: 로그아웃
	 */
	@GET
	@Path("out")
	public void getLogout()
	{
		GetLogout adapter = new GetLogout(request, response);
		adapter.getOut();
	}
}
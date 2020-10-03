package main.java.media.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.media.process.GetMedia;

@WebServlet(description = "미디어 뷰 컨트롤러", urlPatterns = { "/media/view/*" })
public class View extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public View()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		GetMedia adapter = new GetMedia(request, response);
		
		byte[] result = adapter.getView();
		
		if (result != null)
		{
			response.getOutputStream().write(result);
		}
		
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}

package cqupt.swxxxy.oyc.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cqupt.swxxxy.oyc.service.MzidentmlService;
import cqupt.swxxxy.oyc.service.impl.MzidentmlServiceImpl;

public class MzidentmlParseControl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName=req.getParameter("fileName");
		MzidentmlService mzidentmlService=new MzidentmlServiceImpl(fileName);
		int result = mzidentmlService.insertAll();
		resp.getOutputStream().write(result);
		resp.setContentType("text/json; charset=UTF-8");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}

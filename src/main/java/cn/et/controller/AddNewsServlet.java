package cn.et.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.et.model.NewsUtils;

public class AddNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddNewsServlet() {}

    
    NewsUtils newsUtils = new NewsUtils();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// 获取编辑页面提交过来的数据
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		content = content.replaceAll("/News_Manager","/News_Front");
		
		// 使用UUID作为HTML文件名
		String htmlName = UUID.randomUUID().toString() + ".html";
		
		// HTML创建时间
		String createTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
		try {
			
			// 往数据库插入
			newsUtils.insertNews(title, content, htmlName, createTime);
			
			// 生成HTML页面
			newsUtils.saveHtml(title, content, htmlName, createTime);
			
			// 告知编辑添加成功
			response.getWriter().write("新闻添加成功");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

package cn.et.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import cn.et.utils.JdbcUtlis;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class NewsUtils {

	public static final String HTML_DIR = "E:/Learning/temp/171102/";
	
	/**
	 * 插入新闻
	 * 
	 * @param title
	 * @param content
	 * @param htmlName
	 * @param createTime
	 * @throws Exception
	 */
	public void insertNews(String title, String content, String htmlName, String createTime) throws Exception {
		String sql = "insert into news (title , content , htmlpath , createtime) values ('" + title + "' , '" + content
				+ "' , '" + htmlName + "' , '" + createTime + "')";
		JdbcUtlis.execute(sql);
	}

	
	/**
	 * 接收标题和内容，生成静态HTML详情页面
	 * @param titile
	 * @param content
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveHtml(String title , String content , String htmlName , String createTime) throws Exception{
		// 初始化配置对象
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

		// 设置编码
		cfg.setDefaultEncoding("UTF-8");
		
		// 模板文件(ftl)的存放目录
		cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

		// 设置数据的抓取方式
		cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_22));

		// 创建数据模型
		Map root = new HashMap();
		root.put("title", title);
		root.put("content", content);
		root.put("createTime", createTime);

		// 创建模板对象
		Template temp = cfg.getTemplate("newsDetail.ftl");

		// 创建输出流
		FileOutputStream fos = new FileOutputStream(new File(HTML_DIR + htmlName));
		
		// 保存到本地
		Writer out = new OutputStreamWriter(fos);
		temp.process(root, out);
		out.flush();
		out.close();
	}
}

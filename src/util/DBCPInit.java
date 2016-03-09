package util;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

public class DBCPInit extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config){
		try{
			String jdbcDriver=
					config.getInitParameter("jdbcDriver");
			Class.forName(jdbcDriver);
		}catch(Exception e){
			System.out.println("Driver error: "+e);
		}
	}
}

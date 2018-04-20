import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.lang.StringBuffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AutoCompleteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        String search = request.getParameter("search");
		
		try{
            StringBuffer sb = new StringBuffer();
            boolean namesAdded = false;
            
            AjaxUtility ajax = new AjaxUtility();
            sb = ajax.readdata(search);
            if(sb!=null || !sb.equals("")){
                namesAdded =true;
            }
            if (namesAdded) {
                response.setContentType("text/xml");
                response.getWriter().write(sb.toString());
            } 
        } catch(Exception ex){
            ex.printStackTrace();
        }
     
    }	
}
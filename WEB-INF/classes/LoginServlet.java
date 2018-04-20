import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
       
       Userbean userbean = new Userbean();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		
		userbean.setUsername(username);
		userbean.setPassword(password);
		userbean.setUsertype(usertype);
		
		HashMap<String, Userbean> hashObj = new HashMap<>();
		PrintWriter out = response.getWriter();

		try{
			hashObj.put(userbean.getUsername(), userbean);
			MySQLDataStoreUtilities database = new MySQLDataStoreUtilities();
			int existingUser = database.checkExistingUser(userbean.getUsername());
			if(existingUser==1){
				hashObj = (HashMap)database.userLogIn(userbean.getUsername());
				if(hashObj.get(userbean.getUsername()).getUsertype().equals(userbean.getUsertype()) &&  hashObj.get(userbean.getUsername()).getPassword().equals(userbean.getPassword())){
					HttpSession session = request.getSession();
					session.setAttribute("username",userbean.getUsername());
					if(hashObj.get(userbean.getUsername()).getUsertype().equals("Salesmen")){
						response.sendRedirect("Salesmen");
					}
					else if(hashObj.get(userbean.getUsername()).getUsertype().equals("Customer")){
						response.sendRedirect("Home");
					}
					else if(hashObj.get(userbean.getUsername()).getUsertype().equals("Storemanager")){
						response.sendRedirect("Storemanager");
					}
				}else{
					out.println ("<html><body><script>alert('Username or Password incorrect.');</script></body></html>");
				}
			}else{
					out.println ("<html><body><script>alert('User does not exist');</script></body></html>");
				}

		} catch(Exception e){
			e.printStackTrace();
		}

	}
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddCustomerSignup")
public class AddCustomerSignup extends HttpServlet {

	Userbean userbean = new Userbean();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String usertype = request.getParameter("usertype");
		
		userbean.setUsername(username);
		userbean.setPassword(password);
		userbean.setRepassword(repassword);
		userbean.setUsertype(usertype);
		
		HashMap<String, Userbean> hashObj = new HashMap<String, Userbean>();
		PrintWriter out = response.getWriter();

		try{
			hashObj.put(userbean.getUsername(), userbean);
			MySQLDataStoreUtilities database = new MySQLDataStoreUtilities();
			int existingUser = database.checkExistingUser(userbean.getUsername());
			if(existingUser==1){
				out.println("alert(User "+ userbean.getUsername()+" already exists.);");
			}
			else{
				database.addUserToDatabase(hashObj);
				if(hashObj.get(userbean.getUsername()).getUsertype().equals("Customer")){
					System.out.println("Customer added.");
					response.sendRedirect("Salesmen");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

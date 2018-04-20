import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListDataEvent;

public class AddtoAccServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        HashMap<String,Usercart> carthashObj = new HashMap<String,Usercart>();

        String getName = request.getParameter("name");
        String getId = request.getParameter("id");
        String price = request.getParameter("price");
        System.out.println(price+getId);
        Double getPrice = Double.parseDouble(price);
       
        session.setAttribute("id", getId);
        session.setAttribute("price",getPrice);
        session.setAttribute("name", getName);
                                            
        carthashObj = (HashMap)session.getAttribute("sessionCart");
        if(carthashObj == null){
            carthashObj = new HashMap<String,Usercart>();
        }
                                            
        Usercart usercart = new Usercart();
        usercart.setProductId(getId);
        usercart.setPrice(getPrice);
        usercart.setProductName(getName);
        usercart.quantity = 1;

        carthashObj.put(String.valueOf(usercart.getProductId()), usercart);
	    session.setAttribute("sessionCart", carthashObj);

        response.sendRedirect("/SinglaYatin/AddtoCartServlet");

        
    }
}
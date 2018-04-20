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

public class RemovefromCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        HashMap<String,Usercart> remhashObj = new HashMap<String,Usercart>();
        String proId = (String)request.getParameter("productid");
        remhashObj = (HashMap)session.getAttribute("sessionCart");
        remhashObj.remove(proId);
        session.setAttribute("sessionCart",remhashObj);

        response.sendRedirect("/SinglaYatin/AddtoCartServlet");

        /*

        PrintWriter out = response.getWriter();

        out.println("<!doctype html>");
    out.println("<html>");
        out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            out.println("<title>Smart Portables</title>");
            out.println("<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />");
            out.println("<link rel=\"stylesheet\" href=\"http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css\" type=\"text/css\" />");    
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
        out.println("</head>");
        out.println("<body>");
            out.println("<div id=\"container\">");
                out.println("<header>");
                    out.println("<h1><a href=\"/\">Smart<span>Portables</span></a></h1>");
                    out.println("<h2></h2>");
                out.println("</header>");
                out.println("<nav>");
                    out.println("<ul>");
                    out.println("<li class=\"start selected\"><a href=\"index.html\">Home</a></li>");
                        out.println("<li><a href=\"#\">Contact</a></li>");
                        out.println("<li><input type=\"text\" name=\"search\" class=\"search\" placeholder=\"Search..\"></li>");                       
                        out.println("<li><a href=\"\">Logout</a></li>");

                        HashMap<String,Usercart> carthashObj = new HashMap<String,Usercart>();
                        carthashObj = (HashMap)session.getAttribute("sessionCart");
                        if(carthashObj == null){
                            carthashObj = new HashMap<String,Usercart>();
                        }
                        String cartCount = String.valueOf(carthashObj.size());
	                    
                        out.println("<li><a href=\"AddtoCartServlet\">Cart("+ cartCount +")</a></li>");			
                    out.println("</ul>");
                out.println("</nav>");
	            out.println("<img class=\"header-image\" src=\"images/image.jpg\" alt=\"\" />");
                out.println("<div id=\"body\">");
	                out.println("<section id=\"content\">");
                    	out.println("<article>");
                        out.println("<h3>Products in Cart</h3>");
                            out.println("<form action='OrderServlet' type='GET'>");
                            out.println("<ul class=\"products\">");

                            Set set = remhashObj.entrySet();
                            Iterator iterator = set.iterator();
                            while(iterator.hasNext()){
                                Map.Entry entry = (Map.Entry)iterator.next();
                                Usercart usercart = (Usercart)entry.getValue();
                                out.println("<li>");
                                    out.println("<h4>" + usercart.getProductName() + "</h4>");
                                    out.println("<p> Price-"+usercart.getPrice()+", Id-"+ usercart.getProductId()+", Quantity-"+ usercart.getQuantity() + "</p>");                                   
                                    out.println("<a href='RemovefromCartServlet?productid=" + usercart.getProductId() + "'>Remove</a>");
                                out.println("</li>");
                            }
                            out.println("</ul>");
                            if(!remhashObj.isEmpty()){
                                out.println("<h3>Enter Card Details to Place Order</h3>");
                                out.println("<table>");
                                    out.println("<tr>");
                                        out.println("<td><label>Card Number</label></td>");
                                        out.println("<td><input type='text' height='20' width='30' name='cardno' required></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                        out.println("<td><label>Address</label></td>");
                                        out.println("<td><textarea name='address' rows='2' columns='50' required></textarea></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                        out.println("<td> <input type='submit' value='Place Order'></td>");
                                    out.println("</tr>");
                                out.println("</table>");
                            }
                           
                            out.println("</form>");	
                        out.println("</article>");			 
                    out.println("</section>");
        
                    out.println("<aside class=\"sidebar\">");
	
                    out.println("<ul>");	
                        out.println(" <li>");
                            out.println("<h4>Categories</h4>");
                            out.println("<ul>");
                                out.println("<li><a href=\"ShowProductServlet?productid=smartwatch\">Smart Watches</a></li>");
                                out.println("<li><a href=\"\">Speakers</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=headphone\">Headphones</a></li>");
                                out.println("<li><a href=\"\">Phones</a></li>");
                                out.println("<li><a href=\"\">Laptops</a></li>");
                                out.println("<li><a href=\"\">External Storage</a></li>");
                            out.println("</ul>");
                        out.println("</li>");
                
                        out.println(" <li>");
                            out.println("<h4>About us</h4>");
                            out.println("<ul>");
                                out.println("<li class=\"text\">");
                                    out.println("<p style=\"margin: 0;\">The secret of successful retailing is to give your customers what they want. And really, if you think about it from your point of view as a customer, you want everything: a wide assortment of good-quality merchandise; the lowest possible prices; guaranteed satisfaction with what you buy.<a href class=\"readmore\">Read More &raquo;</a></p>");
                                out.println("</li>");
                            out.println("</ul>");
                        out.println("</li>");
                
                        out.println("<li>");
                            out.println("<h4>Search site</h4>");
                            out.println("<ul>");
                                out.println("<li class=\"text\">");
                                    out.println("<form method=\"get\" class=\"searchform\" action >");
                                        out.println("<p>");
                                            out.println(" <input type=\"text\" size=\"30.5\"  name=\"s\" class=\"s\" placeholder=\"Search...\" />");
                                            
                                        out.println(" </p>");
                                    out.println(" </form>");	
                                out.println("</li>");
                            out.println("</ul>");
                        out.println("</li>");
                
                    out.println("</ul>");
                    out.println("</aside>");
    	            out.println("<div class=\"clear\"></div>");
     

                    out.println("<section>");
                        out.println("<article class=\"expanded\">");
                            
                        out.println("</article>");
                    out.println("</section>");

                out.println("</div>");
    
                out.println("<footer>");
                    out.println("<div class=\"footer-content\">");
                        out.println("<ul>");
                            out.println("<li><h4>Proin accumsan</h4></li>");
                            out.println("<li><a href=\"#\">Rutrum nulla a ultrices</a></li>");
                            out.println("<li><a href=\"#\">Blandit elementum</a></li>");
                            out.println("<li><a href=\"#\">Proin placerat accumsan</a></li>");
                        out.println("</ul>");
             
                        out.println("<ul>");
                            out.println("<li><h4>Proin accumsan</h4></li>");
                            out.println("<li><a href=\"#\">Rutrum nulla a ultrices</a></li>");
                            out.println("<li><a href=\"#\">Blandit elementum</a></li>");
                            out.println("<li><a href=\"#\">Proin placerat accumsan</a></li>");
                        out.println("</ul>");
            
                        out.println("<ul>");
                            out.println("<li><h4>Proin accumsan</h4></li>");
                            out.println("<li><a href=\"#\">Rutrum nulla a ultrices</a></li>");
                            out.println("<li><a href=\"#\">Blandit elementum</a></li>");
                            out.println("<li><a href=\"#\">Proin placerat accumsan</a></li>");
                        out.println("</ul>");
            
                        out.println("<div class=\"clear\"></div>");
                    out.println("</div>");
                    out.println("<div class=\"footer-bottom\">");
                        out.println("<p>&copy; Smart Portables 2017.</p>");
                     out.println("</div>");
                out.println("</footer>");
            out.println("</div>");
        out.println("</body>");
    out.println("</html>");  

    */
    }
}
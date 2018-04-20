import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.io.Serializable;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListDataEvent;

public class AddProduct extends HttpServlet implements Serializable {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        String username = (String)session.getAttribute("username");
        //String username = request.getParameter("username");
        
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
            out.println("<script src='ajax.js'></script>");
        out.println("</head>");
        out.println("<body>");
            out.println("<div id=\"container\">");
                out.println("<header>");
                    out.println("<h1><a href=\"/\">Smart<span>Portables</span></a></h1>");
                    out.println("<h2></h2>");
                out.println("</header>");
                 if(username!=null){
                    out.println("<p style=\"text-align:right;font-size:20px;\">Hello "+ username +"</p>");
                }
                out.println("<nav>");
                    out.println("<ul>");
                    out.println("<li class=\"start selected\"><a href=\"Home\">Home</a></li>");
                        out.println("<li><a href=\"#\">Contact</a></li>");
                        out.println("<li><input id='text1' onkeyup='ajax()' type=\"text\" name=\"search\" class=\"search\" placeholder=\"Search..\">");
                        out.println("<div id='div1'><table></table></div></li>");
                        out.println("<li><a href=\"LogoutServlet\">Logout</a></li>");			
                    out.println("</ul>");
                out.println("</nav>");
	            out.println("<img class=\"header-image\" src=\"images/image.jpg\" alt=\"\" />");
                out.println("<div id=\"body\">");
	                out.println("<section id=\"content\">");
                    	out.println("<article>");
                        out.println("<h3>Add Product Details</h3>");
                        out.println("<form method='GET' action='AddProductServlet'>");
                            out.println("<table>");
                                out.println("<tr>");
                                    out.println("<td><label>Product Name</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='productname' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Product Id</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='productid' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Price</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='price' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Retailer</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='retailer' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Discount</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='discount' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Condition</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='condition' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Manufacturer Rebate</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='manufacturerrebate' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Product On Sale</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='productonsale' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<td><label>Product Count</label></td>");
                                    out.println("<td><input type='text' height='20' width='30' name='productcount' required></td>");
                                out.println("</tr>");
                                out.println("<tr>");
                                    out.println("<select name='category'>");
                                        out.println("<option>");
                                            out.println("smartwatch");
                                        out.println("</option>");
                                        out.println("<option>");
                                            out.println("speaker");
                                        out.println("</option>");
                                        out.println("<option>");
                                            out.println("headphone");
                                        out.println("</option>");
                                        out.println("<option>");
                                            out.println("phone");
                                        out.println("</option>");
                                        out.println("<option>");
                                            out.println("laptop");
                                        out.println("</option>");
                                        out.println("<option>");
                                            out.println("externalstorage");
                                        out.println("</option>");
                                    out.println("</select>");
                                out.println("<tr>");
                                    out.println("<td> <input type='submit' class='btn btn-info' value='Submit'></td>");
                                out.println("</tr>");
                                out.println("</tr>");
                            out.println("</table>");
                        out.println("</form>");	              	
                        out.println("</article>");			 
                    out.println("</section>");
        
                    out.println("<aside class=\"sidebar\">");
	
                    out.println("<ul>");	
                        out.println(" <li>");
                            out.println("<h4>Categories</h4>");
                            out.println("<ul>");                                
                                out.println("<li><a href=\"TrendingServlet\">Trending</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=smartwatch\">Smart Watches</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=speaker\">Speakers</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=headphone\">Headphones</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=phone\">Phones</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=laptop\">Laptops</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=externalstorage\">External Storage</a></li>");
                                out.println("<li><a href=\"ShowProductServlet?productid=accessory\">Accessories</a></li>");
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
    }
}
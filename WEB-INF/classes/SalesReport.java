import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SalesReport extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");

        HashMap<String, ArrayList<Orderbean>> hashObj = new HashMap<String, ArrayList<Orderbean>>();
		ArrayList<Orderbean> arrayList = new ArrayList<Orderbean>();
	
		MySQLDataStoreUtilities database = new MySQLDataStoreUtilities();
		hashObj = database.getAllData();

        HashMap<String, ArrayList<Orderbean>> hashObj1 = new HashMap<String, ArrayList<Orderbean>>();
        hashObj1 = database.currentDateData();

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
            out.println("<script src=\"https://www.gstatic.com/charts/loader.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
            out.println("<script src='SalesReport.js'></script>");
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
                        out.println("<li><input type=\"text\" name=\"search\" class=\"search\" placeholder=\"Search..\"></li>");                       
                        out.println("<li><a href=\"LogoutServlet\">Logout</a></li>");			
                    out.println("</ul>");
                out.println("</nav>");
	            out.println("<img class=\"header-image\" src=\"images/image.jpg\" alt=\"\" />");
                out.println("<div id=\"body\">");
	                out.println("<section id=\"content\">");
                    	out.println("<article>");

                        out.println("<h3>Total sales of every product</h3>");
                        out.println("<div id='salesreport_chart'></div>");

                        out.println("<br>");
                        out.println("<br>");
                            
                        out.println("<h3>All products sold and number of items sold</h3>");
                            out.println("<form>");
                                out.println("<table border=1>");
                                for(Map.Entry<String, ArrayList<Orderbean>> map : hashObj.entrySet()){
                                    out.println("<tr><td><label>Product Name</label></td><td><label>Product Price</label></td><td><label>No. Of Product Sold</label></td><td><label>Total Sales</label></td></tr>");
                                    for(Orderbean userorder : map.getValue()){
                                        int num = ((int)userorder.getPrice() * userorder.getProductSold()); 
                                        out.println("<tr><td>"+userorder.getProductName()+"</td><td>"+userorder.getPrice()+"</td><td>"+userorder.getProductSold()+"</td><td>"+num+"</td></tr>");
                                    }
                                }
                                out.println("</table>");
                            out.println("<form>");

                            out.println("<br>");
                            out.println("<br>");

                        out.println("<h3>Total daily sales transactions</h3>");
                            out.println("<form>");
                                out.println("<table border=1>");
                                for(Map.Entry<String, ArrayList<Orderbean>> map : hashObj1.entrySet()){
                                    out.println("<tr><td><label>Date</label></td><td><label>Total Sales</label></td></tr>");
                                    for(Orderbean userorder : map.getValue()){
                                        out.println("<tr><td>"+userorder.getCurrentDate()+"</td><td>"+userorder.getTotalPrice()+"</td></tr>");
                                    }
                                }
                                out.println("</table>");
                            out.println("<form>");

                            out.println("<br>");
                            out.println("<br>");    

                           	              	
                        out.println("</article>");			 
                    out.println("</section>");
        
                    out.println("<aside class=\"sidebar\">");
	
                    out.println("<ul>");	
                        out.println(" <li>");
                            out.println("<h4>Categories</h4>");
                            out.println("<ul>");                                
                                out.println("<li><a href=\"TrendingServlet\">Trending</a></li>");
                                out.println("<li><a href=\"\">Smart Watches</a></li>");
                                out.println("<li><a href=\"\">Speakers</a></li>");
                                out.println("<li><a href=\"\">Headphones</a></li>");
                                out.println("<li><a href=\"\">Phones</a></li>");
                                out.println("<li><a href=\"\">Laptops</a></li>");
                                out.println("<li><a href=\"\">External Storage</a></li>");
                                out.println("<li><a href=\"\">Accessories</a></li>");
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
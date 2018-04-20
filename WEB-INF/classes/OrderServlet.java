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
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListDataEvent;

public class OrderServlet extends HttpServlet implements Serializable {
     
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        MySQLDataStoreUtilities database =  new MySQLDataStoreUtilities();
        HashMap<String,Usercart> carthashObj = new HashMap<String,Usercart>();
        HashMap<String,Orderbean> beanhashObj = new HashMap<String,Orderbean>();
        
        carthashObj = (HashMap)session.getAttribute("sessionCart");
        String username = (String)session.getAttribute("username"); 

        int maxOrderNum = database.getMaxOrderNum();
        int orderNum = maxOrderNum+1;
        String cardNum = request.getParameter("cardno");
        String address = request.getParameter("address");
        String status = "1";

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        dateFormat.format(currentDate);
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        Date currentDatePlusOne = c.getTime();
        String deliveryDate = dateFormat.format(currentDatePlusOne).toString();

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
	    Date date = new Date();
	    String date_current = (String)dateFormat1.format(date);

        ArrayList<Orderbean> orderList = new ArrayList<Orderbean>();
        HashMap<String,ArrayList<Orderbean>> hashObj = new HashMap<String,ArrayList<Orderbean>>();
        
        Set set = carthashObj.entrySet();
        Iterator iterator = set.iterator();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            Usercart usercart = (Usercart)entry.getValue();

            Orderbean orderbean = new Orderbean();
            orderbean.setProductId(usercart.getProductId());
            orderbean.setPrice(usercart.getPrice());
            orderbean.setProductName(usercart.getProductName());
            orderbean.setOrderNumber(orderNum);
            orderbean.setUserName(username);
            orderbean.setDeliveryDate(deliveryDate);
            orderbean.setCreditCardNumber(cardNum);
            orderbean.setAddress(address);
            orderbean.setStatus(status);
            orderbean.setCurrentDate(date_current);
            orderList.add(orderbean);
        }

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
                 if(username!=null){
                    out.println("<p style=\"text-align:right;font-size:20px;\">Hello "+ username +"</p>");
                }
                out.println("<nav>");
                    out.println("<ul>");
                    out.println("<li class=\"start selected\"><a href=\"Home\">Home</a></li>");
                        out.println("<li><a href=\"#\">Contact</a></li>");
                        out.println("<li><input type=\"text\" name=\"search\" class=\"search\" placeholder=\"Search..\"></li>");                       
                        out.println("<li><a href=\"LogoutServlet\">Logout</a></li>");
                        out.println("<li><a href=\"AddtoCartServlet\">Cart</a></li>");
                        out.println("<li><a href=\"ViewOrderServlet?orderid="+ orderNum +"\">View Order</a></li>");			
                    out.println("</ul>");
                out.println("</nav>");
	            out.println("<img class=\"header-image\" src=\"images/image.jpg\" alt=\"\" />");
                out.println("<div id=\"body\">");
	                out.println("<section id=\"content\">");
                    	out.println("<article>");
                        out.println("<h3>Review Order</h3>");
                            out.println("<ul class=\"products\">");
                            
                            hashObj.put(String.valueOf(orderNum), orderList);

                            int result=0;
                            try{
                               result = database.enterOrdertoDb(hashObj);
                               result = database.updateProduct(hashObj);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            if(result==1){
                                out.println("The order " + orderNum + " has been created.");
                                out.println("<br>");
                                out.println("Confirmation No: "+orderNum);
                                out.println("<br>");
                                out.println("Delivery Date: "+deliveryDate);
                                out.println("<br>");
                                out.println("<a href=\"ViewOrderServlet?orderid="+ orderNum +"\">View Order</a>");
                            }else{
                                out.println("The Order "+orderNum+" has not been created");
                            }
      
                            out.println("</ul>");	              	
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
                            out.println("<h2>Accessories</h2>");
                            out.println("<div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">");
                                out.println("<ol class=\"carousel-indicators\">");
                                    out.println("<li data-target=\"#myCarousel\" data-slide-to=\"0\" class=\"active\"></li>");
                                    out.println("<li data-target=\"#myCarousel\" data-slide-to=\"1\"></li>");
                                    out.println("<li data-target=\"#myCarousel\" data-slide-to=\"2\"></li>");
                                out.println("</ol>");

                                out.println("<div class=\"carousel-inner\">");
                                    out.println("<div class=\"item active\">");
                                        out.println("<img src=\"images/headphone1.jpg\" alt=\"Headphones\">");
                                        out.println("<img src=\"images/headphone2.jpg\" alt=\"Headphones\">");
                                        out.println("<img src=\"images/headphone3.jpg\" alt=\"Headphones\">");
                                    out.println("</div>");

                                    out.println("<div class=\"item\">");
                                        out.println("<img src=\"images/speaker1.jpg\" alt=\"Speaker\">");
                                        out.println("<img src=\"images/speaker2.jpg\" alt=\"Speaker\">");
                                        out.println("<img src=\"images/speaker3.jpg\" alt=\"Speaker\">");
                                    out.println("</div>");

                                    out.println("<div class=\"item\">");
                                        out.println("<img src=\"images/smartwatch1.jpg\" alt=\"Smartwatch\">");
                                        out.println("<img src=\"images/smartwatch2.jpg\" alt=\"Smartwatch\">");
                                        out.println("<img src=\"images/smartwatch3.jpg\" alt=\"Smartwatch\">");
                                    out.println("</div>");  
                                out.println("</div>"); 

                                out.println("<a class=\"left carousel-control\" href=\"#myCarousel\" data-slide=\"prev\">");
                                    out.println("<span class=\"glyphicon glyphicon-chevron-left\"></span>");
                                    out.println("<span class=\"sr-only\">Previous</span>");
                                out.println("</a>");
                                out.println("<a class=\"right carousel-control\" href=\"#myCarousel\" data-slide=\"next\">");
                                    out.println("<span class=\"glyphicon glyphicon-chevron-right\"></span>");
                                    out.println("<span class=\"sr-only\">Next</span>");
                                out.println("</a>");
                            out.println("</div>");
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
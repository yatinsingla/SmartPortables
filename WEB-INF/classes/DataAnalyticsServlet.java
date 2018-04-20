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

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class DataAnalyticsServlet extends HttpServlet{

    MongoClient mongoClient = new MongoClient("localhost", 27017);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        String username = (String)session.getAttribute("username");
        //String username = request.getParameter("username");

        DB db = mongoClient.getDB("CustomerReviews");
		DBCollection myReviews = db.getCollection("myReviews");
		BasicDBObject dbObj1 = new BasicDBObject();
		BasicDBObject dbObj2 = new BasicDBObject();
		dbObj2.put("productModelNameDB", 1);
		ArrayList listOfPro = new ArrayList();
		DBCursor dbCursor = myReviews.find(dbObj1, dbObj2);
		
        
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
                    out.println("</ul>");
                out.println("</nav>");
	            out.println("<img class=\"header-image\" src=\"images/image.jpg\" alt=\"\" />");
                out.println("<div id=\"body\">");
	                out.println("<section id=\"content\">");
                    	out.println("<article>");
                        out.println("<h3>Data Analytics</h3>");
                        out.println("<br>");
                        out.println("<a href='DataVisualization' role='button' class='btn btn-primary'>Trending Chart</a>");
                        out.println("<br>");
                        out.println("<br>");
                            
                                out.println("<form method='post' action='DataAnalytics'>");
                                    out.println("<ul class='daproducts'>");
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='productModelName' checked> Select:</li>");
                                        out.println("<li>Product Model Name:</li>");
                                        out.println("<li>");
                                            out.println("<select name='productModelName'>");
                                                out.println("<option value='ALL_PRODUCTS'>All Products</option>");
                                                while (dbCursor.hasNext()) {
                                                    BasicDBObject obj = (BasicDBObject) dbCursor.next();
                                                    listOfPro.add(obj.get("productModelNameDB"));
                                                }
                                                for (int i =0;i<listOfPro.size();i++){
                                                    out.println("<option value="+listOfPro.get(i)+">"+listOfPro.get(i)+"</option>");
                                                }
                                            out.println("</select>");
                                        out.println("</li>");
                                        out.println("<li>");
                                            out.println("<input type='checkbox' name='advanced' value='mostliked'> Most Liked<br>");
                                            out.println("<input type='checkbox' name='advanced' value='mostdisliked'> Most DisLiked - Retailer<br>");
                                            out.println("<input type='checkbox' name='advanced' value='mostdislikedz'> Most DisLiked - Zip Code");
                                        out.println("</li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='productPrice'> Select:</li>");
                                        out.println("<li>Product Price:</li>");
                                        out.println("<li><input type='number' name='productPrice' value = '0' style='width:7em'/></li>");
                                        out.println("<li>");
                                            out.println("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
                                            out.println("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
                                            out.println("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
                                        out.println("</li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                     out.println("<ul class='daproducts'>");
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='productPrice'> Select:</li>");
                                        out.println("<li>Age:</li>");
                                        out.println("<li><input type='number' name='' value = '0' style='width:7em'/></li>");
                                        out.println("<li>");
                                            out.println("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
                                            out.println("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
                                            out.println("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
                                        out.println("</li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='retailerZipcode'> Select:</li>");
                                        out.println("<li>Retailer Zip Code:</li>");
                                        out.println("<li><input type='number' name='retailerZipcode' value = '0' style='width:7em'/></li>");
                                        out.println("<li><input type='checkbox' name='advanced' value='highestPriceZip'> Highest Price </li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");     
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='retailerCity'> Select:</li>");
                                        out.println("<li>Retailer City:</li>");
                                        out.println("<li><input type='text' name='retailerCity' style='width:100px' value = 'All' /> </li>");
                                        out.println("<li><input type='checkbox' name='advanced' value='highestPriceCity'> Highest Product Price</li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");  
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='reviewRating'> Select:</li>");
                                        out.println("<li>Review Ratings:</li>");
                                        out.println("<li>");
                                            out.println("<select name='reviewRating'>");
                                                out.println("<option value='1' selected>1</option>");
                                                out.println("<option value='2'>2</option>");
                                                out.println("<option value='3'>3</option>");
                                                out.println("<option value='4'>4</option>");
                                                out.println("<option value='5'>5</option>");
                                            out.println("</select>");
                                        out.println("</li>");
                                        out.println("<li>");
                                            out.println("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
                                            out.println("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than");
                                        out.println("</li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");  
                                        out.println("<li><input type='checkbox' name='queryCheckBox' value='User_ID'> Select:</td>");                                               
                                        out.println("<li>User Id:</li>");
                                        out.println("<li><input type='text' style='width:100px' name='userID'></li>");
                                        out.println("<li></li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");
                                        out.println("<li>");
                                            out.println("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Select:");
                                        out.println("</li>");
                                        out.println("<li>Group By:</li>");
                                        out.println("<li>");
                                            out.println("<select name='groupByDropdown'>");
                                                out.println("<option value='GROUP_BY_CITY' selected>City</option>");
                                                out.println("<option value='GROUP_BY_PRODUCT'>Product Name</option> ");
                                                out.println("<option value='GROUP_BY_RETAILER_NAME'>Retailer Name</option> ");
                                            out.println("</select>");
                                        out.println("</li>");
                                        out.println("<li>");
                                            out.println("<input type='radio' name='compareCount' value='Count'> Count <br>");
                                            out.println("<input type='radio' name='compareDetail' value='Detail'> Detail");
                                        out.println("</li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");
                                        out.println("<li>");
                                            out.println("Condition:");
                                        out.println("</li>");
                                        out.println("<li>");
                                            out.println("<select name='returnValue'>");
                                                out.println("<option value='ALL'>All</option>");
                                                out.println("<option value='TOP_5' selected>Top 5 </option>");
                                                out.println("<option value='TOP_10'>Top 10 </option>");
                                                out.println("<option value='LATEST_5'>Latest 5 </option>");
                                                out.println("<option value='LATEST_10'>Latest 10 </option>");
                                            out.println("</select>");
                                        out.println("</li>");
                                        out.println("<li></li>");
                                    out.println("</ul>");
                                    // out.println("<hr>");
                                    out.println("<ul class='daproducts'>");
                                        out.println("<li>");
                                            out.println("<input type='submit' class='btn btn-primary' name = 'action' value='Submit' class='formbutton' />");            
                                        out.println("</li>");
                                    out.println("</ul>");
                                out.println("</form>");   
                            	
                           	              	
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
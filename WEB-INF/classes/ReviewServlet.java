import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;


public class ReviewServlet extends HttpServlet {

    Reviewbean reviewbean = new Reviewbean();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
	  	
        System.out.println(request.getParameter("productModelName"));  
        String productModelName = request.getParameter("productModelName");
        String productCategory = request.getParameter("productCategory");
        System.out.println(request.getParameter("productPrice"));
		int productPrice = Integer.parseInt(request.getParameter("productPrice"));
		String retailerName = request.getParameter("retailerName");
		String retailerZip = request.getParameter("retailerZip");
		String retailerCity = request.getParameter("retailerCity");
		String retailerState = request.getParameter("retailerState");
		String productOnSale = request.getParameter("productOnSale");
		String manufacturerName = request.getParameter("manufacturerName");
		String manufacturerRebate = request.getParameter("manufacturerRebate");
		String userId = request.getParameter("userId");
		String userAge = request.getParameter("userAge");
		String userGender = request.getParameter("userGender");
		String userOccupation = request.getParameter("userOccupation");
		String reviewRating = request.getParameter("reviewRating");	
		String reviewDate = request.getParameter("reviewDate");
		String reviewText = request.getParameter("reviewText");

        reviewbean.setProductModelName(productModelName);
        reviewbean.setProductCategory(productCategory);
		reviewbean.setProductPrice(productPrice);
		reviewbean.setRetailerName(retailerName);
		reviewbean.setRetailerZip(retailerZip);
		reviewbean.setRetailerCity(retailerCity);
		reviewbean.setRetailerState(retailerState);
		reviewbean.setProductOnSale(productOnSale);
		reviewbean.setManufacturerName(manufacturerName);
		reviewbean.setManufacturerRebate(manufacturerRebate);
		reviewbean.setUserId(userId);
		reviewbean.setUserAge(userAge);
		reviewbean.setUserGender(userGender);
		reviewbean.setUserOccupation(userOccupation);
		reviewbean.setReviewRating(reviewRating);
		reviewbean.setReviewDate(reviewDate);
		reviewbean.setReviewText(reviewText);    

        HashMap<String,Reviewbean> hashObj = new HashMap<String,Reviewbean>();  
		
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
                        out.println("<h2>Review</h2>");

                            try{
                                hashObj.put(reviewbean.getProductModelName(), reviewbean);
                                MongoDBDataStoreUtilities database = new MongoDBDataStoreUtilities();
                                database.addReview(hashObj);
                                out.println("<h4> Review submitted for:"+ reviewbean.getProductModelName() + "</h4>");
                                out.println("<a href='Home'>Return to main menu</a>");
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        
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


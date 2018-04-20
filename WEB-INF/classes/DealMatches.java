import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
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

public class DealMatches extends HttpServlet implements Serializable {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        String username = (String)session.getAttribute("username");
        //String username = request.getParameter("username");
        MySQLDataStoreUtilities dataStore = new MySQLDataStoreUtilities();
        
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
						//out.println("<table>");

						out.println("<h2>We beat our competitor in all aspects.</h2>");
						out.println("<h2>Price-Match Guaranteed</h2>");

						HashMap<String, ArrayList<Productbean>> productMap = new HashMap<String, ArrayList<Productbean>>();
						productMap = dataStore.viewAllProducts();
						String count = "";

						try (BufferedReader br = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/SinglaYatin/DealMatches.txt"))) {
							String line;
							while ((line = br.readLine()) != null) {
								count = count + line+ ",\n";
							}
							String[] words = count.split(",");
							String outputLine = "";
							String outputProduct = "";
							for(Map.Entry<String, ArrayList<Productbean>> map : productMap.entrySet()){
								for(Productbean bean : map.getValue()){
									for(int i=0;i<words.length;i++){
										if(words[i].contains(bean.getProductName())){
											outputLine = outputLine + words[i] + ",\n";
											outputProduct = outputProduct + bean.getProductName() + ",\n";
										}
									}
								}
							}

							String[] result = outputLine.split(",");
							//System.out.println("lenght: "+ result.length);
							if(result.length == 1){
								out.println("<center>No Offers Found.</center>");
							}else{
								for(int j=0;j<2;j++){
									out.println(result[j]+"<br><br>");
									System.out.println(result[j]);
								}
							}
							

							out.println("<h2>Deal Matches</h2>");
							out.println("<ul class=\"products\">");
							String[] productName = outputProduct.split(",");
							String[] unique = new HashSet<String>(Arrays.asList(productName)).toArray(new String[0]);
							//System.out.println("unique: " + unique.length);
							if(unique.length == 1){
								out.println("<center>No Deals Found.</center>");
							}else{
								for(Map.Entry<String, ArrayList<Productbean>> map : productMap.entrySet()){
									for(Productbean bean : map.getValue()){
										for(int i=0;i<unique.length;i++){
											if(unique[i].contains(bean.getProductName())){
												out.println("<li>");
													out.println("<a href='ViewItemServlet?id="+bean.getProductId()+"'>");
														out.println("<img src=" + bean.getProductImage() + " alt=\"Smartwatch\">");
														out.println("<h4>" + bean.getProductName() + "</h4>");
														out.println("<p> Price-"+bean.getProductPrice()+", Id-"+ bean.getProductId()+", Retailer-"+ bean.getRetailerName()+", Condition-" + bean.getProductCondition() +", Discount-" + bean.getProductDiscount() + "</p>");   
														
														out.println("<a href='WriteReviewServlet?name="+bean.getProductName()+"&price="+bean.getProductPrice()+" ' class='btn btn-info' role='button'>Write Review</a>");
														out.println("<br>");
														out.println("<br>");
														out.println("<a href='ViewReviewServlet?name="+bean.getProductName()+"' class='btn btn-info' role='button'>View Review</a>");                                
													out.println("</a>");
												out.println("</li>"); 
												System.out.println("Product detail: " + bean.getProductName() + " " + bean.getProductPrice());
											}
										}
									}
								}
							}
							

							out.println("</ul>");
							
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
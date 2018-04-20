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

public class AddtoCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        HashMap<String,Usercart> hashObj = new HashMap<String,Usercart>();
        hashObj = (HashMap)session.getAttribute("sessionCart");
       
        ServletContext scObj = request.getServletContext();
        String filename= scObj.getRealPath("WEB-INF/XMLFiles/ProductCatalog.xml");		
        SaxParser4SmartPortableXMLdatastore productDetails = new SaxParser4SmartPortableXMLdatastore(filename);	

        List<String> myList = new ArrayList<String>();
        String category = (String)session.getAttribute("category");

        ArrayList<Smartwatch> swarraylist = new ArrayList<Smartwatch>();
        HashMap<String,List<Smartwatch>> swhashObj = new HashMap<String,List<Smartwatch>>();
		swhashObj.put("smartwatch", productDetails.smartwatches);

        ArrayList<Headphone> hparraylist = new ArrayList<Headphone>();
		HashMap<String,List<Headphone>> hphashObj = new HashMap<String,List<Headphone>>();
		hphashObj.put("headphone", productDetails.headphones);

        ArrayList<Speaker> sparraylist = new ArrayList<Speaker>();
        HashMap<String,List<Speaker>> sphashObj = new HashMap<String,List<Speaker>>();
		sphashObj.put("speaker", productDetails.speakers);

        ArrayList<Phone> pharraylist = new ArrayList<Phone>();
		HashMap<String,List<Phone>> phhashObj = new HashMap<String,List<Phone>>();
		phhashObj.put("phone", productDetails.phones);

        ArrayList<Laptop> lparraylist = new ArrayList<Laptop>();
        HashMap<String,List<Laptop>> lphashObj = new HashMap<String,List<Laptop>>();
		lphashObj.put("laptop", productDetails.laptops);

        ArrayList<ExternalStorage> esarraylist = new ArrayList<ExternalStorage>();
		HashMap<String,List<ExternalStorage>> eshashObj = new HashMap<String,List<ExternalStorage>>();
		eshashObj.put("externalstorage", productDetails.externals);


        if(category.equals("smartwatch")){
            for(Map.Entry<String,List<Smartwatch>> swmap : swhashObj.entrySet()){    
                for(Smartwatch swlist : swmap.getValue()){                                           
                    myList = swlist.getAccessories();                                 
                }
            }
        }
        if(category.equals("headphone")){
            for(Map.Entry<String,List<Headphone>> hpmap : hphashObj.entrySet()){    
                for(Headphone hplist : hpmap.getValue()){                                           
                    myList = hplist.getAccessories();                                 
                }
            }
        }
        if(category.equals("speaker")){
            for(Map.Entry<String,List<Speaker>> spmap : sphashObj.entrySet()){    
                for(Speaker splist : spmap.getValue()){                                           
                    myList = splist.getAccessories();                                 
                }
            }
        }
        if(category.equals("phone")){
            for(Map.Entry<String,List<Phone>> phmap : phhashObj.entrySet()){    
                for(Phone phlist : phmap.getValue()){                                           
                    myList = phlist.getAccessories();                                 
                }
            }
        }
        if(category.equals("laptop")){
            for(Map.Entry<String,List<Laptop>> lpmap : lphashObj.entrySet()){    
                for(Laptop lplist : lpmap.getValue()){                                           
                    myList = lplist.getAccessories();                                 
                }
            }
        }
        if(category.equals("externalstorage")){
            for(Map.Entry<String,List<ExternalStorage>> esmap : eshashObj.entrySet()){    
                for(ExternalStorage eslist : esmap.getValue()){                                           
                    myList = eslist.getAccessories();                                 
                }
            }
        }
        
                             
        // ArrayList<Accessory> acarraylist = new ArrayList<Accessory>();
        HashMap<String,List<Accessory>> achashObj = new HashMap<String,List<Accessory>>();
        achashObj.put("accessorycat", productDetails.accessories);


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
                            // out.println(myList);

                            Double totalPrice = 0.0;
                            Set set = hashObj.entrySet();
                            Iterator iterator = set.iterator();
                            while(iterator.hasNext()){
                                Map.Entry entry = (Map.Entry)iterator.next();
                                Usercart usercart = (Usercart)entry.getValue();
                                totalPrice += usercart.getPrice(); 
                                    out.println("<li>");
                                        out.println("<h4>" + usercart.getProductName() + "</h4>");
                                        out.println("<p> Price-"+usercart.getPrice()+", Id-"+ usercart.getProductId()+", Quantity-"+ usercart.getQuantity() + "</p>");                                   
                                        out.println("<a href='RemovefromCartServlet?productid=" + usercart.getProductId() + "'>Remove</a>");
                                    out.println("</li>");
                            }  
                            out.println("<ul>");           
                            out.println("<li>");
                            out.println("<h4>Total Price: "+ totalPrice+"</h4>");
                            out.println("</li>");
                            out.println("</ul>");

                            out.println("</ul>");

                            if(cartCount!="0"){
                                out.println("<h3>Enter Card Details to Place Order</h3>");
                                out.println("<table>");
                                    out.println("<tr>");
                                        out.println("<td><label>Card Number</label></td>");
                                        out.println("<td><input type='text' height='20' width='30' name='cardno' required></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                        out.println("<td><label>Expiration Date</label></td>");
                                        out.println("<td><input type='text' height='20' width='30' name='cardno' required></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                        out.println("<td><label>CVV</label></td>");
                                        out.println("<td><input type='text' height='20' width='30' name='cardno' required></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                        out.println("<td><label>Name On Card</label></td>");
                                        out.println("<td><input type='text' height='20' width='30' name='cardno' required></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                        out.println("<td><label>Shipping Address</label></td>");
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
                                    out.println("<form type='POST'>");
                                        out.println("<ul class='products'>");
                                        int i=0;
                                            for(Map.Entry<String,List<Accessory>> map : achashObj.entrySet()){
                                                for(Accessory list : map.getValue()){
                                                    if(myList.contains(list.getName())){
                                                        
                                                        if(i<3){
                                                            out.println("<li>");
                                                                out.println("<img src=\"images/"+ list.getImage() +"\" alt=\"Accessory\">");
                                                                out.println("<h6>" + list.getName() + "</h6>");
                                                                out.println("<h6> Price-"+list.getPrice()+", Id-"+ list.getId()+", Condition-" + list.getCondition() + "</h6>");                                
                                                                out.println("<a href='AddtoAccServlet?id="+list.getId() +"&name="+list.getName()+"&price="+list.getPrice()+"' class='btn btn-info' role='button'>Add to Cart</a>");
                                                                out.println("<br>");
                                                                out.println("<a href='WriteReviewServlet?name="+list.getName()+"&price="+list.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                                out.println("<br>");
                                                                out.println("<a href='ViewReviewServlet?name="+list.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                            out.println("</li>");
                                                        i++;
                                                        }
                                                    }
                                                }
                                            }
                                        out.println("</ul>");
                                        out.println("</form>");
                                    out.println("</div>");

                                    out.println("<div class=\"item\">");
                                    out.println("<form type='POST'>");
                                        out.println("<ul class='products'>");
                                        int j=0;
                                        for(Map.Entry<String,List<Accessory>> map : achashObj.entrySet()){
                                            for(Accessory list : map.getValue()){
                                                if(myList.contains(list.getName())){
                                                   

                                                    if(j>2 && j<8){
                                                        out.println("<li>");
                                                            out.println("<img src=\"images/"+ list.getImage() +"\" alt=\"Accessory\">");
                                                            out.println("<h6>" + list.getName() + "</h6>");
                                                            out.println("<h6> Price-"+list.getPrice()+", Id-"+ list.getId()+", Condition-" + list.getCondition() + "</h6>");                                
                                                            out.println("<a href='AddtoAccServlet?id="+list.getId() +"&name="+list.getName()+"&price="+list.getPrice()+"' class='btn btn-info' role='button'>Add to Cart</a>");
                                                            out.println("<br>");
                                                            out.println("<a href='WriteReviewServlet?name="+list.getName()+"&price="+list.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                            out.println("<br>");
                                                            out.println("<a href='ViewReviewServlet?name="+list.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                        out.println("</li>");
                                                    j++;
                                                    }
                                                j++;
                                                }
                                            }
                                        }
                                        out.println("</ul>");
                                        out.println("</form>");
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
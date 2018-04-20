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


public class ShowProductServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {	
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
	  	String proId = request.getParameter("productid");
        String accId = "accessorycat";
        session.setAttribute("category", request.getParameter("productid"));

		ServletContext scObj = request.getServletContext();
		String filename= scObj.getRealPath("WEB-INF/XMLFiles/ProductCatalog.xml");		
		SaxParser4SmartPortableXMLdatastore productDetails = new SaxParser4SmartPortableXMLdatastore(filename);		
		
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

        ArrayList<Accessory> acarraylist = new ArrayList<Accessory>();
		HashMap<String,List<Accessory>> achashObj = new HashMap<String,List<Accessory>>();
		achashObj.put("accessory", productDetails.accessories);
		
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
                        out.println("<h2>Products</h2>");
                            out.println("<ul class=\"products\">");
                            
                            if(proId.equals("smartwatch"))
                            {
                                for(Map.Entry<String,List<Smartwatch>> swmap : swhashObj.entrySet())
                                {    
                                    for(Smartwatch swlist : swmap.getValue())
                                    {                                           
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id="+swlist.getId()+"'>");
                                                    out.println("<img src=\"images/" + swlist.getImage() + "\" alt=\"Smartwatch\">");
                                                    out.println("<h4>" + swlist.getName() + "</h4>");
                                                    out.println("<p> Price-"+swlist.getPrice()+", Id-"+ swlist.getId()+", Retailer-"+ swlist.getRetailer()+", Condition-" + swlist.getCondition() +", Discount-" + swlist.getDiscount() + "</p>");   
                                                    
                                                    out.println("<a href='WriteReviewServlet?name="+swlist.getName()+"&price="+swlist.getPrice()+" ' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+swlist.getName()+"' class='btn btn-info' role='button'>View Review</a>");                                
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                    }
                                }
                                for(Smartwatch smartwatch : productDetails.smartwatches)
                                {
                                    swarraylist.add(smartwatch);
                                }	
                            }


                            if(proId.equals("speaker"))
                            {
                                for(Map.Entry<String,List<Speaker>> spmap : sphashObj.entrySet())
                                {    
                                    for(Speaker splist : spmap.getValue())
                                    {                                           
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id="+splist.getId()+"'>");
                                                    out.println("<img src=\"images/" + splist.getImage() + "\" alt=\"Speaker\">");
                                                    out.println("<h4>" + splist.getName() + "</h4>");
                                                    out.println("<p> Price-"+splist.getPrice()+", Id-"+ splist.getId()+", Retailer-"+ splist.getRetailer()+", Condition-" + splist.getCondition() +", Discount-" + splist.getDiscount() + "</p>");                                   
                                                    
                                                    out.println("<a href='WriteReviewServlet?name="+splist.getName()+"&retailer="+splist.getRetailer()+"&price="+splist.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+splist.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                    }
                                }
                                for(Speaker speaker : productDetails.speakers)
                                {
                                    sparraylist.add(speaker);
                                }	
                            }
                            
                            

                            if(proId.equals("headphone"))
                            {
                                    for(Map.Entry<String,List<Headphone>> hpmap : hphashObj.entrySet())
                                    {    
                                        for(Headphone hplist : hpmap.getValue())
                                        {                                   
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id=" +hplist.getId()+"'>");
                                                    out.println("<img src=\"images/" + hplist.getImage() + "\" alt=\"Headphone\">");
                                                    out.println("<h4>" + hplist.getName() + "</h4>");
                                                    out.println("<p> Price-"+hplist.getPrice()+", Id-"+ hplist.getId()+", Retailer-"+ hplist.getRetailer()+", Condition-" + hplist.getCondition() +", Discount-" + hplist.getDiscount() + "</p>");                                   
                                                    
                                                    out.println("<a href='WriteReviewServlet?name="+hplist.getName()+"&retailer="+hplist.getRetailer()+"&price="+hplist.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+hplist.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                        }
                                    }                            
                                    for(Headphone headphone : productDetails.headphones)
                                    {
                                        hparraylist.add(headphone);
                                    }       		
                            }

                            if(proId.equals("phone"))
                            {
                                for(Map.Entry<String,List<Phone>> phmap : phhashObj.entrySet())
                                {    
                                    for(Phone phlist : phmap.getValue())
                                    {                                           
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id="+phlist.getId()+"'>");
                                                    out.println("<img src=\"images/" + phlist.getImage() + "\" alt=\"Phone\">");
                                                    out.println("<h4>" + phlist.getName() + "</h4>");
                                                    out.println("<p> Price-"+phlist.getPrice()+", Id-"+ phlist.getId()+", Retailer-"+ phlist.getRetailer()+", Condition-" + phlist.getCondition() +", Discount-" + phlist.getDiscount() + "</p>");                                   
                                                    
                                                    out.println("<a href='WriteReviewServlet?name="+phlist.getName()+"&retailer="+phlist.getRetailer()+"&price="+phlist.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+phlist.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                    }
                                }
                                for(Phone phone : productDetails.phones)
                                {
                                    pharraylist.add(phone);
                                }	
                            }


                            if(proId.equals("laptop"))
                            {
                                for(Map.Entry<String,List<Laptop>> lpmap : lphashObj.entrySet())
                                {    
                                    for(Laptop lplist : lpmap.getValue())
                                    {                                           
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id="+lplist.getId()+"'>");
                                                    out.println("<img src=\"images/" + lplist.getImage() + "\" alt=\"Laptop\">");
                                                    out.println("<h4>" + lplist.getName() + "</h4>");
                                                    out.println("<p> Price-"+lplist.getPrice()+", Id-"+ lplist.getId()+", Retailer-"+ lplist.getRetailer()+", Condition-" + lplist.getCondition() +", Discount-" + lplist.getDiscount() + "</p>");                                   
                                                    
                                                    out.println("<a href='WriteReviewServlet?name="+lplist.getName()+"&retailer="+lplist.getRetailer()+"&price="+lplist.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+lplist.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                    }
                                }
                                for(Laptop laptop : productDetails.laptops)
                                {
                                    lparraylist.add(laptop);
                                }	
                            }


                            if(proId.equals("externalstorage"))
                            {
                                for(Map.Entry<String,List<ExternalStorage>> esmap : eshashObj.entrySet())
                                {    
                                    for(ExternalStorage eslist : esmap.getValue())
                                    {                                           
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id="+eslist.getId()+"'>");
                                                    out.println("<img src=\"images/" + eslist.getImage() + "\" alt=\"External Storage\">");
                                                    out.println("<h4>" + eslist.getName() + "</h4>");
                                                    out.println("<p> Price-"+eslist.getPrice()+", Id-"+ eslist.getId()+", Retailer-"+ eslist.getRetailer()+", Condition-" + eslist.getCondition() +", Discount-" + eslist.getDiscount() + "</p>");                                   
                                                    
                                                    out.println("<a href='WriteReviewServlet?name="+eslist.getName()+"&retailer="+eslist.getRetailer()+"&price="+eslist.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+eslist.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                    }
                                }
                                for(ExternalStorage external : productDetails.externals)
                                {
                                    esarraylist.add(external);
                                }	
                            }


                            if(proId.equals("accessory"))
                            {
                                    for(Map.Entry<String,List<Accessory>> acmap : achashObj.entrySet())
                                    {    
                                        for(Accessory aclist : acmap.getValue())
                                        {                                   
                                            out.println("<li>");
                                                out.println("<a href='ViewItemServlet?id=" +aclist.getId()+"'>");
                                                    out.println("<img src=\"images/" + aclist.getImage() + "\" alt=\"Accessory\">");
                                                    out.println("<h4>" + aclist.getName() + "</h4>");
                                                    out.println("<p> Price-"+aclist.getPrice()+", Id-"+ aclist.getId()+", Condition-" + aclist.getCondition() + "</p>");                                   
                                                    out.println("<a href='WriteReviewServlet?name="+aclist.getName()+"&price="+aclist.getPrice()+"' class='btn btn-info' role='button'>Write Review</a>");
                                                    out.println("<br>");
                                                    out.println("<br>");
                                                    out.println("<a href='ViewReviewServlet?name="+aclist.getName()+"' class='btn btn-info' role='button'>View Review</a>");
                                                out.println("</a>");
                                            out.println("</li>");                                                               
                                        }
                                    }                            
                                    for(Accessory accessory : productDetails.accessories)
                                    {
                                        acarraylist.add(accessory);
                                    }       		
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


import java.io.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

public class DataAnalytics extends HttpServlet {
	
	MongoClient mongo = new MongoClient("localhost", 27017);
	PrintWriter out;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");		
		PrintWriter out21 = response.getWriter();
					
		DB db = mongo.getDB("CustomerReviews");
		DBCollection myReviews = db.getCollection("myReviews");
		BasicDBObject query = new BasicDBObject();
		String action = request.getParameter("action");
		boolean check = false;
		try {	
			String productModelName = request.getParameter("productModelName");
			String productPrice = request.getParameter("productPrice");
			String retailerZipcode = request.getParameter("retailerZipcode");
			String retailerCity = request.getParameter("retailerCity");
			String reviewRating = request.getParameter("reviewRating");
			String compareRating = request.getParameter("compareRating");
			String comparePrice = request.getParameter("comparePrice");
			String returnValueDropdown = request.getParameter("returnValue");
			String groupByDropdown = request.getParameter("groupByDropdown");
						
			boolean noFilter = false;
			boolean proFilter = false;
			boolean priFilter = false;
			boolean zipfilter = false;
			boolean cityFilter = false;
			boolean ratingFilter = false;
			
			boolean groupBy = false;
			boolean cityGroup = false;
			boolean progroup = false;
			boolean retailernamegroup = false;
			boolean hpcgroup = false;
			boolean hpzgroup = false;
			boolean mostlikedgroup = false;
			boolean dislike1 = false;
			boolean dislike2 = false;			
			boolean onlycount = false;
			
			DBCursor cityDbCursor=null;
						
			String[] filt = request.getParameterValues("queryCheckBox");
			String[] advanced = request.getParameterValues("advanced");
			String[] extset = request.getParameterValues("extraSettings");
			
			DBCursor dbCursor = null;
			AggregationOutput aggregateData = null;
			
			if(extset != null){				
				groupBy = true;
				for(int x = 0; x <extset.length; x++){
					switch (extset[x]){						
						case "COUNT_ONLY":
							onlycount = true;				
							break;
						case "GROUP_BY":	
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								cityGroup = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT")){
								progroup = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILER_NAME")){
								retailernamegroup = true;
							}
							break;
					}		
				}
			}			
		
		    if(advanced != null){
			    for (int i = 0; i < advanced.length; i++) {		
					switch (advanced[i]){										
						case "mostliked":{
							mostlikedgroup = true;
							groupBy = true;
							break;
						}		
						case "highestPriceZip":{
							hpzgroup = true;
							groupBy = true;
							break;
						}		
						case "highestPriceCity":{
							hpcgroup = true;
							groupBy = true;
							break;
						}		
						case "mostdisliked":{
							dislike1 = true;
							groupBy = true;
							break;
						}		
						case "mostdislikedz":{
							dislike2 = true;
							groupBy = true;
							break;
						}	
					}
				}
			}	
			
			if(filt != null && groupBy != true){
				// if(filt != null){
				for (int i = 0; i < filt.length; i++) {		
					switch (filt[i]){										
						case "productModelName":							
							proFilter = true;
							if(!productModelName.equals("ALL_PRODUCTS")){
								query.put("productModelNameDB", productModelName);
							}	
							break;
												
						case "productPrice":
							priFilter = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("productPriceDB", productPrice);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("productPriceDB", new BasicDBObject("$gt", productPrice));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("productPriceDB", new BasicDBObject("$lt", productPrice));
							}
							break;
												
						case "retailerZipcode":
							zipfilter = true;
							query.put("retailerZipDB", retailerZipcode);
							break;
												
						case "retailerCity": 
							cityFilter = true;
							if(!retailerCity.equals("All") && !cityGroup){
								query.put("retailerCityDB", retailerCity);
							}							
							break;
												
						case "reviewRating":	
							ratingFilter = true;
							if (compareRating.equals("EQUALS_TO")) {
								query.put("reviewRatingDB", reviewRating);
							}else{
								query.put("reviewRatingDB", new BasicDBObject("$gt", reviewRating));
							}
							break;
													
						default:
							noFilter = true;
							break;						
					}				
				}
			}else{
				noFilter = true;
			}
			
			constPageTop(out21);
			
			if(groupBy == true){		

				DBObject match1 = null;
				DBObject groupfields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				AggregationOutput aggregate = null;
				DBObject sort = null;
				
				if(cityGroup){	
					if((filt!=null)&&filt[1].equals("reviewRating")&&filt[0].equals("productModelName")&&productModelName.equals("ALL_PRODUCTS")&&Integer.parseInt(reviewRating)==5 && groupBy == true){
						for (int i = 0; i < filt.length; i++) {
							switch (filt[i]){										
							case "reviewRating":	
								ratingFilter = true;
								if (compareRating.equals("EQUALS_TO")) {
									query.put("reviewRatingDB", reviewRating);
								}else{
									query.put("reviewRatingDB", new BasicDBObject("$gt", reviewRating));
								}
								break;
							}
						}
						groupfields = new BasicDBObject("_id", 0);
						groupfields.put("_id", "$retailerCityDB");
						groupfields.put("count", new BasicDBObject("$sum", 1));
						groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
						groupfields.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
						groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
						groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
						groupfields.put("maxPrice", new BasicDBObject("$max", "$productPriceDB"));

						group = new BasicDBObject("$group", groupfields);

						projectFields = new BasicDBObject("_id", 0);
						projectFields.put("City", "$_id");
						projectFields.put("Review Count", "$count");
						projectFields.put("maxPrice", "$maxPrice");
						projectFields.put("productPrice", "$productPrice");
						projectFields.put("Product", "$productModelName");
						projectFields.put("Reviews", "$review");
						projectFields.put("Rating", "$rating");

						project = new BasicDBObject("$project", projectFields);

						aggregate = myReviews.aggregate(group, project);
						check = true;
						constGroupByCityContent(check, aggregate, out21, onlycount);
					}
					else{
					
                        groupfields = new BasicDBObject("_id", 0);
                        groupfields.put("_id", "$retailerCityDB");
                        groupfields.put("count", new BasicDBObject("$sum", 1));
                        groupfields.put("productmodelName", new BasicDBObject("$push", "$productModelNameDB"));
                        groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
                        groupfields.put("userName",new BasicDBObject("$push","$userNameDB"));
                        groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
                        
                        group = new BasicDBObject("$group", groupfields);

                        projectFields = new BasicDBObject("_id", 0);
                        projectFields.put("City", "$_id");
                        projectFields.put("Review Count", "$count");
                        projectFields.put("Product", "$productModelName");
                        projectFields.put("User", "$userName");
                        projectFields.put("Reviews", "$review");
                        projectFields.put("Rating", "$rating");
                        
                        project = new BasicDBObject("$project", projectFields);
                        
                        aggregate = myReviews.aggregate(group, project);
                        
                        constGroupByCityContent(check, aggregate, out21, onlycount);
					}
				}else if(mostlikedgroup){
					
					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$retailerCityDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
					groupfields.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					groupfields.put("maxPrice", new BasicDBObject("$max", "$productPriceDB"));

					group = new BasicDBObject("$group", groupfields);
					sort = new BasicDBObject("$sort",new BasicDBObject("reviewRatingDB", -1));
					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("maxPrice", "$maxPrice");
					projectFields.put("productPrice", "$productPrice");
					projectFields.put("Product", "$productModelName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					aggregate = myReviews.aggregate(sort,group, project);
				
					constructGroupByLiked(aggregate, out21, onlycount);
				
				}else if(dislike2){
					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$retailerZipDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("retailerName", new BasicDBObject("$push", "$retailerNameDB"));
					groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
					groupfields.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					groupfields.put("maxPrice", new BasicDBObject("$max", "$productPriceDB"));

					group = new BasicDBObject("$group", groupfields);
					sort = new BasicDBObject("$sort",new BasicDBObject("retailerNameDB", 1));
					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Zip", "$_id");
					projectFields.put("Retailer", "$retailerName");
					projectFields.put("Review Count", "$count");
					projectFields.put("maxPrice", "$maxPrice");
					projectFields.put("productPrice", "$productPrice");
					projectFields.put("Product", "$productModelName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					aggregate = myReviews.aggregate(sort,group, project);
				
					constructGroupByDislikedz(aggregate, out21, onlycount);

				}else if(dislike1){
					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$retailerCityDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("retailerName", new BasicDBObject("$push", "$retailerNameDB"));
					groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
					groupfields.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					groupfields.put("maxPrice", new BasicDBObject("$max", "$productPriceDB"));

					group = new BasicDBObject("$group", groupfields);
					sort = new BasicDBObject("$sort",new BasicDBObject("retailerNameDB", 1));
					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Retailer", "$retailerName");
					projectFields.put("Review Count", "$count");
					projectFields.put("maxPrice", "$maxPrice");
					projectFields.put("productPrice", "$productPrice");
					projectFields.put("Product", "$productModelName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					aggregate = myReviews.aggregate(sort,group, project);
				
					constructGroupByDisliked(aggregate, out21, onlycount);
				
                }else if(hpzgroup){
				
					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$retailerZipDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("highestPrice", new BasicDBObject("$max", "$productPriceDB"));
					groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					groupfields.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
					
					group = new BasicDBObject("$group", groupfields);
					

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Zip", "$_id");
					projectFields.put("ReviewCount", "$count");
					projectFields.put("HighestPrice", "$highestPrice");
					projectFields.put("Product", "$productModelName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);

					sort = new BasicDBObject("$sort",new BasicDBObject("productPrice", -1));
					
					aggregate = myReviews.aggregate(group,project,sort);
					
					constGroupByHighestPriceZip(aggregate, out21, onlycount);
				
                }else if(progroup){	

					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$productModelNameDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					
					group = new BasicDBObject("$group", groupfields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
					
					constructGroupByProductContent(aggregate, out21, onlycount);
					
				}else if(hpcgroup){
					
					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$retailerCityDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("highestPrice", new BasicDBObject("$max", "$productPriceDB"));
					groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					groupfields.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
					
					group = new BasicDBObject("$group", groupfields);
					sort = new BasicDBObject("$sort",new BasicDBObject("productPrice", -1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("ReviewCount", "$count");
					projectFields.put("HighestPrice", "$highestPrice");
					projectFields.put("Product", "$productModelName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
					
					constGroupByHighestPriceCity(aggregate, out21, onlycount);
					
				}else if(retailernamegroup){	
										
					groupfields = new BasicDBObject("_id", 0);
					groupfields.put("_id", "$retailerNameDB");
					groupfields.put("count", new BasicDBObject("$sum", 1));
					groupfields.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
					groupfields.put("review", new BasicDBObject("$push", "$reviewTextDB"));
					groupfields.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));
					
					group = new BasicDBObject("$group", groupfields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RetailerName", "$_id");
					projectFields.put("ReviewCount", "$count");
					projectFields.put("Product", "$productModelName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
					
					constGroupByRetailerNameContent(aggregate, out21, onlycount);
				
				}	
			}else{		
				int returnLimit = 0;
				DBObject sort = new BasicDBObject();
				if (returnValueDropdown.equals("TOP_5")){
					returnLimit = 5;
					sort.put("reviewRatingDB",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("TOP_10")){			
					returnLimit = 10;
					sort.put("reviewRatingDB",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_5")){			
					returnLimit = 5;
					sort.put("reviewDateDB",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_10")){			
					returnLimit = 10;
					sort.put("reviewDateDB",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else{	
					dbCursor = myReviews.find(query);
				}		
				constDefaultContent(dbCursor, out21, onlycount);
			}
		}catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public void constPageTop(PrintWriter out){	
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
	}
		
	
	public void constDefaultContent(DBCursor dbCursor, PrintWriter out21, boolean onlycount){
		int count = 0;
		String tableData = " ";
		String pageContent = " ";
		
		while (dbCursor.hasNext()) {			
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>Name: <th>"+bobj.getString("productModelNameDB"));
			    out21.println("<tr><td>Price: </td><td>"+ bobj.getString("productPriceDB")+"</td></tr>");
			    out21.println("<tr><td>Product Category: </td><td>"+ bobj.getString("productCategoryDB") + "</td></tr>");
			    out21.println("<tr><td>Retailer: </td><td>"+ bobj.getString("retailerNameDB") +"</td></tr>");
			    out21.println("<tr><td>Retailer Zipcode: </td><td>"+ bobj.getString("retailerZipDB") + "</td></tr>");
			    out21.println("<tr><td>Retailer City: </td><td>"+ bobj.getString("retailerCityDB") +  "</td></tr>");
			    out21.println("<tr><td>Retailer State: </td><td>"+ bobj.getString("retailerStateDB") + "</td></tr>");
			    out21.println("<tr><td>Sale: </td><td>"+ bobj.getString("productOnSaleDB") +"</td></tr>");
			    out21.println("<tr><td>User ID: </td><td>"+ bobj.getString("userIdDB") +"</td></tr>");
			    out21.println("<tr><td>User Age: </td><td>" + bobj.getString("userAgeDB") + "</td></tr>");
			    out21.println("<tr><td>User Gender: </td><td>" + bobj.getString("userGenderDB") + "</td></tr>");
			    out21.println("<tr><td>User Occupation: </td><td>"+ bobj.getString("userOccupationDB") + "</td></tr>");
			    out21.println("<tr><td>Manufacturer: </td><td>"+ bobj.getString("manufacturerNameDB") + "</td></tr>");
			    out21.println("<tr><td>Manufacturer Rebate: </td><td>"+ bobj.getString("manufacturerRebateDB") + "</td></tr>");
			    out21.println("<tr><td>Rating: </td><td>"+ bobj.getString("reviewRatingDB") +"</td></tr>");
			    out21.println("<tr><td>Date: </td><td>"+ bobj.getString("reviewDateDB") +"</td></tr>");
			    out21.println("<tr><td>Review Text: </td><td>"+ bobj.getString("reviewTextDB") +"</td></tr>");
			    out21.println("<br>");	

			    count++;

				
		}

		if(count == 0){			
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
	}
	
	public void constGroupByHighestPriceCity(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		out21.println("<center><h1> Grouped By - City with Highest Price </h1></center>");		
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
			int High = Integer.parseInt(bobj.getString("HighestPrice"));	
			rowCount++;
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>City: "+bobj.getString("City"));
			    out21.println("<th>Highest Price:"+bobj.getString("HighestPrice")+"");
			    out21.println("<th>Reviews Found:"+bobj.getString("ReviewCount")+"</tr>");		
				out21.println("<tr><td>Product: </td><td>"+productList.get(0)+"</td></tr>");
				out21.println("<tr><td>Product Price: </td><td>"+productPrice.get(0) +"</td></tr>");
				out21.println("<tr><td>Review: </td><td>"+productReview.get(0)+"</td></tr>");
				out21.println("<br>");		
				productCount =0;
		}		
		
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
	}
	
	public void constGroupByHighestPriceZip(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		out21.println("<center><h1> Grouped By - Zip </h1></center>");		
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
			rowCount++;
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>Zip: "+bobj.getString("Zip")+"</th>");
				out21.println("<th>Highest Price:"+bobj.getString("HighestPrice")+"</th>");
				out21.println("<th>Reviews Found:"+bobj.getString("ReviewCount")+"</th></tr>");
				System.out.println("ProList: "+productList+", ProPrice:"+productPrice+", ProReview: "+productReview);
				
                tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					
				out21.println("<tr><td>Product: </td><td>"+productList.get(0)+"</td></tr>");
				out21.println("<tr><td>Product Price: </td><td>"+productPrice.get(0) +"</td></tr>");
				out21.println("<tr><td>Review: </td><td>"+productReview.get(0)+"</td></tr>");
				out21.println("<br>");		
				productCount =0;
		}		
		
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
		out21.println("</table></center></body></html>");
	}
	
	public void constGroupByRetailerNameContent(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		out21.println("<html>");
		out21.println("<head><title>head</title><head>");	
		out21.println("<body>");
		out21.println("<center><h1> Grouped By - Retailer Name </h1></center>");		
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList rating = (BasicDBList) bobj.get("Rating");
			rowCount++;
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>Retailer Name: "+bobj.getString("RetailerName"));
				out21.println("<th>Reviews Found:"+bobj.getString("ReviewCount")+"</tr>");
				while (productCount < productList.size()) {
					out21.println("<tr><td>Product: </td><td>"+productList.get(productCount)+"</td></tr>");
					out21.println("<tr><td>Rating: </td><td>"+ rating.get(productCount) +"</td></tr>");
					out21.println("<tr><td>Review: </td><td>"+productReview.get(productCount)+"</td></tr>");
					out21.println("<br>");		
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					productCount++;					
				}	
				productCount =0;
		}		
		
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
		out21.println("</table></center></body></html>");
	}
	
	protected void constGroupByCityContent(boolean check,AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		if(check==true){
            out21.println("<center><h1> Grouped By - City To find the Top 5 product with rating 5 </h1></center>");
            for (DBObject result : aggregate.results()) {
                BasicDBObject bobj = (BasicDBObject) result;
                BasicDBList productList = (BasicDBList) bobj.get("Product");
                BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
                BasicDBList rating = (BasicDBList) bobj.get("Rating");
                BasicDBList productPrice = (BasicDBList) bobj.get("productPrice");

                rowCount++;
                out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>City: "+bobj.getString("City")+"<th></tr>");
                
                int tempCount = 0;
                while (productCount < productList.size()) {
                    if ((int) rating.get(productCount) == 5) {
                        tempCount++;
                        out21.println("<tr><td>Product: </td><td>"+productList.get(productCount)+"</td></tr>");
                        out21.println("<tr><td>Rating: </td><td>"+ rating.get(productCount) +"</td></tr>");
                        out21.println("<tr><td>Review: </td><td>"+productReview.get(productCount)+"</td></tr>");
                        out21.println("<br>");	
                    }
                    productCount++;
                }
                out21.println( "<tr><td>Total Number Of products with rating 5: </td><td>" + tempCount + "</td></tr><br>");
                productCount = 0;
            }

            if (rowCount == 0) {
                pageContent = "<h1>No Data Found</h1>";
                out21.println(pageContent);
            }
            out21.println("</table></center></body></html>");
		}
        else{
			out21.println("<center><h1> Grouped By - City </h1></center>");		
			for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList userName =  (BasicDBList) bobj.get("User");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>City: <th>"+bobj.getString("City"));
				out21.println("<th>Reviews Found: "+bobj.getString("Review Count")+"</tr>");
				
                while (productCount < productList.size()) {
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					out21.println("<tr><td>Product: </td><td>"+productList.get(productCount)+"</td></tr>");
					out21.println("<tr><td>User Name: </td><td>"+ userName.get(productCount) +"</td></tr>");
					out21.println("<tr><td>Rating: </td><td>"+ rating.get(productCount) +"</td></tr>");
					out21.println("<tr><td>Review: </td><td>"+productReview.get(productCount)+"</td></tr>");
					out21.println("<br>");	
				    productCount++;					
				}
                productCount =0;
		    }		
		
            if(rowCount == 0){
                pageContent = "<h1>No Data Found</h1>";
                out21.println(pageContent);
            }
            out21.println("</table></center></body></html>");
	    }
	}
	
	protected void constructGroupByLiked(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		out21.println("<h1> Grouped By - Top 5 List of Liked Product for every City </h1>");
		
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList rating = (BasicDBList) bobj.get("Rating");
			BasicDBList productPrice = (BasicDBList) bobj.get("productPrice");
			
            out21.println("\n");
			rowCount++;
			
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>City: "+bobj.getString("City")+"<th></tr>");
			
			int temp= 0;
			
			while (productCount < productList.size() &&(temp <5 )) {
				out21.println("<tr><td>Product: </td><td>"+productList.get(productCount)+"</td></tr>");
				out21.println("<tr><td>Rating: </td><td>"+ rating.get(productCount) +"</td></tr>");
				out21.println("<tr><td>Review: </td><td>"+productReview.get(productCount)+"</td></tr>");
				out21.println("<br>");	
				temp++;
				productCount++;
			}
			temp = 0;
			productCount = 0;
		}
		
		if (rowCount == 0) {
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
		out21.println("<table></center></body></html>");
	}
		
	protected void constructGroupByDisliked(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		out21.println("<h1> Grouped By - Top 5 List of Disliked Product for every City </h1>");
		
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList rating = (BasicDBList) bobj.get("Rating");
			BasicDBList productPrice = (BasicDBList) bobj.get("productPrice");
			BasicDBList retailer =(BasicDBList) bobj.get("Retailer");
			
			out21.println("\n");
			rowCount++;
			
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>City: "+bobj.getString("City")+"<th></tr>");
			
			int temp = 0;
			int temp1= 0;
			
			while (productCount < productList.size() &&(temp <5 )) {
				if(Integer.parseInt(rating.get(productCount).toString()) ==1){
					out21.println("<tr><td>Product: </td><td>"+productList.get(productCount)+"</td></tr>");
					out21.println("<tr><td>Retailer Name: </td><td>"+bobj.getString("Retailer")+"</td></tr>");
					out21.println("<tr><td>Rating: </td><td>"+ rating.get(productCount) +"</td></tr>");
					out21.println("<tr><td>Review: </td><td>"+productReview.get(productCount)+"</td></tr>");
					temp1++;
				}	
				temp++;
				productCount++;
			}
			
			out21.println( "<tr><td>Maximum dislikes for this product: </td><td> " + temp1 + "</td></tr>");
			out21.println("<br>");	
			temp =0 ;
			temp1=0;
		
			productCount = 0;
		}
		
		if (rowCount == 0) {
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
		out21.println("<table></center></body></html>");
	}
		
	protected void constructGroupByDislikedz(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		out21.println("<h1> Grouped By - Top 5 List of Disliked Product for every Zip </h1>");
		
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList rating = (BasicDBList) bobj.get("Rating");
			BasicDBList productPrice = (BasicDBList) bobj.get("productPrice");
			BasicDBList retailer =(BasicDBList) bobj.get("Retailer");
			
			out21.println("\n");
			rowCount++;
			
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>Zip: "+bobj.getString("Zip")+"<th></tr>");
			
			int temp= 0;
			int temp1=0;
			
			while (productCount < productList.size() &&(temp <5 )) {
				if(Integer.parseInt(rating.get(productCount).toString()) ==1){
					out21.println("<tr><td>Product: </td><td>"+productList.get(productCount)+"</td></tr>");
					out21.println("<tr><td>Retailer Name: </td><td>"+bobj.getString("Retailer")+"</td></tr>");
					out21.println("<tr><td>Rating: </td><td>"+ rating.get(productCount) +"</td></tr>");
					out21.println("<tr><td>Review: </td><td>"+productReview.get(productCount)+"</td></tr>");
					temp1++;
				}	
				temp++;
				productCount++;
			}
			
			out21.println( "<tr><td>Maximum dislikes for this product: </td><td> " + temp1 + "</td></tr>");
			out21.println("<br>");	
			temp =0 ;
			temp1=0;
			
			productCount = 0;
		}
		
		
		if (rowCount == 0) {
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
		out21.println("<table></center></body></html>");
	}	
		
	protected void constructGroupByProductContent(AggregationOutput aggregate, PrintWriter out21, boolean onlycount){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		out21.println("<center><h1> Grouped By - Products </h1></center>");
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
			rowCount++;
				
			out21.println("<center><table border = '1'><tr BGCOLOR='#5f94cd'><th>Product: "+bobj.getString("Product")+"<th>Reviews Found: "+bobj.getString("Review Count")+"</tr>");
			while (reviewCount < productReview.size()) {
				out21.println("<tr><td>Rating: </td><td>"+rating.get(reviewCount)+"</td></tr>");
				out21.println("<tr><td>Review: </td><td>"+productReview.get(reviewCount)+"</td></tr>");
				out21.println("<br>");
				reviewCount++;					
			}
			reviewCount = 0;
		}		
		out21.println("<table></center></body></html>");
		
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			out21.println(pageContent);
		}
	}

}
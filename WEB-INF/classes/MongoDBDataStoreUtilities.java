import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.io.*;

import com.mongodb.AggregationOutput;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MongoDBDataStoreUtilities {
	MongoClient mongo = new MongoClient("localhost", 27017);
	DBCollection myReviews=null;
	
	public void addReview(HashMap<String,Reviewbean> hashObj) throws ClassNotFoundException{
	
        Reviewbean reviewbean =  new Reviewbean();
        Set set = hashObj.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            reviewbean = (Reviewbean)entry.getValue();
        }
        String productModelName = reviewbean.getProductModelName();
        String productCategory = reviewbean.getProductCategory();
        int productPrice = reviewbean.getProductPrice();
        String retailerName = reviewbean.getRetailerName();
        String retailerZip = reviewbean.getRetailerZip();
        String retailerCity = reviewbean.getRetailerCity();
        String retailerState = reviewbean.getRetailerState();
        String productOnSale = reviewbean.getProductOnSale();
        String manufacturerName=reviewbean.getManufacturerName();
        String manufacturerRebate = reviewbean.getManufacturerRebate();
        String userId = reviewbean.getUserId();
        String userAge = reviewbean.getUserAge();
        String userGender = reviewbean.getUserGender();
        String userOccupation = reviewbean.getUserOccupation();
        String reviewRating = reviewbean.getReviewRating();	
        String reviewDate = reviewbean.getReviewDate();
        String reviewText = reviewbean.getReviewText();
                
        try{		
            DB db = mongo.getDB("CustomerReviews");
            myReviews = db.getCollection("myReviews");
            System.out.println("Collection myReviews selected successfully");
                    
            BasicDBObject doc = new BasicDBObject("title", "myReviews").
                append("productModelNameDB", productModelName).
                append("productCategoryDB", productCategory).
                append("productPriceDB", productPrice).
                append("retailerNameDB", retailerName).
                append("retailerZipDB", retailerZip).
                append("retailerCityDB", retailerCity).
                append("retailerStateDB", retailerState).
                append("productOnSaleDB", productOnSale).
                append("manufacturerNameDB",manufacturerName).
                append("manufacturerRebateDB", manufacturerRebate).
                append("userIdDB", userId).
                append("userAgeDB", userAge).
                append("userGenderDB", userGender).
                append("userOccupationDB", userOccupation).
                append("reviewRatingDB", reviewRating).
                append("reviewDateDB", reviewDate).
                append("reviewTextDB", reviewText);
                                        
            myReviews.insert(doc);
        }catch(Exception e){
            e.printStackTrace();
        }	
    }
	
	
    public HashMap<String,ArrayList<Reviewbean>> viewReview(String productName){
		HashMap<String,ArrayList<Reviewbean>> hashObj = new HashMap<String, ArrayList<Reviewbean>>();
		ArrayList<Reviewbean> reviewbean = new  ArrayList<Reviewbean>();
	
	    DB db = mongo.getDB("CustomerReviews");
	    myReviews = db.getCollection("myReviews");
			
	    BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("productModelNameDB", productName);

		DBCursor cursor = myReviews.find(searchQuery);
	     
		try{		
			while (cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				Reviewbean reviewBean = new Reviewbean();
					reviewBean.setProductModelName(obj.getString("productModelNameDB"));
					reviewBean.setProductCategory(obj.getString("productCategoryDB"));
					reviewBean.setProductPrice(Integer.parseInt(obj.getString("productPriceDB")));
					reviewBean.setRetailerName(obj.getString("retailerNameDB"));
					reviewBean.setRetailerZip(obj.getString("retailerZipDB"));
					reviewBean.setRetailerCity(obj.getString("retailerCityDB"));
					reviewBean.setRetailerState(obj.getString("retailerStateDB"));
					reviewBean.setProductOnSale(obj.getString("productOnSaleDB"));
					reviewBean.setManufacturerName(obj.getString("manufacturerNameDB"));
					reviewBean.setManufacturerRebate(obj.getString("manufacturerRebateDB"));
					reviewBean.setUserId(obj.getString("userIdDB"));
					reviewBean.setUserAge(obj.getString("userAgeDB"));
					reviewBean.setUserGender(obj.getString("userGenderDB"));
					reviewBean.setUserOccupation(obj.getString("userOccupationDB"));
					reviewBean.setReviewRating(obj.getString("reviewRatingDB"));
					reviewBean.setReviewDate(obj.getString("reviewDateDB"));
					reviewBean.setReviewText(obj.getString("reviewTextDB"));
					
                    reviewbean.add(reviewBean);
						 
					hashObj.put("ViewReview",reviewbean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}			 
		return hashObj;
	}

	public AggregationOutput topFiveBasedOnRating(){
		DB database = mongo.getDB("CustomerReviews");
		myReviews = database.getCollection("myReviews");

		AggregationOutput aggregate = null;	

		DBObject sort = new BasicDBObject();
        sort.put("reviewRatingDB",-1);

        DBObject limit = new BasicDBObject();
        DBObject orderby = new BasicDBObject();

        orderby = new BasicDBObject("$sort",sort);
        limit = new BasicDBObject("$limit",5);

        aggregate = myReviews.aggregate(orderby,limit);
		return aggregate;
	}

	public AggregationOutput topFiveZipCode(){
		DB database = mongo.getDB("CustomerReviews");
		myReviews = database.getCollection("myReviews");

		AggregationOutput aggregate = null;	

		DBObject groupFields = new BasicDBObject("_id",0);
		groupFields.put("_id","$retailerZipDB");
		groupFields.put("count", new BasicDBObject("$sum",1));
		DBObject group = new BasicDBObject("$group",groupFields);

		DBObject projectFields = new BasicDBObject("_id",0);
		projectFields.put("retailerZipDB", "$_id");
		projectFields.put("Zip Count","$count");
		DBObject project = new BasicDBObject("$project",projectFields);

		System.out.println(project);

		DBObject sort = new BasicDBObject();
        sort.put("Zip Count",-1);	

		DBObject limit = new BasicDBObject();
        DBObject orderby = new BasicDBObject();

        orderby = new BasicDBObject("$sort",sort);
        limit = new BasicDBObject("$limit",5);

		aggregate = myReviews.aggregate(group,project,orderby,limit);
		return aggregate;	
	}

	public AggregationOutput topFiveSoldProducts(){
		DB database = mongo.getDB("CustomerReviews");
		myReviews = database.getCollection("myReviews");

		AggregationOutput aggregate = null;	

		DBObject groupFields = new BasicDBObject("_id",0);
		groupFields.put("_id","$productModelNameDB");
		groupFields.put("count", new BasicDBObject("$sum",1));
		DBObject group = new BasicDBObject("$group",groupFields);

		DBObject projectFields = new BasicDBObject("_id",0);
		projectFields.put("productModelNameDB", "$_id");
		projectFields.put("Product Count","$count");
		DBObject project = new BasicDBObject("$project",projectFields);

		System.out.println(project);

		DBObject sort = new BasicDBObject();
        sort.put("Product Count",-1);	

		DBObject limit = new BasicDBObject();
        DBObject orderby = new BasicDBObject();

        orderby = new BasicDBObject("$sort",sort);
        limit = new BasicDBObject("$limit",5);

		aggregate = myReviews.aggregate(group,project,orderby,limit);
		return aggregate;	
	}

	static MongoClient mongo1 = new MongoClient("localhost", 27017);
	static DBCollection myReviews1=null;


	public static ArrayList<Reviewbean> selectReviewForChart(){
		DB database = mongo1.getDB("CustomerReviews");
		myReviews1 = database.getCollection("myReviews");
		AggregationOutput aggregate = null;	
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		ArrayList<Reviewbean> reviewList = new ArrayList<Reviewbean>();
		
		DBObject grpflds = new BasicDBObject("_id", 0);
		grpflds.put("_id", "$retailerCityDB");
		grpflds.put("count", new BasicDBObject("$sum", 1));
		grpflds.put("productModelName", new BasicDBObject("$push", "$productModelNameDB"));
		grpflds.put("productPrice", new BasicDBObject("$push", "$productPriceDB"));
		grpflds.put("review", new BasicDBObject("$push", "$reviewTextDB"));
		grpflds.put("rating", new BasicDBObject("$push", "$reviewRatingDB"));

		DBObject grp = new BasicDBObject("$group", grpflds);
		
		DBObject prjflds = new BasicDBObject("_id", 0);
		prjflds.put("City", "$_id");
		prjflds.put("Review Count", "$count");
		prjflds.put("productPrice", "$productPrice");
		prjflds.put("Product", "$productModelName");
		prjflds.put("Reviews", "$review");
		prjflds.put("Rating", "$rating");
					
		DBObject project = new BasicDBObject("$project", prjflds);
		DBObject sort = new BasicDBObject("$sort",new BasicDBObject("reviewRatingDB", 1));
		
		aggregate = myReviews1.aggregate(sort,grp, project);


		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			int reviewCount = (Integer)bobj.get("Review Count");
			BasicDBList productPrice = (BasicDBList)bobj.get("productPrice");
			BasicDBList productList = (BasicDBList) bobj.get("Product");
			BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
			BasicDBList rating = (BasicDBList) bobj.get("Rating");
			String city = (String) bobj.get("City");
			
			rowCount++;
			
			int temp= 0;
			
			while (productCount < productList.size() &&(temp <3 )) {
				
				Reviewbean reviewBean = new Reviewbean();
				reviewBean.setReviewCount(reviewCount);
				reviewBean.setProductPrice((Integer)productPrice.get(productCount));
				reviewBean.setProductModelName((String)productList.get(productCount));
				reviewBean.setReviewText((String)productReview.get(productCount));
				reviewBean.setReviewRating((String)rating.get(productCount));
				reviewBean.setRetailerCity(city);
				reviewList.add(reviewBean);
				
				//System.out.println("Name: "+productList.get(productCount));
				//System.out.println("Rating: "+ rating.get(productCount));
				//System.out.println("Review: "+productReview.get(productCount));
				//System.out.println("<br>");	
				temp++;
				productCount++;
			}
			temp = 0;
			productCount = 0;
		}
		System.out.println(reviewList);
		return reviewList;
	}

	public static ArrayList<Reviewbean> getTop3InEveryCity(){
		ArrayList<Reviewbean> reviewList = new ArrayList<Reviewbean>();
		return reviewList;
	}

}
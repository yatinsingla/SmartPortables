import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;

public class MySQLDataStoreUtilities {
	
	Connection conn=null;
	Statement statement=null;
	PreparedStatement ps =null;

	private Connection createConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		String driverName="com.mysql.jdbc.Driver";
		Class.forName(driverName).newInstance();
		String serverName="localhost";
		String portNumber="3306";
		String sid="smartportables";
		String url="jdbc:mysql://"+serverName+":"+portNumber+"/"+sid;
		String username="root";
		String password="root";
		conn=DriverManager.getConnection(url, username, password);
		
		statement=conn.createStatement();
		return conn;
	}

	public HashMap<String,ArrayList<Orderbean>> currentDateData() {
	 	
		HashMap<String,ArrayList<Orderbean>> hashObj = new HashMap<String, ArrayList<Orderbean>>();
		ArrayList<Orderbean> orderbean = new  ArrayList<Orderbean>();
			
		ResultSet resultSet;
			
			try {
				createConnection();
				String query = "SELECT currentDate, sum(price) FROM customerorders GROUP BY currentDate";
				PreparedStatement pss = conn.prepareStatement(query);
				
				resultSet = pss.executeQuery();
				
					while(resultSet.next()){
						Orderbean orderbean1 = new Orderbean();
						orderbean1.setTotalPrice(resultSet.getInt("sum(price)"));
						orderbean1.setCurrentDate(resultSet.getString("currentDate"));
						orderbean.add(orderbean1);
					}
					hashObj.put("ViewOrder", orderbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return hashObj;

	}

	public HashMap<String, ArrayList<Orderbean>> getAllData(){
			HashMap<String, ArrayList<Orderbean>> hashObj = new HashMap<String, ArrayList<Orderbean>>();
			ArrayList<Orderbean> orderlist = new ArrayList<Orderbean>();
			ResultSet resultSet= null;
			
			try {
				createConnection();
				String query = "SELECT productName, productPrice, productCount, productOnSale, productSold, manufacturerRebate  FROM productdetails";
				PreparedStatement ps = conn.prepareStatement(query);
				resultSet = ps.executeQuery();
				
				while(resultSet.next()){
					Orderbean orderbean = new Orderbean();
					orderbean.setProductName(resultSet.getString("productName"));
					orderbean.setPrice(resultSet.getDouble("productPrice"));
					orderbean.setProductCount(resultSet.getInt("productCount"));
					orderbean.setProductOnSale(resultSet.getString("productOnSale"));
					orderbean.setManufacturerRebate(resultSet.getInt("manufacturerRebate"));
					orderbean.setProductSold(resultSet.getInt("productSold"));
					orderlist.add(orderbean);
				}
				hashObj.put("hashObj", orderlist);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hashObj;
		}

		public ArrayList<Orderbean> getAllDataInArray(){
			ArrayList<Orderbean> orderlist = new ArrayList<Orderbean>();
			ResultSet resultSet= null;
			
			try {
				createConnection();
				String query = "SELECT productName, productPrice, productCount, productSold  FROM productdetails";
				PreparedStatement ps = conn.prepareStatement(query);
				resultSet = ps.executeQuery();
				
				while(resultSet.next()){
					Orderbean orderbean = new Orderbean();
					orderbean.setProductName(resultSet.getString("productName"));
					orderbean.setPrice(resultSet.getDouble("productPrice"));
					orderbean.setProductCount(resultSet.getInt("productCount"));
					orderbean.setProductSold(resultSet.getInt("productSold"));
					//System.out.println(resultSet.getString("productName"));
					orderlist.add(orderbean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return orderlist;
		}

	

        public int checkExistingUser(String username){
			ResultSet rs;
			int result=0;
			try{
				 createConnection();
				 String query = "SELECT username FROM registration WHERE username=?";
				 PreparedStatement ps = conn.prepareStatement(query);
				 ps.setString(1,username);
				 rs=ps.executeQuery();
				 
				if(!rs.next()){
					 result=0;
				}else{
					 result=1;
				}
				 
			}catch(Exception e){
					e.printStackTrace();
			}
			return result;
		}

			
		public HashMap<String, Userbean>  userLogIn(String username){
			 HashMap<String, Userbean> hashObj =  new HashMap<String, Userbean>();
			 ResultSet rs;
			 Userbean userbean = new Userbean();
			 
			 try{
				 createConnection();
				 String query = "SELECT username,password,usertype FROM registration WHERE username = ?";
				 PreparedStatement ps = conn.prepareStatement(query);
				 ps.setString(1,username);
				 rs= ps.executeQuery();
				 
				 while(rs.next())
				 {
					 userbean.setUsername(rs.getString("username"));
					 userbean.setPassword(rs.getString("password"));
					 userbean.setUsertype(rs.getString("usertype"));
				 }
				 hashObj.put(userbean.getUsername(),userbean);
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			 return hashObj;
		}
		 

    public int addUserToDatabase(HashMap<String,Userbean> hashObj) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
		Userbean userbean = new Userbean();
		Set set = hashObj.entrySet();
		Iterator iterator = set.iterator();
		
		while(iterator.hasNext()){
			Map.Entry entry = (Map.Entry)iterator.next();
			userbean = (Userbean)entry.getValue();
		}
		String username = userbean.getUsername();
		String password = userbean.getPassword();
		String repassword = userbean.getRepassword();
		String usertype = userbean.getUsertype();
		
		Boolean resultSet;
		
		createConnection();
		String query = "INSERT INTO registration(username,password,repassword,usertype)"+"VALUES(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1,username);
		ps.setString(2,password);
		ps.setString(3,repassword);
		ps.setString(4,usertype);
		
		resultSet = ps.execute();
	
		int result =0;
		
		if(resultSet){
			result = 1;
		}else{
			result = 0;
		}
		System.out.println(" The Result is "+result);
		return result ;
	}
		 
		 
		 
	public int enterOrdertoDb(HashMap<String,ArrayList<Orderbean>> hashObj ) throws SQLException,InstantiationException, ClassNotFoundException, IllegalAccessException{
		int result=0;
		for(Map.Entry<String, ArrayList<Orderbean>> map : hashObj.entrySet())
		{
			for(Orderbean orderbean : map.getValue())
			{
				String userName  	  = orderbean.getUserName();
				String productId 	  = orderbean.getProductId();
				double price     	  = orderbean.getPrice();
				int orderNumber  	  = orderbean.getOrderNumber();
				String productName	  = orderbean.getProductName();
				String creditCardNumber = orderbean.getCreditCardNumber();
				String address        = orderbean.getAddress();
				String deliveryDate   = orderbean.getDeliveryDate(); 
				String currentDate	  = orderbean.getCurrentDate();
				int status			  = Integer.parseInt(orderbean.getStatus());
				
				createConnection();
				String query = "INSERT INTO customerorders(orderNum, productId, price, productName,userName, creditCardNumber, address, deliveryDate,status,currentDate)"+"VALUES(?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1,orderNumber);
				ps.setString(2,productId);
				ps.setDouble(3,price);
				ps.setString(4,productName);
				ps.setString(5,userName);
				ps.setString(6, creditCardNumber);
				ps.setString(7, address);
				ps.setString(8, deliveryDate);
				ps.setInt(9, status);
				ps.setString(10, currentDate);
				result = ps.executeUpdate();
			}
	 	}
	 	return result;	
	}
		
		
		public int getMaxOrderNum(){
			int maxOrderNum =0;
			ResultSet resultSet;
			try {
				createConnection();
				String query = "SELECT MAX(ordernum) FROM customerorders";
				PreparedStatement ps = conn.prepareStatement(query);
				resultSet = ps.executeQuery();
				
				while(resultSet.next()){
					maxOrderNum = resultSet.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return maxOrderNum;
		}
		
		
		public int existingOrder(int orderNum){
			int result = 0;
			ResultSet resultset;
			
			try {
				createConnection();
				String query = "SELECT productid FROM customerorders WHERE ordernum = ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, orderNum);
				resultset = ps.executeQuery();
				
				if(!resultset.next()){
					result =0;
				}else{
					result =1;
				}
					
			}
			catch( Exception e) {
				e.printStackTrace();
			}
			return result;
		}
			
		
		public HashMap<String,ArrayList<Orderbean>> fetchDetailOfOrder(int orderNum){
			HashMap<String,ArrayList<Orderbean>> hashObj = new HashMap<String, ArrayList<Orderbean>>();
			ArrayList<Orderbean> userorders = new  ArrayList<Orderbean>();
			
			ResultSet resultSet;
			
			try {
				createConnection();
				String query = "SELECT productId, price, productName, userName, creditCardNumber, address, deliveryDate, status  FROM customerorders WHERE orderNum = ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, orderNum);
				resultSet = ps.executeQuery();
				
			while(resultSet.next())
					{
						Orderbean userorder = new Orderbean();
						userorder.setProductId(resultSet.getString("productId"));
						userorder.setPrice(resultSet.getDouble("price"));
						userorder.setProductName(resultSet.getString("productName"));
						userorder.setUserName(resultSet.getString("userName"));
						userorder.setCreditCardNumber(resultSet.getString("creditCardNumber"));
						userorder.setAddress(resultSet.getString("address"));
						userorder.setDeliveryDate(resultSet.getString("deliveryDate"));
						userorder.setStatus(String.valueOf(resultSet.getInt("status")));
						userorders.add(userorder);
					}
					hashObj.put("ViewOrder", userorders);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return hashObj;
		}		

		
		public HashMap<String, ArrayList<Orderbean>> getOrderForSalesmen(){
			HashMap<String, ArrayList<Orderbean>> hashObj = new HashMap<String, ArrayList<Orderbean>>();
			ArrayList<Orderbean> orderlist = new ArrayList<Orderbean>();
			ResultSet resultSet= null;
			
			try {
				createConnection();
				String query = "SELECT orderNum, productId, productName, price, userName, creditCardNumber, address, deliveryDate, status  FROM customerorders";
				PreparedStatement ps = conn.prepareStatement(query);
				resultSet = ps.executeQuery();
				
				while(resultSet.next()){
					Orderbean userbean = new Orderbean();
					userbean.setOrderNumber(resultSet.getInt("orderNum"));
					userbean.setProductId(resultSet.getString("productId"));
					userbean.setProductName(resultSet.getString("productName"));
					userbean.setPrice(resultSet.getDouble("price"));
					userbean.setUserName(resultSet.getString("userName"));
					userbean.setCreditCardNumber(resultSet.getString("creditCardNumber"));
					userbean.setAddress(resultSet.getString("address"));
					userbean.setDeliveryDate(resultSet.getString("deliveryDate"));
					userbean.setStatus(String.valueOf(resultSet.getInt("status")));
					orderlist.add(userbean);
				}
				hashObj.put("hashObj", orderlist);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hashObj;
		}

		
		public int orderUpdate(int orderNum){
			int result= 0;
			try {
				createConnection();
				String query = "UPDATE customerorders SET status=2 WHERE ordernum = ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, orderNum);
				result = ps.executeUpdate();
			} catch (Exception e) {				
				e.printStackTrace();
			}
			return result;
		}

		
		public int orderCancel(int orderNum){
			int result =0;		
			try{
				createConnection();
				String query = "DELETE FROM customerorders WHERE ordernum = ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, orderNum);
				result = ps.executeUpdate();	
			}catch(Exception e){
				e.printStackTrace();
			}			
			return result;
		}


		public int updateProduct(HashMap<String,ArrayList<Orderbean>> hashObj ) throws SQLException,InstantiationException, ClassNotFoundException, IllegalAccessException{
			int result=0;
			for(Map.Entry<String, ArrayList<Orderbean>> map : hashObj.entrySet())
			{
				for(Orderbean orderbean : map.getValue())
				{
					String productName	  = orderbean.getProductName();
					createConnection();
					String query = "UPDATE productdetails SET productCount=productCount-1, productSold=productSold+1 WHERE productName = ?";
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1,productName);
					result = ps.executeUpdate();
				}
			}
			return result;	
		}


		public int addingProductsFromHashMap(HashMap<String, ArrayList<Productbean>> hashObj)
	  	{
		  int result= 0;
		  
		  Productbean productBean = new Productbean();
		  for(Map.Entry<String, ArrayList<Productbean>> map : hashObj.entrySet())
		  {
			  for(Productbean bean : map.getValue())
			  {
				  productBean = bean;
				  try {
						createConnection();
						String que ="INSERT INTO productdetails (productId, productCategory, productPrice, productName, productImage, productCondition, retailerName, productDiscount, manufacturerRebate, productOnSale, productCount, productSold, productAccessories) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps = conn.prepareStatement(que);
						ps.setInt(1, productBean.getProductId());
						ps.setString(2, productBean.getProductCategory());
						ps.setDouble(3, productBean.getProductPrice());
						ps.setString(4, productBean.getProductName());
						ps.setString(5, "images/"+productBean.getProductCategory()+".jpg");
						ps.setString(6, productBean.getProductCondition());
						ps.setString(7, productBean.getRetailerName());
						ps.setInt(8, productBean.getProductDiscount());
						ps.setInt(9,productBean.getManufacturerRebate());
						ps.setString(10,productBean.getProductOnSale());
						ps.setInt(11,productBean.getProductCount());
						ps.setInt(12,0);
						ps.setString(13,"smartwatch_acc1,smartwatch_acc2,smartwatch_acc3,smartwatch_acc4,smartwatch_acc5,smartwatch_acc6");
						result = ps.executeUpdate();
						System.out.println(productBean.getProductId()+productBean.getProductCategory()+productBean.getProductPrice()+productBean.getProductCondition()+productBean.getRetailerName());
					} catch (Exception e) {
						System.out.println("error excption");
						e.printStackTrace();
					}
			  } 
		  }
		  return result;
	 	}

		

		public HashMap<String,ArrayList<Productbean>> viewAllProducts()
	  	{
			HashMap<String,ArrayList<Productbean>> hashObj = new HashMap<String,ArrayList<Productbean>>();
			ResultSet rs = null;
			ArrayList<Productbean> pbean = new ArrayList<Productbean>();
			try {
				createConnection();
				String query = "SELECT productId, productName, productCategory, productPrice, productImage, productCondition, retailerName, productDiscount, manufacturerRebate, productOnSale, productCount, productSold, productAccessories FROM productdetails";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				rs = preparedStatement.executeQuery();
				
				while(rs.next())
				{
					Productbean bp = new Productbean();
					bp.setProductId(rs.getInt("productId"));
					bp.setProductName(rs.getString("productName"));
					bp.setProductCategory(rs.getString("productCategory"));
					bp.setProductPrice(rs.getDouble("productPrice"));
					bp.setProductImage(rs.getString("productImage"));
					bp.setProductCondition(rs.getString("productCondition"));
					bp.setRetailerName(rs.getString("retailerName"));
					bp.setProductDiscount(rs.getInt("productDiscount"));
					bp.setManufacturerRebate(rs.getInt("manufacturerRebate"));
					bp.setProductOnSale(rs.getString("productOnSale"));
					bp.setProductCount(rs.getInt("productCount"));
					bp.setProductSold(rs.getInt("productSold"));
					bp.setProductAccessories(rs.getString("productAccessories"));
					pbean.add(bp);
				}
				hashObj.put("PRODUCTS", pbean);
			} catch (Exception e) {
				e.printStackTrace();
			}   
			return hashObj;
	  	}


		public HashMap<String, ArrayList<Productbean>> getProductsForStoreManager()
		{
			HashMap<String, ArrayList<Productbean>> hsb = new HashMap<String, ArrayList<Productbean>>();
			ArrayList<Productbean> orderlist = new ArrayList<Productbean>();
			ResultSet resultSet= null;
			
			try {
				createConnection();
				String query1 = "SELECT retailerName, productName, productId, productCondition, productPrice, productAccessories,productCategory FROM productdetails";
				PreparedStatement ps001 = conn.prepareStatement(query1);
				resultSet = ps001.executeQuery();
				
				while(resultSet.next())
				{
					Productbean beanproduct = new Productbean();
					beanproduct.setRetailerName(resultSet.getString("retailerName"));
					beanproduct.setProductName(resultSet.getString("productName"));
					beanproduct.setProductId(resultSet.getInt("productId"));
					beanproduct.setProductCondition(resultSet.getString("productCondition"));
					beanproduct.setProductPrice(resultSet.getDouble("productPrice"));
					beanproduct.setProductAccessories(resultSet.getString("productAccessories"));
					beanproduct.setProductCategory(resultSet.getString("productCategory"));
					orderlist.add(beanproduct);

				}
				
				hsb.put("hsb", orderlist);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hsb;
		}

		public HashMap<String,ArrayList<Productbean>> viewingProductsById(int productId)
		{
			HashMap<String,ArrayList<Productbean>> hpb = new HashMap<String,ArrayList<Productbean>>();
			ResultSet resultSet = null;
			ArrayList<Productbean> plist = new ArrayList<Productbean>();
			try {
				createConnection();
				String qry = "SELECT retailerName, productName, productId, productCondition, productPrice, productAccessories,productCategory FROM productdetails WHERE productId  = ? ";
				PreparedStatement pss = conn.prepareStatement(qry);
				pss.setInt(1, productId);
				resultSet = pss.executeQuery();
				
				while(resultSet.next())
				{
					Productbean beanproduct = new Productbean();
					beanproduct.setRetailerName(resultSet.getString("retailerName"));
					beanproduct.setProductName(resultSet.getString("productName"));
					beanproduct.setProductId(resultSet.getInt("productId"));
					beanproduct.setProductCondition(resultSet.getString("productCondition"));
					beanproduct.setProductPrice(resultSet.getDouble("productPrice"));
					beanproduct.setProductAccessories(resultSet.getString("productAccessories"));
					beanproduct.setProductCategory(resultSet.getString("productCategory"));
					plist.add(beanproduct);
				}
			hpb.put("PRODUCTS", plist);
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
			return hpb;
		}


		public HashMap<String,ArrayList<Productbean>> viewingProductsByCategory(String productCategory)
		{
			HashMap<String,ArrayList<Productbean>> hashObj = new HashMap<String,ArrayList<Productbean>>();
			ResultSet resultSet = null;
			ArrayList<Productbean> plist = new ArrayList<Productbean>();
			try {
				createConnection();
				String query = "SELECT retailerName, productName, productId, productCondition, productPrice, productAccessories,productCategory FROM productdetails WHERE productCategory  = ? ";
				PreparedStatement pss = conn.prepareStatement(query);
				pss.setString(1, productCategory);
				resultSet = pss.executeQuery();
				
				while(resultSet.next())
				{
					Productbean beanproduct = new Productbean();
					beanproduct.setRetailerName(resultSet.getString("retailerName"));
					beanproduct.setProductName(resultSet.getString("productName"));
					beanproduct.setProductId(resultSet.getInt("productId"));
					beanproduct.setProductCondition(resultSet.getString("productCondition"));
					beanproduct.setProductPrice(resultSet.getDouble("productPrice"));
					beanproduct.setProductAccessories(resultSet.getString("productAccessories"));
					beanproduct.setProductCategory(resultSet.getString("productCategory"));
					plist.add(beanproduct);
				}
			hashObj.put("PRODUCTS", plist);
			} catch (Exception e) {
				e.printStackTrace();
			}   
			return hashObj;
		}

		public int updateStoremanagerProduct(Productbean beanProduct)
		{
			int result = 0;
			try {
				createConnection();
				String query = "UPDATE productdetails SET retailerName = ?, productName = ?, productCondition = ?, productPrice = ?, productAccessories = ?, productCategory = ?  where productId = ? ";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, beanProduct.getRetailerName());
				preparedStatement.setString(2, beanProduct.getProductName());
				preparedStatement.setString(3, beanProduct.getProductCondition());
				preparedStatement.setDouble(4, beanProduct.getProductPrice());
				preparedStatement.setString(5, beanProduct.getProductAccessories());
				preparedStatement.setString(6, beanProduct.getProductCategory());
				preparedStatement.setInt(7, beanProduct.getProductId());
				System.out.println(beanProduct.getProductPrice() + beanProduct.getProductAccessories() + beanProduct.getProductCategory());
				result = preparedStatement.executeUpdate();
			
			} catch (Exception e) {
				System.out.println("this is catch error");
				e.printStackTrace();
			}  
			return result;
		}

		public int deleteProduct(String productId){
			int result1 =0;
			
			try{
				createConnection();
				String query = "DELETE FROM productdetails WHERE productId = ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, productId);
				result1 = ps.executeUpdate();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return result1;
		}

}

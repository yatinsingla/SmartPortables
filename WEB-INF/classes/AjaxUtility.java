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
import java.lang.StringBuffer;


public class AjaxUtility{
	
	Connection conn=null;
	Statement statement=null;

    public Connection createConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
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

    public StringBuffer readdata(String searchId){
        HashMap<String,Productbean> hashObj=new HashMap<String,Productbean>();
        StringBuffer sb =  new StringBuffer();
        try{
            createConnection();
            String selectCustomerQuery ="select * from  productdetails where productName like'"+searchId+"%'";
            PreparedStatement preStat = conn.prepareStatement(selectCustomerQuery);
            ResultSet rs = preStat.executeQuery();
            Productbean p = new Productbean();
            while(rs.next()){
                p.setProductId(rs.getInt("productId"));
                p.setProductName(rs.getString("productName"));
                p.setProductCategory(rs.getString("productCategory"));
                hashObj.put(rs.getString("productId"), p);

                sb.append("<product>");
                sb.append("<a href='ViewItemServlet?id="+p.getProductId()+"&category="+p.getProductCategory()+"'<productName>" + p.getProductName() + "</ productName >");
                sb.append("</ product >");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return sb;
    }
	
}


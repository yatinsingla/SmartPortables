import java.util.Date;
import java.io.Serializable;

public class Orderbean implements Serializable {

	private String productName;
	private String productId;
	private int orderNumber;
	private String userName;
	private double price;
	private String deliveryDate;
	private String creditCardNumber;
	private String address;
	private String status;
	private int productCount;
	private String productOnSale;
	private int manufacturerRebate;
	private int productSold;
	private String currentDate;
	private int totalPrice;

    public Orderbean(){

    }

	public Orderbean(String productId, double price, String productName,int orderNumber,String userName, String deliveryDate, String creditCardNumber, String address,String status) {
		this.productId = productId;
		this.price = price;
		this.productName = productName;
		this.orderNumber = orderNumber;
		this.userName=  userName;
		this.deliveryDate = deliveryDate;
		this.creditCardNumber = creditCardNumber;
		this.address = address;
		this.status = status;
	}

    public void setProductName(String productName) {
		this.productName = productName;
	}

    public String getProductName() {
		return productName;
	}

    public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

    public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPrice(double price) {
		this.price = price;
	}

    public double getPrice() {
		return price;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

    public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}	

	public String getAddress() {
		return address;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setProductCount(int productCount){
		this.productCount = productCount;
	}

	public int getProductCount(){
		return productCount;
	}

	public void setProductOnSale(String productOnSale){
		this.productOnSale = productOnSale;
	}

	public String getProductOnSale(){
		return productOnSale;
	}

	public void setManufacturerRebate(int manufacturerRebate){
		this.manufacturerRebate = manufacturerRebate;
	}

	public int getManufacturerRebate(){
		return manufacturerRebate;
	}

	public void setProductSold(int productSold){
		this.productSold = productSold;
	}

	public int getProductSold(){
		return productSold;
	}

	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}

	public String getCurrentDate(){
		return currentDate;
	}


	public void setTotalPrice(int totalPrice){
		this.totalPrice = totalPrice;
	}

	public int getTotalPrice(){
		return totalPrice;
	}

}

import java.io.Serializable;


public class Productbean  implements Serializable{

	    private int productId;
        private String productCategory;
	    private double productPrice;
	    private String productName;
	    private String productImage;
	    private String productCondition;
	   	private String retailerName;
        private int productDiscount;
        private int manufacturerRebate;
        private String productOnSale;
        private int productCount;
        private int productSold;
        private String productAccessories;

		
        public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
        public String getProductCategory() {
			return productCategory;
		}
		public void setProductCategory(String productCategory) {
			this.productCategory = productCategory;
		}
		public double getProductPrice() {
			return productPrice;
		}
		public void setProductPrice(double productPrice) {
			this.productPrice = productPrice;
		}
		
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
        public String getProductImage(){
            return productImage;
        }
        public void setProductImage(String productImage){
            this.productImage = productImage;
        }
		public String getProductCondition() {
			return productCondition;
		}
		public void setProductCondition(String productCondition) {
			this.productCondition = productCondition;
		}
        public String getRetailerName() {
			return retailerName;
		}
		public void setRetailerName(String retailerName) {
			this.retailerName = retailerName;
		}
		
		public int getProductDiscount() {
			return productDiscount;
		}
		public void setProductDiscount(int productDiscount) {
			this.productDiscount = productDiscount;
		}
	    public int getManufacturerRebate() {
			return manufacturerRebate;
		}
		public void setManufacturerRebate(int manufacturerRebate) {
			this.manufacturerRebate = manufacturerRebate;
		}
        public String getProductOnSale() {
			return productOnSale;
		}
	    public void setProductOnSale(String productOnSale) {
			this.productOnSale = productOnSale;
		}
		public int getProductCount() {
			return productCount;
		}
		public void setProductCount(int productCount) {
			this.productCount = productCount;
		}
        public int getProductSold() {
			return productSold;
		}
		public void setProductSold(int productSold) {
			this.productSold = productSold;
		}
        public String getProductAccessories() {
			return productAccessories;
		}
	    public void setProductAccessories(String productAccessories) {
			this.productAccessories = productAccessories;
		}
	
	
}
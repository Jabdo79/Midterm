import java.math.BigDecimal;

public class Product {
	private String productName;
	private String productCategory;
	private String productDescription;
	private BigDecimal productPrice;
	private int productQuantity;

	// Constructor
	public Product(String productName, String productCategory, String productDescription, BigDecimal productPrice) {
		this.productName = productName;
		this.productCategory = productCategory;
		this.productDescription = productDescription;
		this.productPrice = productPrice;

	}

	// Constructor overloading 2
	public Product(String productName, BigDecimal productPrice, int productQuantity) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
	}
	
	public Product addToCart(int quantity){
		return new Product(productName, productPrice, quantity);
	}

	public String getProductName() {

		return productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
}



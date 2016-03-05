package cs263w16;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SharedProduct {
	private String email;
	private String productID;
	private String productName;

	public SharedProduct() {

	}

	public SharedProduct(String email, String productID, String productName) {
		this.email = email;
		this.productID = productID;
		this.productName = productName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
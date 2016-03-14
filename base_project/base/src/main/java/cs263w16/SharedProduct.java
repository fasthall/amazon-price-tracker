package cs263w16;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SharedProduct {
	private String email;
	private String productID;
	private String productName;
	private Date sharedDate;

	public SharedProduct() {

	}

	public SharedProduct(String email, String productID, String productName,
			Date sharedDate) {
		this.email = email;
		this.productID = productID;
		this.productName = productName;
		this.sharedDate = sharedDate;
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

	public Date getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(Date sharedDate) {
		this.sharedDate = sharedDate;
	}

}
package cs263w16;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WishlistProduct {
	private String productID;
	private String productName;
	private double currentPrice;
	private double lowestPrice;
	private Date lowestDate;

	public WishlistProduct() {

	}

	public WishlistProduct(String productID, String productName,
			double currentPrice, double lowestPrice, Date lowestDate) {
		this.productID = productID;
		this.productName = productName;
		this.currentPrice = currentPrice;
		this.lowestPrice = lowestPrice;
		this.lowestDate = lowestDate;
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

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Date getLowestDate() {
		return lowestDate;
	}

	public void setLowestDate(Date lowestDate) {
		this.lowestDate = lowestDate;
	}

}
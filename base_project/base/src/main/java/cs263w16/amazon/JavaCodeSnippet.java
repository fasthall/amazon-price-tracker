package cs263w16.amazon;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import cs263w16.WishlistProduct;

/*
 * This class shows how to make a simple authenticated call to the
 * Amazon Product Advertising API.
 *
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class JavaCodeSnippet {

	private static final String AWS_ACCESS_KEY_ID = "AKIAJHL7REQ4PUFZTZFQ";
	private static final String AWS_SECRET_KEY = "+L8DL8/CLPKLyizvcU7qqMCP4cIzb6dwjO+PEBNC";
	private static final String ENDPOINT = "webservices.amazon.com";

	private SignedRequestsHelper helper;
	private Map<String, String> params;

	public JavaCodeSnippet() {
		params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("AWSAccessKeyId", "AKIAJHL7REQ4PUFZTZFQ");
		params.put("AssociateTag", "fasthall-20");
		params.put("IdType", "ASIN");
		params.put("ResponseGroup", "Images,ItemAttributes,Offers");
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WishlistProduct search(String productID) throws Exception {
		params.put("ItemId", productID);
		String url = helper.sign(params);
		System.out.println(url);
		WishlistProduct wishlistProduct = new WishlistProduct();

		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setContentHandler(new XMLContentHandler(wishlistProduct));
		xmlReader.parse(new InputSource(new URL(url).openStream()));
		/*
		 * if (resEntityGet != null) { String result =
		 * EntityUtils.toString(resEntityGet);
		 * 
		 * DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 * DocumentBuilder db = dbf.newDocumentBuilder(); InputSource is = new
		 * InputSource(); is.setCharacterStream(new StringReader(result));
		 * Document doc = db.parse(is); NodeList nodes =
		 * doc.getElementsByTagName("Item");
		 * 
		 * if (nodes.getLength() < 1) { return null; } else {
		 * wishlistProduct.setProductID(productID); Element element = (Element)
		 * nodes.item(0); NodeList title =
		 * element.getElementsByTagName("Title");
		 * wishlistProduct.setProductName(((Element)
		 * title.item(0)).getTextContent()); NodeList lowestNewPrice =
		 * element.getElementsByTagName("LowestNewPrice"); if
		 * (lowestNewPrice.getLength() == 0) {
		 * wishlistProduct.setCurrentPrice(0f); } NodeList amount = ((Element)
		 * lowestNewPrice.item(0)).getElementsByTagName("Amount"); if
		 * (amount.getLength() == 0) { wishlistProduct.setCurrentPrice(0f); }
		 * else {
		 * product.setPrice(Float.parseFloat(amount.item(0).getTextContent()));
		 * } } EntityUtils.consume(resEntityGet); }
		 */
		return wishlistProduct;
	}
}

package cs263w16.amazon;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import cs263w16.WishlistProduct;

/*
 * This class shows how to make a simple authenticated call to the
 * Amazon Product Advertising API.
 *
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class JavaCodeSnippet {

	private static final String AWS_ACCESS_KEY_ID = "";
	private static final String AWS_SECRET_KEY = "";
	private static final String AWS_ASSOCIATE_TAG = "";
	private static final String ENDPOINT = "webservices.amazon.com";

	private SignedRequestsHelper helper;
	private Map<String, String> params;

	public JavaCodeSnippet() {
		params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
		params.put("AssociateTag", AWS_ASSOCIATE_TAG);
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

		return wishlistProduct;
	}

	public double getCachedPrice(String productID) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		return (double) syncCache.get(productID);
	}
}

package cs263w16.amazon;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import cs263w16.WishlistProduct;

public class XMLContentHandler implements ContentHandler {

	private String currentTag = "";
	private WishlistProduct wishlistProduct;

	public XMLContentHandler(WishlistProduct wishlistProduct) {
		this.wishlistProduct = wishlistProduct;
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (currentTag.equals("ListPrice")) {
			if (qName.equals("FormattedPrice")) {
				currentTag = "FormattedPrice";
			}
		} else if (qName.equals("ListPrice")) {
			currentTag = "ListPrice";
		} else if (qName.equals("Title")) {
			currentTag = "Title";
		} else {
			currentTag = "";
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currentTag.equals("FormattedPrice")) {
			double price;
			try {
				price = Double
						.parseDouble(new String(ch, start + 1, length - 1));
			} catch (NumberFormatException e) {
				price = 0;
			}
			wishlistProduct.setCurrentPrice(price);
			wishlistProduct.setLowestPrice(price);
			currentTag = "";
		}
		if (currentTag.equals("Title")) {
			wishlistProduct.setProductName(new String(ch, start, length));
		}
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}

}
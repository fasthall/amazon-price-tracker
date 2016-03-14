/*
 * @Author Wei-Tsung Lin
 * @Date 02/18/2016
 * @Description Extract Product ID from URL
 */
package cs263w16.amazon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractPID {

	public static String getProductId(String url) {
		String regex1 = ".*/([a-zA-z0-9]{10})/.*";
		String regex2 = ".*/([a-zA-z0-9]{10})";
		String regex3 = ".*/([a-zA-z0-9]{10})\\?.*";

		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(url);
		if (m.matches()) {
			return m.group(1);
		}

		p = Pattern.compile(regex2);
		m = p.matcher(url);
		if (m.matches()) {
			return m.group(1);
		}
		
		p = Pattern.compile(regex3);
		m = p.matcher(url);
		if (m.matches()) {
			return m.group(1);
		}
		return null;
	}
}
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class DSTester {

	public static void main(String[] args) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURI());

		test1(service);
	}

	public static void test1(WebTarget service) {
		System.out.println("Test 1 Deleting product B0041Q44T2.");
		String productID = "B0041Q44T2";
		service.path("rest").path("wishlist").path(productID).request()
				.delete();

		// Register a new product
		Form form = new Form();
		form.param("productID", productID);
		Response response = service
				.path("rest")
				.path("wishlist")
				.request()
				.post(Entity
						.entity(form, MediaType.APPLICATION_FORM_URLENCODED),
						Response.class);
		System.out.println("Test 1 Posting product B0041Q44T2.");
		// Return code should be 201 == created resource
		int status = response.getStatus();
		if (status != 204) {
			System.out.println("Test 1 Failed: Expected status 204, got: "
					+ status);
		} else {
			System.out.println("Test 1 Passed");
		}
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8888/").build();
		// return
		// UriBuilder.fromUri("http://cs263w16-1190.appspot.com/").build();
	}
}

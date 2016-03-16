import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Test;

public class WishlistTest {

	private static final String HOST = "http://localhost:8080/";

	@Test
	public void test() {
		URI uri = UriBuilder.fromUri(HOST).build();
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(uri);

		Response response;
		boolean result;

		// Delete an entity from the Datastore
		String keyname = "XXXXXXXXXX";
		response = service.path("rest").path("wishlist")
				.request(MediaType.APPLICATION_JSON).get();

		response = service
				.path("rest")
				.path("wishlist")
				.request(MediaType.TEXT_HTML)
				.post(Entity
						.entity("{\"productID\":\"XXXXXXXXXX\", \"productName\":\"Test product\", \"currentPrice\":999.0, \"lowestPrice\":999.0, \"lowestDate\":0}",
								MediaType.APPLICATION_JSON));
		result = response.getStatus() == 201 || response.getStatus() == 204;
		Assert.assertTrue(result);

		result = true;
		try {
			service.path("rest").path("wishlist").request()
					.accept(MediaType.TEXT_XML).get(String.class);
		} catch (Exception e) {
			result = false;
		}
		Assert.assertTrue(result);

		result = true;
		try {
			service.path("rest").path("wishlist").request()
					.accept(MediaType.APPLICATION_JSON).get(String.class);
		} catch (Exception e) {
			result = false;
		}
		Assert.assertTrue(result);

		result = true;
		try {
			service.path("rest").path("wishlist").path("XXXXXXXXXX").request()
					.accept(MediaType.APPLICATION_XML).get(String.class);
		} catch (Exception e) {
			result = false;
		}
		Assert.assertTrue(result);

		service.path("rest").path("ds").path(keyname).request().delete();

		// Get TaskData with id dstester1 again
		result = false;
		try {
			service.path("rest").path("wishlist").path("XXXXXXXXXX").request()
					.accept(MediaType.APPLICATION_XML).get(String.class);
		} catch (Exception e) {
			result = true;
		}
		Assert.assertTrue(result);
	}

}

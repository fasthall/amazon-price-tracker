package cs263w16;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;

@SuppressWarnings("serial")
public class DatastoreServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().println("<html><body>");

        int argsCnt = req.getParameterMap().size();
        String keyname = req.getParameter("keyname");
        String value = req.getParameter("value");
      
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (argsCnt == 0) {
            Query query = new Query("TaskData");
            List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
            for (Entity entity : results) {
                resp.getWriter().println(entity.getKey().getName() + " " + entity.getProperty("value") + "<br>");
            }
        } else if (argsCnt == 1 && keyname != null) {
            Key entKey = KeyFactory.createKey("TaskData", keyname);
            try {
                Entity entity = datastore.get(entKey);
                // print
                value = (String) entity.getProperty("value");
                resp.getWriter().println(keyname + " " + value);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        } else if (argsCnt == 2 && keyname != null && value != null) {
            Entity entity = new Entity("TaskData", keyname);
            entity.setProperty("date", new Date());
            entity.setProperty("value", value);
            datastore.put(entity);
            // print Stored KEY and VALUE in Datastore
            resp.getWriter().println("Stored " + keyname + " and " + value + " in Datastore");
        } else {
            resp.getWriter().println("Can't recognize the parameters.");
        }

        resp.getWriter().println("</body></html>");
    }
}

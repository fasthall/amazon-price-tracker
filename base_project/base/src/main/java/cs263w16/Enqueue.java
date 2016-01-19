package cs263w16;

import java.io.IOException; 
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class Enqueue extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("keyname");
        String value = request.getParameter("value");

        // Add the task to the default queue.
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.withUrl("/rest/ds").param("keyname", key).param("value", value));

        response.sendRedirect("/done.html");
    }
}

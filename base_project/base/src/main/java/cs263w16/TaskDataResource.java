package cs263w16;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;
import javax.xml.bind.JAXBElement;

public class TaskDataResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String keyname;

  public TaskDataResource(UriInfo uriInfo, Request request, String kname) {
    this.uriInfo = uriInfo;
    this.request = request;
    this.keyname = kname;
  }
  // for the browser
  @GET
  @Produces(MediaType.TEXT_XML)
  public TaskData getTaskDataHTML() {
    //add your code here (get Entity from datastore using this.keyname)
    // throw new RuntimeException("Get: TaskData with " + keyname +  " not found");
    //if not found
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    String cached = (String) syncCache.get(keyname);
    if (cached != null) {
      return new TaskData(keyname, cached, null);
    }
    try {
      Key entKey = KeyFactory.createKey("TaskData", keyname);
      Entity entity = datastore.get(entKey);
      String value = (String) entity.getProperty("value");
      Date date = (Date) entity.getProperty("date");
      return new TaskData(keyname, value, date);
    } catch (EntityNotFoundException e) {
      throw new RuntimeException("Get: TaskData with " + keyname +  " not found");
    }
  }
  // for the application
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public TaskData getTaskData() {
    //same code as above method
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    String cached = (String) syncCache.get(keyname);
    if (cached != null) {
      return new TaskData(keyname, cached, null);
    }
    try {
      Key entKey = KeyFactory.createKey("TaskData", keyname);
      Entity entity = datastore.get(entKey);
      String value = (String) entity.getProperty("value");
      Date date = (Date) entity.getProperty("date");
      return new TaskData(keyname, value, date);
    } catch (EntityNotFoundException e) {
      throw new RuntimeException("Get: TaskData with " + keyname +  " not found");
    }
  }

  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  public Response putTaskData(String val) {
    Response res = null;
    //add your code here
    //first check if the Entity exists in the datastore
    //if it is not, create it and 
    //signal that we created the entity in the datastore 
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    Key entKey = KeyFactory.createKey("TaskData", keyname);
    try {
      Entity entity = datastore.get(entKey);
      //else signal that we updated the entity
      res = Response.noContent().build();
    } catch (EntityNotFoundException e) {
      res = Response.created(uriInfo.getAbsolutePath()).build();
    }
    Entity entity = new Entity("TaskData", keyname);
    entity.setProperty("date", new Date());
    entity.setProperty("value", val);
    datastore.put(entity);
    syncCache.put(keyname, val);
 
    return res;
  }

  @DELETE
  public void deleteIt() {

    //delete an entity from the datastore
    //just print a message upon exception (don't throw)
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    Key entKey = KeyFactory.createKey("TaskData", keyname);
    try {
      datastore.delete(entKey);
      syncCache.delete(keyname);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

} 
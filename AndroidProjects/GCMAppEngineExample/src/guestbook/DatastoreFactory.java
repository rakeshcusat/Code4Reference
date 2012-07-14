package guestbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.mortbay.log.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * This class defines the method related with datastore.
 * @author rakesh
 *
 */
public class DatastoreFactory {
	
	 public static final String USER_INFO =  "UserInfo";
	 public static final String DEVICE_REG_ID_PROPERTY = "regId";
	 public static final String MULTICAST_TYPE = "Multicast";
	 public static final String MULTICAST_REG_IDS_PROPERTY = "regIds";

	 
	 private static final Logger log =
	      Logger.getLogger(DatastoreFactory.class.getSimpleName());
	  private static final DatastoreService datastore =
	      DatastoreServiceFactory.getDatastoreService();

	  /**
	   * This constructor will make sure no one can make object of this class.
	   */
	  private DatastoreFactory() {
		    throw new UnsupportedOperationException();
		}
	  /**
	   * This is a helper method which creates UserInfo and store in datastore.
	   * @param regId
	   * @param emailId
	   * @param name
	   * @param regDate
	   * @param unregDate
	   * @return
	   */
	public static Entity createUserInfoEntity(String regId, String emailId, String name, Date regDate, Date unregDate){
		Entity userInfo = new Entity(USER_INFO);
		userInfo.setProperty("name", name);
		userInfo.setProperty(DEVICE_REG_ID_PROPERTY, regId);
		userInfo.setProperty("emailId", emailId);
		userInfo.setProperty("RegDate", regDate);
		userInfo.setProperty("UnregDate",unregDate);
		return userInfo;
	}
	/**
	 * This method Register the user and store user related information.
	 * @param regId
	 * @param emailId
	 * @param phoneNo
	 * @param name
	 */
	public static void registerUser(String regId, String emailId,  String name){
		Entity userInfo = createUserInfoEntity(regId, emailId, name, new Date(), null);
		Log.info("Rgistered : "+regId);
		datastore.put(userInfo);
	}
	/**
	 * This method removes the registered user and removes its personal information from database.
	 * @param regId
	 */
	public static void unregisterUser(String regId){
		 log.info("Unregistering " + regId);
		    Entity entity = findDeviceByRegId(regId);
		    Key key = entity.getKey();
		    datastore.delete(key);
	}
	/**
	 * This method returns all the registration id which are stored in the datastore.
	 * @return
	 */
	public static List<String> getRegIds() {
	    Query query = new Query(USER_INFO);
	    Iterable<Entity> entities = datastore.prepare(query).asIterable();
	    List<String> regIds = new ArrayList<String>();
	    for (Entity entity : entities) {
	      String regId = (String) entity.getProperty(DEVICE_REG_ID_PROPERTY);
	      regIds.add(regId);
	    }
	    return regIds;
	  }
	/**
	 * This method search userInfo based on the registration Id.
	 * @param regId
	 * @return
	 */
	 private static Entity findDeviceByRegId(String regId) {
		    Query query = new Query(USER_INFO)
		        .setFilter(new FilterPredicate(DEVICE_REG_ID_PROPERTY, FilterOperator.EQUAL,regId));
		    PreparedQuery preparedQuery = datastore.prepare(query);
		    Entity entity = preparedQuery.asSingleEntity();
		    return entity;
		  }
	 
	static Entity createMessageEntity(String fromAppId, String toEmailId, String fromEmailId, String subject, String message){
		Entity messageEntity = new Entity("MessageEntity");
		messageEntity.setProperty("toEmailId", toEmailId);
		messageEntity.setProperty("fromEmailId",fromEmailId);
		messageEntity.setProperty("subject", subject);
		messageEntity.setProperty("message", message);
		messageEntity.setProperty("date", new Date());
		messageEntity.setProperty("delivered", false);
		return messageEntity;
	}
}

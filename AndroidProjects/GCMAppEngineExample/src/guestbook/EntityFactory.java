package guestbook;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class EntityFactory {

	static Entity createUserInfoEntity(String appId, String emailId, String phoneNo, String userId){
		Entity userInfo = new Entity("UserInfo");
		userInfo.setProperty("userId", userId);
		userInfo.setProperty("appId", appId);
		userInfo.setProperty("emailId", emailId);;
		userInfo.setProperty("phoneNo", phoneNo);
		return userInfo;
	}
	static Entity createMessageEntity(String fromAppId, String toEmailId, String subject, String message){
		Entity messageEntity = new Entity("MessageEntity");
		messageEntity.setProperty("fromAppId", fromAppId);
		messageEntity.setProperty("toEmailId", toEmailId);
		messageEntity.setProperty("subject", subject);
		messageEntity.setProperty("message", message);
		messageEntity.setProperty("date", new Date());
		return messageEntity;
	}
}

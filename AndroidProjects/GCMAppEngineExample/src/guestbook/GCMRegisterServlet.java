package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

@SuppressWarnings("serial")
public class GCMRegisterServlet extends GCMBaseServlet {

	private static final Logger log =
		Logger.getLogger(GCMRegisterServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	String appId = req.getParameter("appId");
	String emailId = req.getParameter("emailId");
	String phoneNo = req.getParameter("phoneNo");
	String userId = req.getParameter("userId");
	log.info("appId :" +appId+", emailId : "+emailId + ", phoneNo : "+phoneNo+", userId : "+userId);
	DatastoreFactory.registerUser(appId, emailId,  userId);
	setSuccess(resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
}

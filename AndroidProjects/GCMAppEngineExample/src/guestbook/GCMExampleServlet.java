package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

@SuppressWarnings("serial")
public class GCMExampleServlet extends HttpServlet {

	private static final Logger log =
		Logger.getLogger(GCMExampleServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	String appId = req.getParameter("appId");
	String emailId = req.getParameter("emailId");
	String phoneNo = req.getParameter("phoneNo");
	String userId = req.getParameter("userId");
	
	log.info("appId :" +appId+", emailId : "+emailId + ", phoneNo : "+phoneNo+", userId : "+userId);
	
	DatastoreServiceFactory.getDatastoreService().put(EntityFactory.createUserInfoEntity(appId, emailId, phoneNo, userId));
	resp.setContentType("text/plain");
	resp.getWriter().println("saved the user data");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}

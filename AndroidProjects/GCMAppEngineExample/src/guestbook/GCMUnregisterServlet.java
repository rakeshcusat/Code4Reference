package guestbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GCMUnregisterServlet extends GCMBaseServlet {

	private final static String PARAMETER_REG_ID = "regId";
	@Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException {
	    String regId = getParameter(req, PARAMETER_REG_ID);
	    DatastoreFactory.unregisterUser(regId);
	    setSuccess(resp);
	  }
}

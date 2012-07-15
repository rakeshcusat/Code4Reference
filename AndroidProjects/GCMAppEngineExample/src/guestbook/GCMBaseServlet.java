package guestbook;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GCMBaseServlet extends HttpServlet {

	// change to true to allow GET calls
	  static final boolean DEBUG = true;

	  protected final Logger log = Logger.getLogger(getClass().getName());

	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException, ServletException {
	    if (DEBUG) {
	      doPost(req, resp);
	    } else {
	      super.doGet(req, resp);
	    }
	  }

	  protected String getParameter(HttpServletRequest req, String parameter)
	      throws ServletException {
	    String value = req.getParameter(parameter);
	    if (value == null || value.trim().isEmpty()) {
	      if (DEBUG) {
	        StringBuilder parameters = new StringBuilder();
	        @SuppressWarnings("unchecked")
	        Enumeration<String> names = req.getParameterNames();
	        while (names.hasMoreElements()) {
	          String name = names.nextElement();
	          String param = req.getParameter(name);
	          parameters.append(name).append("=").append(param).append("\n");
	        }
	        log.fine("parameters: " + parameters);
	      }
	      throw new ServletException("Parameter " + parameter + " not found");
	    }
	    return value.trim();
	  }

	  protected String getParameter(HttpServletRequest req, String parameter,
	      String defaultValue) {
	    String value = req.getParameter(parameter);
	    if (value == null || value.trim().isEmpty()) {
	      value = defaultValue;
	    }
	    return value.trim();
	  }

	  protected void setSuccess(HttpServletResponse resp) {
	    setSuccess(resp, 0);
	  }

	  protected void setSuccess(HttpServletResponse resp, int size) {
	    resp.setStatus(HttpServletResponse.SC_OK);
	    resp.setContentType("text/plain");
	    resp.setContentLength(size);
	  }
}

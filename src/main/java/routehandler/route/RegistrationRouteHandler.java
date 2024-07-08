package routehandler.route;

import http.MyHttpRequest;
import http.MyHttpResponse;
import http.enums.HttpStatusType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import routehandler.core.IRouteHandler;
import utils.FileReadUtil;

public class RegistrationRouteHandler implements IRouteHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationRouteHandler.class);

    public void handle(MyHttpRequest req, MyHttpResponse res) {
        var url = req.getUrl();

        var userId = url.getParameter("userId");
        var password = url.getParameter("password");
        var name = url.getParameter("name");
        var email = url.getParameter("email");

        logger.debug("id: {}, password: {}", userId, password);

        if (userId != null && password != null && name != null && email != null) {
            var user = new User(userId, password, name, email);
            logger.debug("user: {}", user);
            res.redirect("/");
            return;
        }
        try {
            byte[] body = FileReadUtil.read("/registration/index.html");
            res.setBody(body);
            res.setStatusInfo(HttpStatusType.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            res.setStatusInfo(HttpStatusType.INTERNAL_SERVER_ERROR);
        }
    }
}

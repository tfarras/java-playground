package http.server.body;

import java.util.logging.Logger;

public class BodyService {
    private final Logger logger = Logger.getLogger(BodyService.class.getName());

    public Object handleBody(Object body) {
        logger.info("Body: " + body.toString());

        return body;
    }
}

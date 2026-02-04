package http.server.param;

import java.util.Map;
import java.util.logging.Logger;

public class ParamService {
    private static final Logger logger = Logger.getLogger(ParamService.class.getName());

    public Object handleParams(Map<String, String> params) {
        logger.info(params.toString());

        return params;
    }
}

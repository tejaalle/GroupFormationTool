package CSCI5308.GroupFormationTool.ErrorHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private LoggerUtil() {
    }

    // Providing Global point of access for the loggerUtil
    public static Logger getLoggerInstance(Class<?> className) {
        return LogManager.getLogger(className);
    }

}

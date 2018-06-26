package rmichat.core;

import java.sql.Timestamp;
import rmichat.core.Models.Message;

/**
 *
 * @author Krzysztof
 */
public class TimeStampProvider {
    
    public String getCurrent() {
        return "[" + new Timestamp(System.currentTimeMillis()) + "]";
    }
    
    public String getFromMessage(Message message) {
        return "[" + message.getTimestamp() + "]";
    }
}

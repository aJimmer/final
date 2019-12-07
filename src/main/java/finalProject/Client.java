package finalProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Client {
  private static final Logger clientLogger = LoggerFactory.getLogger(Client.class);
  String message;

  @Autowired
  public Client() {

  }
  public void receiveMessage(String message) {
    clientLogger.info("Publisher:  " + message);
    updateMessage(message);
  }
  private void updateMessage(String message){
    this.message = message;
  }
}
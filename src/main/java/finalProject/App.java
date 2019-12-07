package finalProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class App {

  private static final Logger serverLogger = LoggerFactory.getLogger(App.class);

  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {

    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(listenerAdapter, new PatternTopic("observe"));

    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(Client receiver) {
    return new MessageListenerAdapter(receiver, "receiveMessage");
  }

  @Bean
  Client receiver() {
    return new Client();
  }

  @Bean
  StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
    return new StringRedisTemplate(connectionFactory);
  }

  public static void main(String[] args) throws InterruptedException {

    ApplicationContext ctx = SpringApplication.run(App.class, args);
    StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);

    serverLogger.info("Sending message...");
    template.convertAndSend("observe", "Message received..");

    System.exit(0);
  }
}
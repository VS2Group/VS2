package de.hska.exablog;

import de.hska.exablog.Logik.Model.Database.Messaging.Receiver;
import de.hska.exablog.Logik.Model.Service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;


@SpringBootApplication
public class ExablogApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(de.hska.exablog.ExablogApplication.class, args);

		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		CountDownLatch latch = ctx.getBean(CountDownLatch.class);
		TimelineService timelineService = ctx.getBean(TimelineService.class);
		Logger LOGGER = LoggerFactory.getLogger(Receiver.class);


		long letztesUpdate = 0;
		while (true) {
			int neueposts = timelineService.getGlobalTimeline(letztesUpdate, Long.MAX_VALUE).getPosts().size();
			if (neueposts > 0) {
				String message = Long.toString(letztesUpdate) + ";" + Integer.toString(neueposts);
				LOGGER.info(String.format("Sent to Receiver: <%s>", message));
				template.convertAndSend("neue_posts_verfuegbar", message);

				letztesUpdate += neueposts;
				latch.await();
			}

			Thread.sleep(2000);
		}
	}
}

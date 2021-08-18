package com.github.sodara.ewallet;

import java.net.URISyntaxException;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class App {
  public static void main(String[] args) throws URISyntaxException {
    DOMConfigurator.configure("log4j.xml");
    Logger log = LoggerFactory.getLogger(App.class);
    log.info("Starting App");
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    applicationContext.getBean(HttpServer.class).bindUntilJavaShutdown(Duration.ofSeconds(60), null);
    log.info("Exiting App");
  }
}

package com.github.sodara.ewallet;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.xml.DOMConfigurator;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class App {
  public static void main(String[] args) throws URISyntaxException {
    DOMConfigurator.configure("log4j.xml");
    Logger log = LoggerFactory.getLogger(App.class);
    log.info("Starting App");
    //Path errorHTML = Paths.get(App.class.getResource("/error.html").toURI());
    HttpServer.create()
        .port(8080)
        .route(routes ->
            routes.get("/items", (request, response) ->
                    response.sendString(Mono.just("Hello World!")
                        .log("http-server")))
                .get("/items/{param}", (request, response) ->
                    response.sendString(Mono.just(request.param("param"))
                        .log("http-server")))
        )
        .bindNow()
        .onDispose()
        .block();
    //AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    //applicationContext.getBean(DisposableServer.class).onDispose().block();
    //CreditCard c = new CreditCard();
    //CqlSession cqlSession = CqlSession.builder().build();
    // CQLConnect con = new CQLConnect(cqlSession);
    // c= c.applyMasterCard();
    // System.out.println(c.getCardNumber());
    // System.out.println(c.getType());
    // System.out.println(c.getCvv());
    // System.out.println(c.getExpDate());
    // con.insert(c);
    log.info("Exiting App");
  }
}

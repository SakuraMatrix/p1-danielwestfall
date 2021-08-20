package com.github.sodara.ewallet;

import com.github.sodara.ewallet.service.TransferService;
import com.github.sodara.ewallet.service.WalletService;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

@Configuration
@ComponentScan
public class AppConfig {
  @Autowired
  WalletService walletService;
  @Autowired
  TransferService transferService;

  @Bean
  public HttpServer httpServer(ApplicationContext context) throws URISyntaxException {
    Path index = Paths.get(App.class.getResource("/index.html").toURI());
    HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
    ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
    return HttpServer.create().port(8080).route(routes -> routes
        .file("/index", index)
        .post("/my_wallet_addition", (request, response) ->
            response.send(request.receive().asString()
                .map(App::parseWallet)
                .map(walletService::create)
                .map(App::toByteBuf)
                .log("http-server")))
    ).handle(adapter);

  }
}

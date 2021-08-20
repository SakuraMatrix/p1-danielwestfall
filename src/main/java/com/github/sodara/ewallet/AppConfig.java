package com.github.sodara.ewallet;

import com.datastax.oss.driver.api.core.CqlSession;
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
import reactor.netty.http.server.HttpServer;

@Configuration
@ComponentScan
public class AppConfig {
  @Autowired
  WalletService walletService;
  @Autowired
  TransferService transferService;
  @Bean
  public CqlSession cqlSession(){
    return CqlSession.builder().withKeyspace("eWallets").build();
  }
  @Bean
  public HttpServer httpServer(ApplicationContext context) throws URISyntaxException {
    Path add_wallet_html = Paths.get(App.class.getResource("/add_wallet.html").toURI());
    Path add_transfer_html = Paths.get(App.class.getResource("/add_transfer.html").toURI());
    return HttpServer.create().port(8080).route(routes ->
        routes.get("/wallets", (request, response) ->
                response.send(walletService.getAll()
                    .map(App::toByteBuf)
                    .log("http-server")))
            .get("/transfers", (request, response) ->
                response.send(transferService.getAll()
                    .map(App::toByteBuf)
                    .log("http-server")))
            .post("/new_wallet", (request, response) ->
                response.send(request.receive().asString()
                    .map(App::parseWallet)
                    .map(walletService::create)
                    .map(App::toByteBuf)
                    .log("http-server")))
            .post("/new_transfer", (request, response) ->
                response.send(request.receive().asString()
                    .map(App::parseTransfer)
                    .map(transferService::create)
                    .map(App::toByteBuf)
                    .log("http-server")))
            .get("/add_wallet", (request, response) ->
                response.sendFile(add_wallet_html))
            .get("/add_transfer", (request, response) ->
                response.sendFile(add_transfer_html))
            .get("/wallet/{userId}", ((request, response) ->
                response.send(walletService.getWallet(
                        Integer.parseInt(request.param("userId")))
                    .map(App::toByteBuf)
                    .log("http-server")
                )))
            .get("/transfers/{userId}", ((request, response) ->
                response.send(transferService.getTransfersByUserID(
                        Integer.parseInt(request.param("userId")))
                    .map(App::toByteBuf)
                    .log("http-server")
                )))
    );
  }

}

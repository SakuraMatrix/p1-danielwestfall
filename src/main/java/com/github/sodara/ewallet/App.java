package com.github.sodara.ewallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sodara.ewallet.domain.Transfer;
import com.github.sodara.ewallet.domain.Wallet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.http.server.HttpServer;

public class App {

  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) throws URISyntaxException {
    DOMConfigurator.configure("log4j.xml");
    Logger log = LoggerFactory.getLogger(App.class);
    log.info("Starting App");
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    applicationContext.getBean(HttpServer.class).bindUntilJavaShutdown(Duration.ofSeconds(60), null);
    log.info("Exiting App");
  }

  static ByteBuf toByteBuf(Object o) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      OBJECT_MAPPER.writeValue(out, o);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
  }

  static Wallet parseWallet(String str) {
    Wallet wallet = null;
    try {
      wallet = OBJECT_MAPPER.readValue(str, Wallet.class);
    } catch (JsonProcessingException ex) {
      String[] params = str.split("&");
      int user_id = Integer.parseInt(params[0].split("=")[1]);
      String name = params[1].split("=")[1];
      double balance = Double.parseDouble(params[2].split("=")[1]);
      wallet = new Wallet(user_id, name, balance);
    }
    return wallet;
  }

  static Transfer parseTransfer(String str) {
    Logger log = LoggerFactory.getLogger(App.class);
    Transfer transfer = null;
    try {
      transfer = OBJECT_MAPPER.readValue(str, Transfer.class);
    } catch (JsonProcessingException ex) {
      log.info("Parsing Transfer");
      log.info(str);
      String[] params = str.split("&");
      int user_id = Integer.parseInt(params[0].split("=")[1]);
      double amount = Double.parseDouble(params[1].split("=")[1]);
      transfer = new Transfer(user_id, amount);
    }
    return transfer;
  }
}

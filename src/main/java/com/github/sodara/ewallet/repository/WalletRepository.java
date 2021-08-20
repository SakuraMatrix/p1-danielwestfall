package com.github.sodara.ewallet.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.sodara.ewallet.domain.Transfer;
import com.github.sodara.ewallet.domain.Wallet;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class WalletRepository {
  private final CqlSession session;

  public WalletRepository(CqlSession session) {
    this.session = session;
  }

  public Flux<Wallet> getAll() {
    return Flux.from(session.executeReactive("SELECT * FROM wallets"))
        .map(row -> new Wallet(row.getInt("user_id"), row.getString("name"), row.getDouble("balance")));
  }

  public Mono<Wallet> get(int id) {
    return Mono.from(
            session.executeReactive("SELECT * FROM wallets WHERE user_id = " + id + " ALLOW FILTERING"))
        .map(row -> new Wallet(row.getInt("user_id"), row.getString("name"), row.getDouble("balance")));
  }

  public Wallet create(Wallet wallet) {
    SimpleStatement stmt = SimpleStatement.builder(
            "INSERT INTO wallets (user_id, name, balance) values (?, ?, ?)")
        .addPositionalValues(wallet.getUserId(), wallet.getName(), wallet.getBalance())
        .build();
    Flux.from(session.executeReactive(stmt)).subscribe();
    return wallet;
  }
}
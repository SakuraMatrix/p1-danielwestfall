package com.github.sodara.ewallet.repository;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import java.sql.Timestamp;
import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sodara.ewallet.domain.Transfer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class TransferRepository {

  private final CqlSession session;

  public TransferRepository(CqlSession session) {
    this.session = session;
  }

  public Flux<Transfer> getAll() {
    return Flux.from(session.executeReactive("SELECT * FROM eWallets.transfers"))
        .map(row -> new Transfer(row.getUuid("transfer_id"), row.getInt("user_id"),
            row.getDouble("amount"), Timestamp.from(row.getInstant("transfer_created"))));
  }

  public Flux<Transfer> get(int id) {
    return Flux.from(
            session.executeReactive("SELECT * FROM eWallets.transfers WHERE user_id = " + id))
        .map(row -> new Transfer(row.getUuid("transfer_id"), row.getInt("user_id"),
            row.getDouble("amount"), Timestamp.from(row.getInstant("transfer_created"))));
  }

  public Transfer create(Transfer transfer) {
    SimpleStatement stmt = SimpleStatement.builder(
            "INSERT INTO eWallets.transfer (user_id, transfer_id, amount, transfer_created) values (?, ?, ?, ?)")
        .addPositionalValues(transfer.getUserId(), transfer.getUuid(), transfer.getAmount(),
            transfer.getTransferCreated().toInstant())
        .build();
    Flux.from(session.executeReactive(stmt)).subscribe();
    return transfer;
  }
}
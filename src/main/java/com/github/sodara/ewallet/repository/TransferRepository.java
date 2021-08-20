package com.github.sodara.ewallet.repository;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import java.sql.Timestamp;
import java.util.UUID;
import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sodara.ewallet.domain.Transfer;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class TransferRepository {
  private final CqlSession session;

  public TransferRepository(CqlSession session) {
    this.session = session;
  }
  @AllowFiltering
  public Flux<Transfer> getAll() {
    return Flux.from(session.executeReactive("SELECT * FROM transfers"))
        .map(row -> new Transfer(row.getUuid("transfer_id"), row.getInt("user_id"),
            row.getDouble("amount"), Timestamp.from(row.getInstant("transfer_created"))));
  }

  public Flux<Transfer> get(int id) {
    return Flux.from(
            session.executeReactive("SELECT * FROM transfers WHERE user_id = " + id + " ALLOW FILTERING"))
        .map(row -> new Transfer(row.getUuid("transfer_id"), row.getInt("user_id"),
            row.getDouble("amount"), Timestamp.from(row.getInstant("transfer_created"))));
  }

  public Flux<Transfer> getByTransferID(UUID id) {
    return Flux.from(
            session.executeReactive("SELECT * FROM transfers WHERE transfer_id = " + id + " ALLOW FILTERING"))
        .map(row -> new Transfer(row.getUuid("transfer_id"), row.getInt("user_id"),
            row.getDouble("amount"), Timestamp.from(row.getInstant("transfer_created"))));
  }

  public Transfer create(Transfer transfer) {
    SimpleStatement stmt = SimpleStatement.builder(
            "INSERT INTO transfers (transfer_id, user_id, amount, transfer_created) values (?, ?, ?, ?)")
        .addPositionalValues(transfer.getUuid(), transfer.getUserId(), transfer.getAmount(),
            transfer.getTransferCreated().toInstant())
        .build();
    Flux.from(session.executeReactive(stmt)).subscribe();
    return transfer;
  }

  public void destroy(UUID transfer_id, int user_id){
    SimpleStatement stmt = SimpleStatement.builder(
            "DELETE FROM transfers WHERE transfer_id = " + transfer_id + " and  user_id = " + user_id ).build();
    Flux.from(session.executeReactive(stmt)).subscribe();
  }
}
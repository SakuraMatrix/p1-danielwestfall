package com.github.sodara.ewallet.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("transfers")
public class Transfer {
  @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private int user_id;
  @PrimaryKeyColumn(name = "transfer_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
  private UUID transfer_id;
  private double amount;
  private Timestamp transfer_created;

  public Transfer(int user_id, double amount) {
    this.transfer_id = UUID.randomUUID();
    this.user_id = user_id;
    this.amount = amount;
    this.transfer_created = Timestamp.from(Instant.now());
  }

  public Transfer(UUID transfer_id, int user_id, double amount, Timestamp transfer_created) {
    this.transfer_id = transfer_id;
    this.user_id = user_id;
    this.amount = amount;
    this.transfer_created = transfer_created;
  }
  public Transfer(String transfer_id, int user_id){
    this.transfer_id = UUID.fromString(transfer_id);
    this.user_id = user_id;
    this.amount = 0;
    this.transfer_created = Timestamp.from(Instant.now());
  }

  @Override
  public String toString() {
    return "Transfer{" +
        "transfer_id=" + this.transfer_id +
        "user_id=" + this.user_id +
        ", amount='" + this.amount + '\'' +
        ", transfer_created=" + this.transfer_created +
        '}';
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    Transfer other_transfer = (Transfer) other;
    return transfer_id == other_transfer.transfer_id && user_id == other_transfer.user_id && Double.compare(amount, other_transfer.amount) == 0 && Objects.equals(transfer_created, other_transfer.transfer_created);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transfer_id, user_id, amount);
  }

  public UUID getUuid(){
    return transfer_id;
  }

  public void setUuid(UUID new_id){
    this.transfer_id = new_id;
  }

  public int getUserId() {
    return user_id;
  }

  public void setUserId(int new_id) {
    this.user_id = new_id;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double new_amount) {
    this.amount = new_amount;
  }

  public Timestamp getTransferCreated() {
    return transfer_created;
  }

  public void setTransferCreated(Timestamp new_time) {
    this.transfer_created = new_time;
  }
}

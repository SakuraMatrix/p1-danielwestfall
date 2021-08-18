package com.github.sodara.ewallet.domain;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("transfers")
public class Transfer {
  @PrimaryKey
  private UUID uuid;
  private int user_id;
  private double amount;
  private Timestamp transfer_created;

  public Transfer() {
  }

  public Transfer(UUID uuid, int user_id, double amount, Timestamp t_created) {
    this.uuid = uuid;
    this.user_id = user_id;
    this.amount = amount;
    this.transfer_created = t_created;
  }

  @Override
  public String toString() {
    return "Transfer{" +
        "uuid=" + this.uuid +
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
    return uuid == other_transfer.uuid && user_id == other_transfer.user_id && Double.compare(amount, other_transfer.amount) == 0 && Objects.equals(transfer_created, other_transfer.transfer_created);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, user_id, amount);
  }

  public UUID getUuid(){
    return uuid;
  }

  public void setUuid(UUID new_id){
    this.uuid = new_id;
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

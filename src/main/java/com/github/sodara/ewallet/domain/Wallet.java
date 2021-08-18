package com.github.sodara.ewallet.domain;
/*
  Handles wallet items from the database
 */

import java.util.Objects;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("wallets")
public class Wallet {
  @PrimaryKey
  private int user_id;
  private String name;
  private double balance;

  public Wallet() {
  }

  public Wallet(int user_id, String name, double balance) {
    this.user_id = user_id;
    this.name = name;
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "Wallet{" +
        "user_id=" + this.user_id +
        ", name='" + this.name + '\'' +
        ", balance=" + this.balance +
        '}';
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    Wallet other_wallet = (Wallet) other;
    return user_id == other_wallet.user_id && Double.compare(balance, other_wallet.balance) == 0 && Objects.equals(name, other_wallet.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user_id, name, balance);
  }

  public int getUserId() {
    return user_id;
  }

  public void setUserId(int new_id) {
    this.user_id = new_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String new_name) {
    this.name = name;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double new_balance) {
    this.balance = new_balance;
  }
}

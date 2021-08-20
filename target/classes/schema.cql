CREATE KEYSPACE IF NOT EXISTS eWallets WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE eWallets;

CREATE TABLE IF NOT EXISTS wallets (
  user_id int,
  name text,
  balance double,
  PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS transfers (
  transfer_id uuid,
  user_id int,
  amount double,
  transfer_created timestamp,
  PRIMARY KEY (user_id, transfer_id)
);
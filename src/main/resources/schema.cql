CREATE KEYSPACE IF NOT EXISTS eWallets WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE eWallets;

CREATE TABLE IF NOT EXISTS wallets (
  user_id int,
  name text,
  amount double,
  PRIMARY KEY (user_id, name)
);
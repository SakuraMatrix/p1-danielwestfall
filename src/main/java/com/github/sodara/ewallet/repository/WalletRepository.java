package com.github.sodara.ewallet.repository;

import com.github.sodara.ewallet.domain.Wallet;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WalletRepository extends ReactiveCassandraRepository<Wallet, Integer> {
  @AllowFiltering
  Flux<Wallet> findByName(int name);
}
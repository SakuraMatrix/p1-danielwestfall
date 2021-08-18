package com.github.sodara.ewallet.repository;

import com.github.sodara.ewallet.domain.Wallet;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransferRepository extends ReactiveCassandraRepository<Wallet, Integer> {
  @AllowFiltering
  Flux<Wallet> findByUserId(int u_id);
  @AllowFiltering
  Flux<Wallet> findByName(int name);
}

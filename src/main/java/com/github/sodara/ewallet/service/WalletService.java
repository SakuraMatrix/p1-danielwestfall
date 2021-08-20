package com.github.sodara.ewallet.service;

import com.github.sodara.ewallet.domain.Wallet;
import com.github.sodara.ewallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletService {
  @Autowired
  private final WalletRepository walletRepository;

  public WalletService(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  public Flux<Wallet> getAll() {
    return walletRepository.getAll();
  }

  public Mono<Wallet> getWallet(int id) {
    return walletRepository.get(id);
  }

  public Wallet create(Wallet wallet) {
    return walletRepository.create(wallet);
  }
}

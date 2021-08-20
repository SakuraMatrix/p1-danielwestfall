package com.github.sodara.ewallet.service;

import com.github.sodara.ewallet.domain.Transfer;
import com.github.sodara.ewallet.domain.Wallet;
import com.github.sodara.ewallet.repository.TransferRepository;
import com.github.sodara.ewallet.repository.WalletRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransferService {
  @Autowired
  private final TransferRepository transferRepository;
  @Autowired
  private final WalletRepository walletRepository;

  public TransferService(TransferRepository transferRepository, WalletRepository walletRepository) {
    this.transferRepository = transferRepository;
    this.walletRepository = walletRepository;
  }

  public Flux<Transfer> getAll() {
    return transferRepository.getAll();
  }

  public Flux<Transfer> getAllByUserID(int user_id) {
    return transferRepository.get(user_id);
  }

  public Transfer create(Transfer transfer) {
    Mono<Wallet> mono_wallet = walletRepository.findById(transfer.getUserId());
    Wallet my_wallet = mono_wallet.block();
    if (my_wallet != null) {
      my_wallet.setBalance(my_wallet.getBalance() + transfer.getAmount());
      walletRepository.save(my_wallet);
      return transferRepository.create(transfer);
    }
    else {return null;}
  }
}

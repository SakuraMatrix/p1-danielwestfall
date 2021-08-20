package com.github.sodara.ewallet.service;

import com.github.sodara.ewallet.domain.Transfer;
import com.github.sodara.ewallet.domain.Wallet;
import com.github.sodara.ewallet.repository.TransferRepository;
import com.github.sodara.ewallet.repository.WalletRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
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

  public Flux<Transfer> getTransfersByUserID(int user_id) {
    return transferRepository.get(user_id);
  }

  public Transfer create(Transfer transfer) {
    Disposable my_wallet = walletRepository.get(transfer.getUserId()).map(wallet -> {
      wallet.setBalance(wallet.getBalance() + transfer.getAmount());
      walletRepository.create(wallet);
      return wallet;
    }).subscribe();
    return transferRepository.create(transfer);
  }
}

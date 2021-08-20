package com.github.sodara.ewallet.service;

import com.github.sodara.ewallet.domain.Transfer;
import java.util.UUID;
import com.github.sodara.ewallet.repository.TransferRepository;
import com.github.sodara.ewallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

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
    walletRepository.get(transfer.getUserId()).map(wallet -> {
      wallet.setBalance(wallet.getBalance() + transfer.getAmount());
      walletRepository.create(wallet);
      return wallet;
    }).subscribe();
    return transferRepository.create(transfer);
  }
  public String delete(Transfer transfer) {
    transferRepository.getByTransferID(
      transfer.getUuid()).map(my_transfer -> {
        walletRepository.get(my_transfer.getUserId()).map(wallet -> {
          wallet.setBalance(wallet.getBalance() - my_transfer.getAmount());
          walletRepository.create(wallet);
          transferRepository.destroy(my_transfer.getUuid(), my_transfer.getUserId());
          return wallet;
      }).log().subscribe();
      return my_transfer;
    }).log().subscribe();
    return "Complete";
  }
}

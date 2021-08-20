package com.github.sodara.ewallet.controller;

import com.github.sodara.ewallet.domain.Transfer;
import com.github.sodara.ewallet.service.TransferService;
import com.github.sodara.ewallet.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/transfers")
public class TransferController {
  private final TransferService transferService;
  private final WalletService walletService;

  public TransferController(TransferService transferService, WalletService walletService) {
    this.transferService = transferService;
    this.walletService = walletService;
  }

  @GetMapping("")
  public Flux<Transfer> getAll() {
    return transferService.getAll();
  }

  @GetMapping("/{user_id}")
  public Flux<Transfer> get(@PathVariable("user_id") int user_id) {
    return transferService.getAllByUserID(user_id);
  }
}

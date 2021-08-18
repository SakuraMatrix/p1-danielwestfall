package com.github.sodara.ewallet.controller;

import com.github.sodara.ewallet.domain.Wallet;
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
@RequestMapping(value = "/wallet")
public class WalletController {
  private final WalletService walletService;

  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @GetMapping("")
  public Flux<Wallet> getAll() {
    return walletService.getAll();
  }

  @GetMapping("/{id}")
  public Mono<Wallet> get(@PathVariable("id") int id) {
    return walletService.get(id);
  }

  @PostMapping("")
  public Mono<Wallet> create(@RequestBody Wallet wallet) {
    return walletService.create(wallet);
  }
}

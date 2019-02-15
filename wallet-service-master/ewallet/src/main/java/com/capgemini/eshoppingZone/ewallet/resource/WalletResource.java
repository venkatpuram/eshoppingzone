package com.capgemini.eshoppingZone.ewallet.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.eshoppingZone.ewallet.pojo.Ewallet;
import com.capgemini.eshoppingZone.ewallet.pojo.Statement;
import com.capgemini.eshoppingZone.ewallet.service.EwalletService;

@RestController
@RequestMapping("/wallets")
public class WalletResource {

	private final static String Deposit = "Money Deposit";
	private final static String Withdraw = "Money Withdraw";

	@Autowired
	EwalletService service;

	@PostMapping(value="/{walletId}")
	public void addNewWallet(@PathVariable Integer walletId) {
		Ewallet wallet = new Ewallet();
		wallet.setWalletId(walletId);
		wallet.setCurrentBalance(100.0);
		service.addWallet(wallet);
		
	}

	@GetMapping
	// @JsonDeserialize
	public ResponseEntity<List<Ewallet>> getAllWallet() {
		List<Ewallet> wallets = service.getWallets();
		if (wallets.isEmpty())
			return new ResponseEntity<List<Ewallet>>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<List<Ewallet>>(wallets, HttpStatus.OK);
	}

	@GetMapping("/{walletId}")
	public Double getById(@PathVariable int walletId) {
		Ewallet wallet = service.getById(walletId);
		if (wallet.equals(null))
			return null;
		else
			return wallet.getCurrentBalance();
	}

	@PutMapping("/{walletId}")
	public ResponseEntity<Ewallet> AddMoney(@PathVariable int walletId, @RequestParam("currentBalance") double amount) {
		Ewallet wallet = service.getById(walletId);
		if (amount > 0) {
			wallet.setCurrentBalance(wallet.getCurrentBalance() + amount);
			service.addmoney(wallet, amount, Deposit);
			return new ResponseEntity<>(wallet,HttpStatus.OK);
		}

		return new ResponseEntity<>(wallet,HttpStatus.NOT_ACCEPTABLE);
	}

	@PutMapping("/pay/{walletId}")
	public ResponseEntity<Ewallet> payMoney(@PathVariable int walletId, @RequestParam("currentBalance") double amount,@RequestParam("orderId") int orderId) {
		Ewallet wallet = service.getById(walletId);
		double currentBalance = wallet.getCurrentBalance();
		if (amount < currentBalance && amount > 0) {
			wallet.setCurrentBalance(wallet.getCurrentBalance() - amount);
			service.update(wallet, amount, Withdraw,orderId);
			return new ResponseEntity<Ewallet>(wallet,HttpStatus.OK);
		}
		return new ResponseEntity<Ewallet>(HttpStatus.NOT_ACCEPTABLE);
	}

	@GetMapping("/statements/{walletId}")
	public ResponseEntity<List<Statement>> getStatementsById(@PathVariable int walletId) {
		List<Statement> statements = service.getStatementsById(walletId);
		if (statements.isEmpty())
			return new ResponseEntity<List<Statement>>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<List<Statement>>(statements, HttpStatus.OK);
	}

	@GetMapping("/statements")
	public List<Statement> getStatements() {
		List<Statement> statements = service.getStatements();
		if (statements.isEmpty())
			return null;
		return statements;
	}

	@DeleteMapping("/{walletId}")
	public void deleteById(@PathVariable int walletId) {
		service.deleteByid(walletId);
	}

}

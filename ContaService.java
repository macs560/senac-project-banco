package br.edu.sc.senac.demo.project.banco;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Conta")

public class ContaService {
	private static Map<String, Conta> contas = new HashMap<String, Conta>();

	@PostMapping("/cadastrocliente/{titular}/{numeroConta}")
	public Conta cadastrarConta(@PathVariable String titular, @PathVariable String numeroConta) {
		Conta conta = new Conta(titular, numeroConta, 100);
		contas.put(numeroConta, conta);
		return conta;
	}

	@GetMapping("Saque/{numeroConta}/{ValorSaque}")
	public ResponseEntity<Conta> efetuarSaque(@RequestBody String buscaNumeroConta, @PathVariable double valorSaque) {
		
		if (!contas.containsKey(buscaNumeroConta)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Conta conta = contas.get(buscaNumeroConta);
		conta.sacar(valorSaque);
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}

	@GetMapping("transferencia/{numeroContaOrigem}/{numeroContaDestino}/{ValorTransferencia}")
	public ResponseEntity<Conta> efetuarTransferencia(@RequestBody String numeroContaOrigem,
			@RequestBody String numeroContaDestino, @PathVariable double valor) {
		if (!contas.containsKey(numeroContaDestino) || !contas.containsKey(numeroContaOrigem)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Conta contaDestino = contas.get(numeroContaDestino);
		contas.get(numeroContaOrigem).transferirPara(contaDestino, valor);
		return new ResponseEntity<>(contaDestino, HttpStatus.OK);
	}

	@GetMapping("transferencia/{numeroContaOrigem}/{numeroContaDestino}/{ValorTransferencia}")
	public ResponseEntity<Conta> efetuarDeposito(@RequestBody String numeroContaOrigem,
			@PathVariable double valorDeposito) {
		if (!contas.containsKey(numeroContaOrigem)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Conta conta = contas.get(numeroContaOrigem);
		conta.depositar(valorDeposito);
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}

	@GetMapping("dadosConta/{numeroConta}")
	public ResponseEntity<Conta> imprimirDados(@RequestBody Conta conta, @RequestBody String numeroConta) {
		if (!contas.containsKey(numeroConta)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String texto = conta.toString();
		JOptionPane.showMessageDialog(null, texto);
		System.out.println(texto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

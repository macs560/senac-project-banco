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
	public ResponseEntity<Conta> efetuarSaque(@RequestBody Conta conta) {
		String buscaNumeroConta = conta.getNumeroConta();
		if (contas.containsKey(buscaNumeroConta)) {
			Conta contaAtual = contas.get(buscaNumeroConta);
			efetuarSaque(contaAtual);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("Deposito/{numeroContaOrigem}/{numeroContaDestino}/{ValorTransferencia}")
	public ResponseEntity<Conta> efetuarTransferencia(@RequestBody String numeroContaOrigem,
			@RequestBody String numeroContaDestino) {
		if (contas.containsKey(numeroContaDestino)) {
			efetuarTransferencia(numeroContaOrigem, numeroContaDestino);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("dadosConta/{numeroConta}")
	public ResponseEntity<Conta> imprimirDados(@RequestBody Conta conta, @RequestBody String numeroConta) {
		if (contas.containsKey(numeroConta)) {
			String texto = conta.toString();
			JOptionPane.showMessageDialog(null, texto);
			System.out.println(texto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}

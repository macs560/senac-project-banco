package br.edu.sc.senac.demo.project.banco;

public class Conta {
	private String titular;
	private double saldo;
	private String numeroConta;

	public Conta(String numeroConta, String titular, double saldo) {
		this.numeroConta = numeroConta;
		this.titular = titular;
		this.saldo = saldo;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}

	public double getSaldo() {
		return saldo;
	}

	@Override
	public String toString() {
		return "Numero da conta: " + this.numeroConta + "\nTitular: " + this.titular + "\nSaldo: " + this.saldo;
	}

	public void depositar(double valorDeposito) {
		this.saldo += valorDeposito;
	}

	public boolean sacar(double valorSaque) {
		if (valorSaque > this.saldo) {
			return false;
		}
		this.saldo -= valorSaque;
		return true;
	}

	public boolean transferirPara(Conta contaDestino, double valor) {
		boolean isPodeTransferir = this.sacar(valor);
		if (isPodeTransferir) {
			contaDestino.depositar(valor);
		}
		return isPodeTransferir;
	} 
}

package com.gustavo.kayan.igor.entity;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Emprestimo {

	private Long id;

	private Date dataEmprestimo;

	private Date dataDevolucao;

	private String observacao;

	private Cliente cliente;

	private Filme filme;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	public String getEmprestimoFormatado() {
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.dataEmprestimo.toLocalDate().format(dataFormatada).toString();
	}
	
	public String getDevolucaoFormatado() {
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.dataDevolucao.toLocalDate().format(dataFormatada).toString();
	}
	
}

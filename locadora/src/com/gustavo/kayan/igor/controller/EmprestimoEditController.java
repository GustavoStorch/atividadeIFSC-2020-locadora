package com.gustavo.kayan.igor.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.gustavo.kayan.igor.entity.Cliente;
import com.gustavo.kayan.igor.entity.Emprestimo;
import com.gustavo.kayan.igor.entity.Filme;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EmprestimoEditController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private GridPane pnlDados;

	@FXML
	private Label lblCliente;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblEmprestimo;

	@FXML
	private ComboBox<Filme> cbxFilme;

	@FXML
	private Label lblDevolucao;

	@FXML
	private ComboBox<Cliente> cbxCliente;

	@FXML
	private DatePicker dtpEmprestimo;

	@FXML
	private DatePicker dtpDevolucao;

	@FXML
	private Label lblObservacao;

	@FXML
	private TextField txtObservacao;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Button btnCancela;

	private Stage janelaEmprestimoEdit;

	private Emprestimo emprestimo;

	private boolean okClick = false;

	private ClienteListaController clienteListaController;
	private FilmeListaController filmeListaController;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaEmprestimoEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.emprestimo.setCliente(this.cbxCliente.getSelectionModel().getSelectedItem());
			this.emprestimo.setFilme(this.cbxFilme.getSelectionModel().getSelectedItem());
			this.emprestimo.setDataEmprestimo(Date.valueOf(this.dtpEmprestimo.getValue()));
			this.emprestimo.setDataDevolucao(Date.valueOf(this.dtpDevolucao.getValue()));
			this.emprestimo.setObservacao(this.txtObservacao.getText());

			this.okClick = true;
			this.getJanelaEmprestimoEdit().close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.clienteListaController = new ClienteListaController();
		this.filmeListaController = new FilmeListaController();

		this.carregarComboBoxClientes();
		this.carregarComboBoxFilmes();
	}

	public Stage getJanelaEmprestimoEdit() {
		return janelaEmprestimoEdit;
	}

	public void setJanelaEmprestimoEdit(Stage janelaEmprestimoEdit) {
		this.janelaEmprestimoEdit = janelaEmprestimoEdit;
	}

	public void populaTela(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;

		this.txtObservacao.setText(this.emprestimo.getObservacao());

		if (this.emprestimo.getDataEmprestimo() != null) {
			this.dtpEmprestimo.setValue(this.emprestimo.getDataEmprestimo().toLocalDate());
		}

		if (this.emprestimo.getDataDevolucao() != null) {
			this.dtpDevolucao.setValue(this.emprestimo.getDataDevolucao().toLocalDate());
		}

		if (this.emprestimo.getCliente() != null) {
			this.cbxCliente.setValue(this.emprestimo.getCliente());
		}

		if (this.emprestimo.getFilme() != null) {
			this.cbxFilme.setValue(this.emprestimo.getFilme());
		}
	}

	public boolean isOkClick() {
		return okClick;
	}

	public void carregarComboBoxClientes() {
		ObservableList<Cliente> observableListaClientes = FXCollections
				.observableArrayList(this.clienteListaController.retornaListagemCliente());

		this.cbxCliente.setItems(observableListaClientes);
	}

	public void carregarComboBoxFilmes() {
		ObservableList<Filme> observableListaFilme = FXCollections
				.observableArrayList(this.filmeListaController.retornaListagemFilme());

		this.cbxFilme.setItems(observableListaFilme);
	}

	private boolean validarCampos() {
		String mensagemErro = new String();

		if (this.cbxCliente.getSelectionModel().getSelectedItem() == null
				|| this.cbxCliente.getTypeSelector().trim().length() == 0) {
			mensagemErro += "Informe o nome do cliente!\n";
		}
		if (this.cbxFilme.getSelectionModel().getSelectedItem() == null
				|| this.cbxFilme.getTypeSelector().trim().length() == 0) {
			mensagemErro += "Informe o nome do filme!\n";
		}
		if (this.dtpEmprestimo.getValue() == null || this.dtpEmprestimo.getTypeSelector().trim().length() == 0) {
			mensagemErro += "Informe a data de empréstimo do filme!\n";
		}
		if (this.dtpDevolucao.getValue() == null || this.dtpDevolucao.getTypeSelector().trim().length() == 0) {
			mensagemErro += "Informe a data de devolução do filme!\n";
		}
		if (mensagemErro.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaEmprestimoEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErro);
			alerta.showAndWait();

			return false;
		}
	}
}
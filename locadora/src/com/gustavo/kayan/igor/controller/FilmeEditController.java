package com.gustavo.kayan.igor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gustavo.kayan.igor.entity.Filme;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FilmeEditController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private GridPane pnlDados;

	@FXML
	private Label lblFilme;

	@FXML
	private TextField txtFilme;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Button btnCancela;

	private Stage janelaFilmeEdit;

	private Filme filme;

	private boolean okClick = false;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaFilmeEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.filme.setNome(this.txtFilme.getText());

			this.okClick = true;
			this.getJanelaFilmeEdit().close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Stage getJanelaFilmeEdit() {
		return janelaFilmeEdit;
	}

	public void setJanelaFilmeEdit(Stage janelaFilmeEdit) {
		this.janelaFilmeEdit = janelaFilmeEdit;
	}

	private boolean validarCampos() {
		String mensagemErro = new String();

		if (this.txtFilme.getText() == null || this.txtFilme.getText().trim().length() == 0) {
			mensagemErro += "Informe o nome do filme!\n";
		}
		if (mensagemErro.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaFilmeEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErro);
			alerta.showAndWait();

			return false;
		}
	}

	public boolean isOkClick() {
		return okClick;
	}

	public void populaTela(Filme filme) {
		this.filme = filme;

		this.txtFilme.setText(filme.getNome());
	}

}

package com.gustavo.kayan.igor.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable {

	@FXML
	private VBox pnlPrincipal;

	@FXML
	private MenuBar barMenu;

	@FXML
	private Menu mnuCadastro;

	@FXML
	private MenuItem mnoClientes;

	@FXML
	private MenuItem mnoFilmes;

	@FXML
	private MenuItem mnoEmprestimo;

	@FXML
	private SeparatorMenuItem sepCadastro;

	@FXML
	private MenuItem mnoSair;

	@FXML
	private Menu mnuAjuda;

	@FXML
	private MenuItem mnoSobre;

	@FXML
	private AnchorPane pnlMeio;

	@FXML
	private HBox pnlStatuBar;

	@FXML
	private Label lblData;

	@FXML
	private Separator sepData;

	@FXML
	private Label lblHora;

	private Stage stage;

	@FXML
	void onClickMnoCliente(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gustavo/kayan/igor/view/ClienteLista.fxml"));
			Parent clienteListaXML = loader.load();

			ClienteListaController clienteListaController = loader.getController();
			Scene clienteListaLayout = new Scene(clienteListaXML);
			
			this.getStage().setScene(clienteListaLayout);
			this.getStage().setTitle("Cadastro de clientes");

			this.getStage().setOnCloseRequest(e -> {
				if (clienteListaController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
			stage.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/gustavo/kayan/igor/view/icone.png")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickMnoEmprestimo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gustavo/kayan/igor/view/EmprestimoLista.fxml"));
			Parent emprestimoListaXML = loader.load();

			EmprestimoListaController emprestimoListaController = loader.getController();
			Scene emprestimoListaLayout = new Scene(emprestimoListaXML);

			this.getStage().setScene(emprestimoListaLayout);
			this.getStage().setTitle("Locação de filmes");

			this.getStage().setOnCloseRequest(e -> {
				if (emprestimoListaController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
			stage.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/gustavo/kayan/igor/view/icone.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickMnoFilme(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gustavo/kayan/igor/view/FilmeLista.fxml"));
			Parent filmeListaXML = loader.load();

			FilmeListaController filmeListaController = loader.getController();
			Scene filmeListaLayout = new Scene(filmeListaXML);

			this.getStage().setScene(filmeListaLayout);
			this.getStage().setTitle("Cadastro de filmes");

			this.getStage().setOnCloseRequest(e -> {
				if (filmeListaController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
			stage.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/gustavo/kayan/igor/view/icone.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickMnoSair(ActionEvent event) {
		if (this.onCloseQuery()) {
			System.exit(0);
		} else {
			event.consume();
		}
	}

	@FXML
	void onClickMnoSobre(ActionEvent event) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle("Sobre");
		alerta.setHeaderText(
				"Sistema desenvolvido pela equipe: Gustavo Storch,\n Kayan Cioato & Igor Matheus \nDesenvolvimento de sistemas 2° fase.");
		alerta.showAndWait();
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.configuraBarraStatus();
		this.configuraStage();
	}

	public void configuraStage() {
		this.setStage(new Stage());
		this.getStage().initModality(Modality.APPLICATION_MODAL);
		this.getStage().resizableProperty().setValue(Boolean.FALSE);
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do sistema?");
		ButtonType botaoNao = ButtonType.NO;
		ButtonType botaoSim = ButtonType.YES;
		alerta.getButtonTypes().setAll(botaoSim, botaoNao);
		Optional<ButtonType> resultado = alerta.showAndWait();
		return resultado.get() == botaoSim ? true : false;
	}

	public void configuraBarraStatus() {
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.lblData.setText("Data: " + LocalDateTime.now().format(dataFormatada));

		Timeline relogio = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter horaFormatada = DateTimeFormatter.ofPattern("HH:mm");
			this.lblHora.setText("Hora: " + LocalDateTime.now().format(horaFormatada));
		}), new KeyFrame(Duration.seconds(1)));
		relogio.setCycleCount(Animation.INDEFINITE);
		relogio.play();
	}

}

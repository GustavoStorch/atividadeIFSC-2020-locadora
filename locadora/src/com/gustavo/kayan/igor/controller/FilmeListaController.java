package com.gustavo.kayan.igor.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gustavo.kayan.igor.dao.FilmeDAO;
import com.gustavo.kayan.igor.entity.Filme;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FilmeListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Filme> tbvFilmes;

	@FXML
	private TableColumn<Filme, Long> tbcCodigo;

	@FXML
	private TableColumn<Filme, String> tbcFilme;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblFilmeValor;

	@FXML
	private ButtonBar barBotoes;

	@FXML
	private Button btnInclur;

	@FXML
	private Tooltip tlpIncluir;

	@FXML
	private Button btnEditar;

	@FXML
	private Tooltip tlpEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Tooltip tlpExcluir;

	private List<Filme> listaFilmes;
	private ObservableList<Filme> observableListaFilmes = FXCollections.observableArrayList();
	private FilmeDAO filmeDAO;

	public static final String FILME_EDITAR = " - Editar";
	public static final String FILME_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Filme filme = this.tbvFilmes.getSelectionModel().getSelectedItem();

		if (filme != null) {
			boolean btnConfirmarClic = this.onShowTelaFilmeEditar(filme, FilmeListaController.FILME_EDITAR);

			if (btnConfirmarClic) {
				this.getFilmeDAO().update(filme, null);
				this.carregarTableViewFilmes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um filme da tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Filme filme = this.tbvFilmes.getSelectionModel().getSelectedItem();

		if (filme != null) {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclus?o do filme?\n" + filme.getNome());
			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getFilmeDAO().delete(filme);
				this.carregarTableViewFilmes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um filme da tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Filme filme = new Filme();

		boolean btnConfirmarClic = this.onShowTelaFilmeEditar(filme, FilmeListaController.FILME_INCLUIR);

		if (btnConfirmarClic) {
			this.getFilmeDAO().save(filme);
			this.carregarTableViewFilmes();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setFilmeDAO(new FilmeDAO());
		this.carregarTableViewFilmes();
		this.selecionarItemTableViewFilmes(null);
		this.tbvFilmes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewFilmes(newValue));
	}

	public List<Filme> getListaFilmes() {
		return listaFilmes;
	}

	public void setListaFilmes(List<Filme> listaFilmes) {
		this.listaFilmes = listaFilmes;
	}

	public ObservableList<Filme> getObservableListaFilmes() {
		return observableListaFilmes;
	}

	public void setObservableListaFilmes(ObservableList<Filme> observableListaFilmes) {
		this.observableListaFilmes = observableListaFilmes;
	}

	public FilmeDAO getFilmeDAO() {
		return filmeDAO;
	}

	public void setFilmeDAO(FilmeDAO filmeDAO) {
		this.filmeDAO = filmeDAO;
	}

	public List<Filme> retornaListagemFilme() {
		if (this.getFilmeDAO() == null) {
			this.setFilmeDAO(new FilmeDAO());
		}
		return this.getFilmeDAO().getAll();
	}

	public boolean onShowTelaFilmeEditar(Filme filme, String operacao) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gustavo/kayan/igor/view/FilmeEdit.fxml"));
			Parent filmeEditXML = loader.load();

			Stage janelaFilmeEditar = new Stage();
			janelaFilmeEditar.setTitle("Cadastro de filmes" + operacao);
			janelaFilmeEditar.initModality(Modality.APPLICATION_MODAL);
			janelaFilmeEditar.resizableProperty().setValue(Boolean.FALSE);
			janelaFilmeEditar.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/gustavo/kayan/igor/view/icone.png")));

			Scene filmeEditLayout = new Scene(filmeEditXML);
			janelaFilmeEditar.setScene(filmeEditLayout);

			FilmeEditController filmeEditController = loader.getController();
			filmeEditController.setJanelaFilmeEdit(janelaFilmeEditar);
			filmeEditController.populaTela(filme);

			janelaFilmeEditar.showAndWait();

			return filmeEditController.isOkClick();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de filmes?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public void carregarTableViewFilmes() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcFilme.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaFilmes(this.getFilmeDAO().getAll());
		this.setObservableListaFilmes(FXCollections.observableArrayList(this.getListaFilmes()));
		this.tbvFilmes.setItems(this.getObservableListaFilmes());
	}

	public void selecionarItemTableViewFilmes(Filme filme) {
		if (filme != null) {
			this.lblFilmeValor.setText(filme.getNome());
		} else {
			this.lblFilmeValor.setText("");
		}
	}
}
package com.gustavo.kayan.igor.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gustavo.kayan.igor.dao.EmprestimoDAO;
import com.gustavo.kayan.igor.entity.Cliente;
import com.gustavo.kayan.igor.entity.Emprestimo;
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

public class EmprestimoListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Emprestimo> tbvEmprestimos;

	@FXML
	private TableColumn<Emprestimo, Long> tbcCodigo;

	@FXML
	private TableColumn<Cliente, String> tbcCliente;

	@FXML
	private TableColumn<Filme, String> tbcFilme;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblCliente;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblClienteValor;

	@FXML
	private Label lblFilmeValor;

	@FXML
	private Label lblEmprestimo;

	@FXML
	private Label lblEmprestimoValor;

	@FXML
	private Label lblDevolucao;

	@FXML
	private Label lblDevolucaoValor;

	@FXML
	private Label lblObservacao;

	@FXML
	private Label lblObservacaoValor;

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

	private List<Emprestimo> listaEmprestimos;
	private ObservableList<Emprestimo> observableListaEmprestimos = FXCollections.observableArrayList();
	private EmprestimoDAO emprestimoDAO;

	public static final String EMPRESTIMO_EDITAR = " - Editar";
	public static final String EMPRESTIMO_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Emprestimo emprestimo = this.tbvEmprestimos.getSelectionModel().getSelectedItem();

		if (emprestimo != null) {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão da locação do cliente?\n" + emprestimo.getCliente().getNome());
			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getEmprestimoDAO().delete(emprestimo);
				this.carregarTableViewEmprestimos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um emprestimo da tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Emprestimo emprestimo = this.tbvEmprestimos.getSelectionModel().getSelectedItem();

		if (emprestimo != null) {
			boolean btnConfirmarClic = this.onShowTelaEmprestimoEditar(emprestimo,
					EmprestimoListaController.EMPRESTIMO_EDITAR);
			if (btnConfirmarClic) {
				this.getEmprestimoDAO().update(emprestimo, null);
				this.carregarTableViewEmprestimos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um empréstimo da tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Emprestimo emprestimo = new Emprestimo();

		boolean btnConfirmarClic = this.onShowTelaEmprestimoEditar(emprestimo,
				EmprestimoListaController.EMPRESTIMO_INCLUIR);

		if (btnConfirmarClic) {
			this.getEmprestimoDAO().save(emprestimo);
			this.carregarTableViewEmprestimos();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setEmprestimoDAO(new EmprestimoDAO());
		this.carregarTableViewEmprestimos();
		this.selecionarItemTableViewEmprestimos(null);

		this.tbvEmprestimos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewEmprestimos(newValue));
	}

	public List<Emprestimo> getListaEmprestimos() {
		return listaEmprestimos;
	}

	public void setListaEmprestimos(List<Emprestimo> listaEmprestimos) {
		this.listaEmprestimos = listaEmprestimos;
	}

	public ObservableList<Emprestimo> getObservableListaEmprestimos() {
		return observableListaEmprestimos;
	}

	public void setObservableListaEmprestimos(ObservableList<Emprestimo> observableListaEmprestimos) {
		this.observableListaEmprestimos = observableListaEmprestimos;
	}

	public EmprestimoDAO getEmprestimoDAO() {
		return emprestimoDAO;
	}

	public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
		this.emprestimoDAO = emprestimoDAO;
	}

	public boolean onShowTelaEmprestimoEditar(Emprestimo emprestimo, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gustavo/kayan/igor/view/EmprestimoEdit.fxml"));
			Parent emprestimoEditXML = loader.load();

			Stage janelaEmprestimoEditar = new Stage();
			janelaEmprestimoEditar.setTitle("Cadastro de empréstimos de filmes" + operacao);
			janelaEmprestimoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaEmprestimoEditar.resizableProperty().setValue(Boolean.FALSE);
			janelaEmprestimoEditar.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/gustavo/kayan/igor/view/icone.png")));

			Scene emprestimoEditLayout = new Scene(emprestimoEditXML);
			janelaEmprestimoEditar.setScene(emprestimoEditLayout);

			EmprestimoEditController emprestimoEditController = loader.getController();
			emprestimoEditController.setJanelaEmprestimoEdit(janelaEmprestimoEditar);
			emprestimoEditController.populaTela(emprestimo);

			janelaEmprestimoEditar.showAndWait();

			return emprestimoEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de empréstimos de filmes?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public void carregarTableViewEmprestimos() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		this.tbcFilme.setCellValueFactory(new PropertyValueFactory<>("filme"));

		this.setListaEmprestimos(this.getEmprestimoDAO().getAll());
		this.setObservableListaEmprestimos(FXCollections.observableArrayList(this.getListaEmprestimos()));
		this.tbvEmprestimos.setItems(this.getObservableListaEmprestimos());

	}

	public void selecionarItemTableViewEmprestimos(Emprestimo emprestimo) {
		if (emprestimo != null) {
			this.lblClienteValor.setText(emprestimo.getCliente().getNome());
			this.lblFilmeValor.setText(emprestimo.getFilme().getNome());
			this.lblEmprestimoValor.setText(emprestimo.getEmprestimoFormatado());
			this.lblDevolucaoValor.setText(emprestimo.getDevolucaoFormatado());
			this.lblObservacaoValor.setText(emprestimo.getObservacao());
		} else {
			this.lblClienteValor.setText("");
			this.lblFilmeValor.setText("");
			this.lblEmprestimoValor.setText("");
			this.lblDevolucaoValor.setText("");
			this.lblObservacaoValor.setText("");
		}
	}
}
package com.gustavo.kayan.igor.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gustavo.kayan.igor.dao.ClienteDAO;
import com.gustavo.kayan.igor.entity.Cliente;

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

public class ClienteListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Cliente> tbvClientes;

	@FXML
	private TableColumn<Cliente, Long> tbcCodigo;

	@FXML
	private TableColumn<Cliente, String> tbcNome;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblTelefone;

	@FXML
	private Label lblNomeValor;

	@FXML
	private Label lblTelefoneValor;

	@FXML
	private Label lblEndereco;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblEnderecoValor;

	@FXML
	private Label lblEmailValor;

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

	private List<Cliente> listaClientes;
	private ObservableList<Cliente> observableListaClientes = FXCollections.observableArrayList();
	private ClienteDAO clienteDAO;

	public static final String CLIENTE_EDITAR = " - Editar";
	public static final String CLIENTE_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Cliente cliente = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (cliente != null) {
			boolean btnConfirmarClic = this.onShowTelaClienteEditar(cliente, ClienteListaController.CLIENTE_EDITAR);

			if (btnConfirmarClic) {
				this.getClienteDAO().update(cliente, null);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um cliente da tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Cliente cliente = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (cliente != null) {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclus�o do cliente?\n" + cliente.getNome());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getClienteDAO().delete(cliente);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um cliente da tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Cliente cliente = new Cliente();

		boolean btnConfirmarClic = this.onShowTelaClienteEditar(cliente, ClienteListaController.CLIENTE_INCLUIR);

		if (btnConfirmarClic) {
			this.getClienteDAO().save(cliente);
			this.carregarTableViewClientes();

		}
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ObservableList<Cliente> getObservableListaClientes() {
		return observableListaClientes;
	}

	public void setObservableListaClientes(ObservableList<Cliente> observableListaClientes) {
		this.observableListaClientes = observableListaClientes;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setClienteDAO(new ClienteDAO());
		this.carregarTableViewClientes();
		this.selecionarItemTableViewClientes(null);

		this.tbvClientes.getSelectionModel().selectedItemProperty()
				.addListener((Observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
	}

	public boolean onShowTelaClienteEditar(Cliente cliente, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gustavo/kayan/igor/view/ClienteEdit.fxml"));
			Parent clienteEditXML = loader.load();

			Stage janelaClienteEditar = new Stage();
			janelaClienteEditar.setTitle("Cadastro de clientes" + operacao);
			janelaClienteEditar.initModality(Modality.APPLICATION_MODAL);
			janelaClienteEditar.resizableProperty().setValue(Boolean.FALSE);
			janelaClienteEditar.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/gustavo/kayan/igor/view/icone.png")));

			Scene clienteEditLayout = new Scene(clienteEditXML);
			janelaClienteEditar.setScene(clienteEditLayout);

			ClienteEditController clienteEditController = loader.getController();
			clienteEditController.setJanelaClienteEdit(janelaClienteEditar);
			clienteEditController.populaTela(cliente);

			janelaClienteEditar.showAndWait();
			return clienteEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void carregarTableViewClientes() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaClientes(this.getClienteDAO().getAll());
		this.setObservableListaClientes(FXCollections.observableArrayList(this.getListaClientes()));
		this.tbvClientes.setItems(this.getObservableListaClientes());
	}

	public void selecionarItemTableViewClientes(Cliente cliente) {
		if (cliente != null) {
			this.lblNomeValor.setText(cliente.getNome());
			this.lblTelefoneValor.setText(cliente.getTelefone());
			this.lblEnderecoValor.setText(cliente.getEndereco());
			this.lblEmailValor.setText(cliente.getEmail());
		} else {
			this.lblNomeValor.setText("");
			this.lblTelefoneValor.setText("");
			this.lblEnderecoValor.setText("");
			this.lblEmailValor.setText("");
		}
	}

	public List<Cliente> retornaListagemCliente() {
		if (this.getClienteDAO() == null) {
			this.setClienteDAO(new ClienteDAO());
		}
		return this.getClienteDAO().getAll();
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de clientes?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}
}
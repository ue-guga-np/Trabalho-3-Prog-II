/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uenp.cadastroclientes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaPrincipalController implements Initializable {

    @FXML
    private TextField nome;
    
    @FXML
    private TextField cep;
            
    @FXML        
    private TextField rua;
            
    @FXML        
    private TextField numero;
            
    @FXML        
    private TextField cidade;
            
    @FXML        
    private TextField estado;
            
    @FXML        
    private TextField telefone;
    
    @FXML
    private ArrayList<Cliente> clientes;
    
    @FXML
    private TableColumn<Cliente, Integer> colunaCodigo;
    
    @FXML
    private TableView<Cliente> tabelaClientes;
    
    @FXML
    private TableColumn<Cliente, String> colunaNome;
    
    @FXML
    private TableColumn<Cliente, String> colunaCidade;
    
    @FXML
    private TableColumn<Cliente, String> colunaEstado;
    
    @FXML
    private TableColumn<Cliente, String> colunaTelefone;
    
    @FXML
    private void Buscar() throws IOException {     // buscar cep
            
        Endereco enderecoCep = null;
        
        try {
            enderecoCep = Buscador.buscar(cep.getText().strip());
        }
        catch(IllegalArgumentException iae) // formato não válido
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(iae.getMessage());
            alert.show();
        }
        catch(IOException ioe)  // não foi possível conectar ao servidor OU cep não encontrado
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(ioe.getMessage());
            alert.show();
        }
        
        rua.setText(enderecoCep.getRua());
        cidade.setText(enderecoCep.getCidade());
        estado.setText(enderecoCep.getEstado());
        
    }
    
    @FXML
    private void Limpar() {
        nome.setText("");
        cep.setText("");
        rua.setText("");
        numero.setText("");
        cidade.setText("");
        estado.setText("");
        telefone.setText("");
    }
    
    @FXML
    private void Gravar() {
        
        try {
            ExcessaoCamposObrigatorios.Continuar(nome.getText().strip(), cep.getText().strip(), rua.getText().strip(), numero.getText().strip(), cidade.getText().strip(), estado.getText().strip(), telefone.getText().strip());
        }
        catch(ExcessaoCamposObrigatorios eco){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(eco.getMessage());
            alert.show();
            return;
        }
        
        Endereco endereco = new Endereco(cep.getText().strip(), rua.getText().strip(), numero.getText().strip(), cidade.getText().strip(), estado.getText().strip());
        Cliente cliente = new Cliente(nome.getText().strip(), telefone.getText().strip(), endereco);
        
        clientes.add(cliente);
        
        tabelaClientes.setItems(FXCollections.observableArrayList(clientes));
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Cliente cadastrado com sucesso!");
        alert.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientes = new ArrayList();
        
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        colunaCidade.setCellValueFactory(cid -> {
            return new SimpleStringProperty(
                    cid.getValue().getEndereco().getCidade()
            );
        });
        colunaEstado.setCellValueFactory(cid -> {
            return new SimpleStringProperty(
                    cid.getValue().getEndereco().getEstado()
            );
        });
        
        
    }    
}

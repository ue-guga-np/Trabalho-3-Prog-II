package uenp.cadastroclientes;

import java.io.IOException;

public class ExcessaoCamposObrigatorios extends Exception{
    public ExcessaoCamposObrigatorios(String mensagem){
        super(mensagem);
    }
    
    public static void Continuar(String nome, String cep, String rua, String numero, String cidade, String estado, String telefone) throws ExcessaoCamposObrigatorios{
        
        if(nome.equals("") || cep.equals("") || rua.equals("") || numero.equals("") || cidade.equals("") || estado.equals("") || telefone.equals("") ){
            throw new ExcessaoCamposObrigatorios("É necessário preencher os campos para concluir o cadastro.");
        }
        
        try {
            Buscador.buscar(cep);
        }
        catch(IllegalArgumentException iae) // formato não válido
        {
            throw new ExcessaoCamposObrigatorios("Formato de CEP inválido.");
        }
        catch(IOException ioe)  // não foi possível conectar ao servidor OU cep não encontrado
        {
            throw new ExcessaoCamposObrigatorios("CEP não encontrado.");
        }
        
        if(!telefone.matches("\\d{2} \\d{4}-\\d{4}"))
        {
            throw new ExcessaoCamposObrigatorios("Formato do telefone inválido.");
        }
    }
}

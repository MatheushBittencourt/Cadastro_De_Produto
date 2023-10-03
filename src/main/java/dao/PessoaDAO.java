package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PessoaDAO {
    private Connection conexao;

    public PessoaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirPessoa(Pessoa pessoa) {
        try {
            String sql = "INSERT INTO Usuarios (NOME, SOBRENOME, USUARIO, SENHA, EMAIL, DATA) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getSobrenome());
            statement.setString(3, pessoa.getUsuario());
            statement.setString(4, pessoa.getSenha());
            statement.setString(5, pessoa.getEmail());
            statement.setDate(6, pessoa.getDataNascimento());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void excluirPessoa(String nomeUsuario) {
        try {
            String sql = "DELETE FROM Usuarios WHERE USUARIO = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, nomeUsuario);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Usuário excluído com sucesso!");
            } else {
                System.out.println("Nenhum usuário foi encontrado com o nome de usuário fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

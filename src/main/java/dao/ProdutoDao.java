package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {
    private Connection conexao;

    public ProdutoDao(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO Produtos (MODELO, MARCA, QNT, TIPO, DATA) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, produto.getModelo());
            pstmt.setString(2, produto.getMarca());
            pstmt.setInt(3, produto.getQuantidade());
            pstmt.setString(4, produto.getTipo());
            pstmt.setDate(5, (Date) produto.getData());
            pstmt.executeUpdate();
        }
    }
    public void atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE Produtos SET MODELO=?, MARCA=?, QNT=?, TIPO=?, DATA=? WHERE ID=?";

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, produto.getModelo());
            pstmt.setString(2, produto.getMarca());
            pstmt.setInt(3, produto.getQuantidade());
            pstmt.setString(4, produto.getTipo());
            pstmt.setDate(5, (Date) produto.getData());
            pstmt.setInt(6, produto.getId());
            pstmt.executeUpdate();
        }
    }

    public void deletarProduto(Produto produto) throws SQLException {
        try {
            String sql = "DELETE FROM Produtos WHERE ID = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt(1, produto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Produto> obterTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";

        try (PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("ID"));
                produto.setModelo(resultSet.getString("MODELO"));
                produto.setMarca(resultSet.getString("MARCA"));
                produto.setQuantidade(resultSet.getInt("QNT"));
                produto.setTipo(resultSet.getString("TIPO"));
                produto.setData(resultSet.getDate("DATA"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }
}


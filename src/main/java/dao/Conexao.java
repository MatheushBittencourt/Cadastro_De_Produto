package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    //Para realizar a conexão passe o caminho do banco criado no HeidSQL
    private static final String URL = "jdbc:sqlite:C:\\Users\\Edubu\\OneDrive\\Área de Trabalho\\HeidiSQL_12.5_32_Portable\\Banco";

    public static Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados!", e);
        }
    }
}
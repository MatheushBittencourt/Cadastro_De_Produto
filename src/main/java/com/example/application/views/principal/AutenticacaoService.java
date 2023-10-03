package com.example.application.views.principal;

import java.sql.*;

public class AutenticacaoService {

    public static boolean autenticar(String usuario, String senha) {
        // Conecte-se ao banco de dados e verifique se as credenciais são válidas
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Edubu\\OneDrive\\Área de Trabalho\\HeidiSQL_12.5_32_Portable\\Banco");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Usuarios WHERE USUARIO = ? AND SENHA = ?")) {
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next(); // Retorna verdadeiro se as credenciais estiverem corretas
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
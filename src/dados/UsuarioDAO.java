package dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Usuario;

public class UsuarioDAO {
    private final Connection conexao;
    public UsuarioDAO() {
            this.conexao = new ConexaoBD().getConnection();
    }
    public void adicionaUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario " +
                    "(nome, email, celular, nascimento," + 
                    "senha) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getCelular());
            ps.setDate(4, usuario.getNascimento());
            ps.setString(5, usuario.getSenha());
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao adicionar usuário!");
            throw new RuntimeException(e);
        }
    }
    public boolean logarUsuario(String usuario, String senha) {
        String sql = "SELECT * FROM Usuario " +
                    " WHERE email = ? AND senha = ?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao realizar login!");
            throw new RuntimeException(e);
        }
    }
}

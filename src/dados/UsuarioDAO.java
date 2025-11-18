package dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Extrato;
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
    
    public Usuario buscarUsuario(String email) {
        Usuario usuario = new Usuario();
        try {
            String sql = "SELECT * FROM Usuario WHERE email = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
            }
            rs.close();
            ps.close();
            return usuario;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao recuperar usuário!");
            throw new RuntimeException(e);
        }
    }
    
    public Usuario buscarUsuario(int id) {
        Usuario usuario = new Usuario();
        try {
            String sql = "SELECT * FROM Usuario WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setNascimento(rs.getDate("nascimento"));
                usuario.setSenha(rs.getString("senha"));
            }
            rs.close();
            ps.close();
            return usuario;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao recuperar usuário!");
            throw new RuntimeException(e);
        }
    }
    
    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuario SET nome = ?, email = ?, celular = ?, nascimento = ?, senha = ? WHERE id = ?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getCelular());
            ps.setDate(4, usuario.getNascimento());
            ps.setString(5, usuario.getSenha());
            ps.setInt(6, usuario.getId());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar usuário!");
            throw new RuntimeException(e);
        }
    }
}

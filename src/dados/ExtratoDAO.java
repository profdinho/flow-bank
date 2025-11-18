package dados;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import modelo.Extrato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ExtratoDAO {
    private final Connection conexao;
    public ExtratoDAO(){
        this.conexao = new ConexaoBD().getConnection();
    }
    public List<Extrato> consultarExtrato(int idUsuario) {
        List<Extrato> extrato = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Extrato WHERE id_usuario = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Extrato itemExtrato = new Extrato();
                itemExtrato.setIdUsuario(rs.getInt("id_usuario"));
                itemExtrato.setData(rs.getDate("data"));
                itemExtrato.setHora(rs.getTime("hora"));
                itemExtrato.setTransacao(rs.getString("transacao"));
                itemExtrato.setValor(rs.getDouble("valor"));
                extrato.add(itemExtrato);
            }
            rs.close();
            ps.close();
            return extrato;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao recuperar extrato!");
            throw new RuntimeException(e);
        }
    }
}

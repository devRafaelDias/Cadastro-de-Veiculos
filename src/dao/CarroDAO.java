package dao;

import beans.Carro;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for Carro.
 */
public class CarroDAO {
    private Connection conn;

    public CarroDAO() {
        this.conn = Conexao.getConexao();
    }

    
    public void inserir(Carro carro) throws Exception {
        String sql = "INSERT INTO carros (marca, modelo, cor, cambio) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setString(3, carro.getCor());
            stmt.setBoolean(4, carro.isCambio());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception("Erro ao inserir carro: " + e.getMessage(), e);
        }
    }

    public void atualizar(Carro carro) throws Exception {
        String sql = "UPDATE carros SET marca = ?, modelo = ?, cor = ?, cambio = ? WHERE id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setString(3, carro.getCor());
            stmt.setBoolean(4, carro.isCambio());
            stmt.setInt(5, carro.getId());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar carro: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) throws Exception {
        String sql = "DELETE FROM carros WHERE id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) {
            throw new Exception("Erro ao excluir carro: " + e.getMessage(), e);
        }
    }

    public Carro getCarro(int id) throws Exception {
        String sql = "SELECT * FROM carros WHERE id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // Check if there's a result
                Carro carro = new Carro();
                carro.setId(rs.getInt("id"));
                carro.setMarca(rs.getString("marca"));
                carro.setModelo(rs.getString("modelo"));
                carro.setCor(rs.getString("cor"));
                carro.setCambio(rs.getBoolean("cambio"));
                return carro;
            } else {
                throw new Exception("Nenhum carro encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Erro ao buscar carro: " + e.getMessage(), e);
        }
    }
}
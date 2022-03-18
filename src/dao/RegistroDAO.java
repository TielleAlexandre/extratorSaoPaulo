package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import model.Registro;

public class RegistroDAO {
	
	public boolean cadastrarRegistro(Registro registro, String tabela) throws Exception {
			
	    PreparedStatement pStatement = null;
		Connection connection =null;
		boolean retorno;
		String sql = "INSERT INTO "+ tabela +"(data_recebimento,data_criacao,linha,latitude,longitude, equipamento, estado_viagem, ponto_interesse)"
				+ "VALUES(?,?,?,?,?,?,?,?)";
		
		
		try {
			connection = new ConexaoBD().getConnection();
		    pStatement = connection.prepareStatement(sql);
		    pStatement.setTimestamp(1, new Timestamp(registro.getData_recebimento().getTime()));
		    pStatement.setTimestamp(2, new Timestamp(registro.getData_criacao().getTime()));
		    pStatement.setInt(3, registro.getLinha());
		    pStatement.setString(4, registro.getLatitude());
		    pStatement.setString(5, registro.getLongitude());
		    pStatement.setString(6, registro.getEquipamento());
		    pStatement.setInt(7, registro.getEstado_viagem());
		    pStatement.setInt(8, registro.getPonto_interesse());
		    retorno=pStatement.execute();
		    		
	    } catch (SQLException e) {
	        e.printStackTrace();
	    	throw new Exception("Erro ao cadastrar registro: " + e.getMessage());
	        
	    }finally{	    
	        try {
	           if(pStatement != null){pStatement.close();}
	        } catch (SQLException e) {
	            throw new Exception("Erro ao fechar pStatement:" + e);
	        }try {
	        if(connection != null){connection.close();}
	        } catch (SQLException e) {
	             throw new Exception("Erro ao fechar conexão:" + e);
	        }
	    }
	   return retorno;
	
	}
	
	public List<String> listarEquipamentos(String tabela) throws Exception {

		String sql = "select distinct equipamento" 
		        + " from " + tabela 
				+ " order by equipamento";

		PreparedStatement pStatement = null;
		Connection connection = null;
		List<String> listaEquipamentos = null;
		ResultSet rs = null;
		try {
			connection = new ConexaoBD().getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();

			if (rs != null) {
				listaEquipamentos = new ArrayList<String>();
				while (rs.next()) {
					listaEquipamentos.add(rs.getString("equipamento"));
				}

			}

		} catch (SQLException e) {
			throw new Exception("Erro ao listar equipamentos:" + e);
		} finally {

			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException e) {
				throw new Exception("Erro ao fechar pStatement:" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new Exception("Erro ao fechar conexão:" + e);
			}
		}
		return listaEquipamentos;

	}

	public List<Registro> listarRegistros(String equipamento, String tabela, String dataInicio, String dataFim) throws Exception {

		String sql = "select * from " + tabela
				   + " where equipamento=? and" 
				   + " data_recebimento between '" + dataInicio +"'"+" and '" + dataFim +"'"
				   + " order by data_recebimento";

		PreparedStatement pStatement = null;
		Connection connection = null;
		List<Registro> listaRegistros = null;
		ResultSet rs = null;
		try {
			connection = new ConexaoBD().getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, equipamento);
			rs = pStatement.executeQuery();

			if (rs != null) {
				listaRegistros = new ArrayList<Registro>();
				while (rs.next()) {
					Registro reg = new Registro();
					reg.setId(rs.getInt("id"));
					reg.setData_recebimento(rs.getTimestamp("data_recebimento"));
					reg.setData_criacao(rs.getTimestamp("data_criacao"));
					reg.setLinha(rs.getInt("linha"));
					reg.setLatitude(rs.getString("latitude"));
					reg.setLongitude(rs.getString("longitude"));
					reg.setEquipamento(rs.getString("equipamento"));
					reg.setEstado_viagem(rs.getInt("estado_viagem"));
					reg.setPonto_interesse(rs.getInt("ponto_interesse"));
					listaRegistros.add(reg);
				}

			}

		} catch (SQLException e) {
			throw new Exception("Erro ao listar registros:" + e);
		} finally {

			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException e) {
				throw new Exception("Erro ao fechar pStatement:" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new Exception("Erro ao fechar conexão:" + e);
			}
		}
		return listaRegistros;

	}

	
}

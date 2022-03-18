package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.RegistroViagem;

public class RegistroViagemDAO {

	
	public boolean cadastrarRegistro(RegistroViagem registro, String tabela) throws Exception {

		PreparedStatement pStatement = null;
		Connection connection = null;
		boolean retorno;
		String sql = "INSERT INTO "+ tabela +"(data_partida,data_chegada,linha,tempo_viagem,equipamento)"
				+ "VALUES(?,?,?,?,?)";

		try {
			connection = new ConexaoBD().getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setTimestamp(1, new Timestamp(registro.getDataPartida().getTime()));
			pStatement.setTimestamp(2, new Timestamp(registro.getDataChegada().getTime()));
			pStatement.setInt(3, registro.getLinha());
			pStatement.setLong(4, registro.getTempoViagem());
			pStatement.setString(5, registro.getEquipamento());
			retorno = pStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao cadastrar registro: " + e.getMessage());

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
		return retorno;

	}
	
	public List<RegistroViagem> listarRegistrosViagem(String equipamento, String tabela) throws Exception {

		String sql = "select id,data_partida,data_chegada"
				   + " from "+tabela 
                   + " where equipamento=?"  				
				   + " order by data_partida";

		PreparedStatement pStatement = null;
		Connection connection = null;
		List<RegistroViagem> listaRegistrosViagem = null;
		ResultSet rs = null;
		try {
			connection = new ConexaoBD().getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, equipamento);
			rs = pStatement.executeQuery();

			if (rs != null) {
				listaRegistrosViagem = new ArrayList<RegistroViagem>();
				while (rs.next()) {
					RegistroViagem reg = new RegistroViagem();
					reg.setId(rs.getInt("id"));
					reg.setDataPartida(rs.getDate("data_partida"));
					reg.setPartidaTimeStamp(rs.getString("data_partida"));
					reg.setDataChegada(rs.getDate("data_chegada"));
					reg.setChegadaTimeStamp(rs.getString("data_chegada"));
					listaRegistrosViagem.add(reg);
				}

			}

		} catch (SQLException e) {
			throw new Exception("Erro ao listar os registros de viagem:" + e);
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
		return listaRegistrosViagem;

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

	
	public boolean atualizarRegistro(RegistroViagem registro, String tabela) throws Exception {

		PreparedStatement pStatement = null;
		Connection connection = null;
		boolean retorno;
		String sql = "update "+ tabela+ " set dia_semana=?, partidaTimeStamp=?, chegadaTimeStamp=?, qtdDiasAno=?, direcao=?, tipo_dia=?, hora_dia=? where id=?";
				
		try {
			connection = new ConexaoBD().getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, registro.getDia_semana());
			pStatement.setLong(2, registro.getTimeStamp());
			pStatement.setLong(3, registro.getTimeStamp2());
			pStatement.setInt(4, registro.getData_dias());
			pStatement.setInt(5, registro.getDirecao());
			pStatement.setInt(6, registro.getTipo_dia());
			pStatement.setInt(7, registro.getHora_segundos());
			pStatement.setInt(8, registro.getId());
			retorno = pStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao atualizar registro: " + e.getMessage());

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
		return retorno;

	}



}

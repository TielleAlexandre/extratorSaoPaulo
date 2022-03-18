package model;

import java.util.Date;

public class Registro {
	
	int id;
	Date data_recebimento;
	Date data_criacao;
	int linha;
	String latitude;
	String longitude;
	String equipamento;
	int estado_viagem;
	int ponto_interesse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData_recebimento() {
		return data_recebimento;
	}
	public void setData_recebimento(Date data_recebimento) {
		this.data_recebimento = data_recebimento;
	}
	public Date getData_criacao() {
		return data_criacao;
	}
	public void setData_criacao(Date data_criacao) {
		this.data_criacao = data_criacao;
	}
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}
	public int getEstado_viagem() {
		return estado_viagem;
	}
	public void setEstado_viagem(int estado_viagem) {
		this.estado_viagem = estado_viagem;
	}
	public int getPonto_interesse() {
		return ponto_interesse;
	}
	public void setPonto_interesse(int ponto_interesse) {
		this.ponto_interesse = ponto_interesse;
	}
	
	
	

}

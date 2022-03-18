package model;

import java.util.Date;

public class RegistroViagem {
	
	private int id;
	private Date dataPartida;
	private Date dataChegada;
	private long tempoViagem;
	private String equipamento;
	private int linha;
	//usado para fazer a transformação
	private String partidaTimeStamp;
	private String chegadaTimeStamp;
	private long timeStamp;
	private long timeStamp2;
	private String dia_semana;
	private int data_dias;
	// ida - 1 ou 2 - volta
	private int direcao;
	// 1 dia últil/ 2 feriado
	private int tipo_dia;
	// 1 manhã/ 2 tarde / noite
	private int turno_dia;
	private int hora_segundos;
	
	public long getTimeStamp2() {
		return timeStamp2;
	}
	public void setTimeStamp2(long timeStamp2) {
		this.timeStamp2 = timeStamp2;
	}
	public String getChegadaTimeStamp() {
		return chegadaTimeStamp;
	}
	public void setChegadaTimeStamp(String chegadaTimeStamp) {
		this.chegadaTimeStamp = chegadaTimeStamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataPartida() {
		return dataPartida;
	}
	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}
	public Date getDataChegada() {
		return dataChegada;
	}
	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}
	public String getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}
	public long getTempoViagem() {
		return tempoViagem;
	}
	public void setTempoViagem(long tempoViagem) {
		this.tempoViagem = tempoViagem;
	}
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public String getDia_semana() {
		return dia_semana;
	}
	public void setDia_semana(String dia_semana) {
		this.dia_semana = dia_semana;
	}
	public String getPartidaTimeStamp() {
		return partidaTimeStamp;
	}
	public void setPartidaTimeStamp(String partidaTimeStamp) {
		this.partidaTimeStamp = partidaTimeStamp;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getData_dias() {
		return data_dias;
	}
	public void setData_dias(int data_dias) {
		this.data_dias = data_dias;
	}
	public int getDirecao() {
		return direcao;
	}
	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
	public int getTipo_dia() {
		return tipo_dia;
	}
	public void setTipo_dia(int tipo_dia) {
		this.tipo_dia = tipo_dia;
	}
	public int getTurno_dia() {
		return turno_dia;
	}
	public void setTurno_dia(int turno_dia) {
		this.turno_dia = turno_dia;
	}
	public int getHora_segundos() {
		return hora_segundos;
	}
	public void setHora_segundos(int hora_segundos) {
		this.hora_segundos = hora_segundos;
	}	
	
}

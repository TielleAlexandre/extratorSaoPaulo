package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.apache.logging.log4j.*;

import dao.RegistroDAO;
import dao.RegistroViagemDAO;
import model.Registro;
import model.RegistroViagem;
import service.RegistroService;

public class identificarViagensImportandoBD {
	
	
	// Pontos rota 874
	
	  /*static String latitudePartida = "-23.464983"; 
	  static String longitudePartida = "-46.690889";
	  
	  static String latitudeChegada = "-23.474956"; 
	  static String longitudeChegada = "-46.670154";*/
	 

	// Pontos rota 33642

	  //-23.474956,-46.670154
	 /* static String latitudePartida = "-23.474956"; 
	  static String longitudePartida = "-46.670154";
	 
	  //-23.464983,-46.690889
	  static String latitudeChegada = "-23.464983";
	  static String longitudeChegada = "-46.690889"; */
	 

	// Pontos rota 1425

	
	/*static String latitudePartida = "-23.474351881333323"; 
	static String longitudePartida = "-46.70804738063377";
	  
	  
	 static String latitudeChegada = "-23.520138897581383"; 
	 static String longitudeChegada  = "-46.699908870904316";*/
	 

	// Pontos rota 34193

	   //-23.520226,-46.699741
	   //-23.520138897581383, -46.699908870904316 (GTFS)
	/*  static String latitudePartida = "-23.520138897581383"; 
	  static String longitudePartida = "-46.699908870904316";	  
	  
	  // -23.474226, -46.708138 (GFTS)
	  //-23.474351881333323,-46.70804738063377
	  static String latitudeChegada = "-23.474351881333323"; 
	  static String longitudeChegada = "-46.70804738063377"; */
	 

	// Pontos rota 1015
	 //-23.552159,-46.598996
	 /* static String latitudePartida = "-23.552159"; 
	  static String longitudePartida = "-46.598996";
	  
	  //acertei viagem pelo google maps: -23.547132835611848,-46.60693679367299
	  static String latitudeChegada = "-23.547132835611848"; 
	  static String longitudeChegada  = "-46.60693679367299";*/
	 

	// Pontos rota 33783
	
	  //-23.546531, -46.605728 (trips)
	  //acertei viagem pelo google maps: -23.547132835611848,-46.60693679367299
	 /* static String latitudePartida = "-23.547132835611848"; 
	  static String longitudePartida  = "-46.60693679367299";
	  
	  //-23.552159, -46.598996
	  static String latitudeChegada = "-23.552159";
	  static String longitudeChegada = "-46.598996"; */
	 

	// Pontos rota 2455 (circular)
	   // -23.826307, -46.744105
	/*  static String latitudePartida = "-23.826307"; 
	  static String longitudePartida = "-46.744105";
	 
	  //-23.826307, -46.744105
	 static String latitudeChegada = "-23.826307"; 
	 static String longitudeChegada = "-46.744105"; */
	 

	// Pontos rota 2304

	  // -23.489037,-46.698063	
	  /*static String latitudePartida = "-23.489037"; 
	  static String longitudePartida = "-46.698063";
	  
	  //-23.526062,-46.668226
	  static String latitudeChegada = "-23.526062"; 
	  static String longitudeChegada = "-46.668226";*/
	 

	// Pontos rota 35072

	// -23.526062,-46.668226
	/*  static String latitudePartida = "-23.526062";
	  static String longitudePartida = "-46.668226";

     //-23.489037,-46.698063
     static String latitudeChegada = "-23.489037";
     static String longitudeChegada = "-46.698063"; */
	  
	// Pontos rota 125

	  
     /*static String latitudePartida = "-23.583842";
     static String longitudePartida = "-46.584827"; 
	
	  
	 static String latitudeChegada = "-23.546922";
	 static String longitudeChegada = "-46.629805";*/
	 
	  
	// Pontos rota 32893
		  
	static String latitudePartida = "-23.546831";
	static String longitudePartida = "-46.629867"; 
	
	static String latitudeChegada = "-23.583833";
	static String longitudeChegada = "-46.584727";
	
	
	// Pontos rota 747
		  
	/*static String latitudePartida = "-23.613931";
	static String longitudePartida = "-46.475926"; 
	
	static String latitudeChegada = "-23.518673";
	static String longitudeChegada = "-46.546878";*/
	
	// Pontos rota 33515
	
	/*static String latitudePartida = "-23.518745";
	static String longitudePartida = "-46.546921"; 
	
	static String latitudeChegada = "-23.613860";
	static String longitudeChegada = "-46.475912";*/
	
	
	// Pontos rota 539
		
	/*static String latitudePartida = "-23.464474";
	static String longitudePartida = "-46.663846"; 
	
	static String latitudeChegada = "-23.502823";
	static String longitudeChegada = "-46.624224";*/
	
	// Pontos rota 33307
		
	/*static String latitudePartida = "-23.502919";
	static String longitudePartida = "-46.624228"; 
	
	static String latitudeChegada = "-23.464025";
	static String longitudeChegada = "-46.663726";*/
		
	  
	static double limite = 0.03; // 20 metros
	//rota 2304 e 35072
	//static double limiteMaximoViagem = 4980;
	
	// rota 33783 e 1015
	//static double limiteMaximoViagem = 1980;	
	
	//1425/34193/125/32893/539/33307
	static double limiteMaximoViagem = 20000;	
	
	// 747 e 33515
	//static double limiteMaximoViagem = 6960;
		
	//33642 e 874
	//static double limiteMaximoViagem = 3780;
	
	static String tabelaOrigem = "rota32893_agosto";
	static String tabelaDestino = "rota32893_viagem_agosto";

	static String caracterSplit = ",";
	static RegistroService regService = new RegistroService();
	public static Logger logger;
	
	
	public static void main(String[] args) {

		int qtdRegCadastrados = 0;

		try {

			//criação do log
			Date data = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			//System.setProperty("logName", "identificando_viagens"+tabelaOrigem);
			//logger = LogManager.getLogger(identificarViagensImportandoBD.class);
			//System.out.println("IDENTIFICANDO AS VIAGENS DA ROTA: "+ tabelaOrigem + " - " + sdf.format(data));
			System.out.println("IDENTIFICANDO AS VIAGENS DA ROTA: "+ tabelaOrigem + " - " + sdf.format(data));

			//declaração das variáveis
			HashMap<String, Integer> listaGeral = new HashMap<String, Integer>();
			Registro pontoPartida = null;
			Registro pontoChegada = null;
			int diaFinal=0;
			double distanciaAnterior =0;
			List<Registro> listaRegistros = null;
			
			RegistroDAO regDAO = new RegistroDAO();
			//listar todos os equipamentos de uma rota
			List<String> listaEquipamentos = regDAO.listarEquipamentos(tabelaOrigem);
	
			for (String equipamento : listaEquipamentos) {
				//buscar os registros diários por equipamento
				for (int diaInicial = 1; diaInicial <= 31; diaInicial = diaInicial + 1) {										
					switch (diaInicial) {
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9: {
						diaFinal = diaInicial + 1;
						listaRegistros = regDAO.listarRegistros(equipamento, tabelaOrigem, "2019-08-"+"0" + Integer.toString(diaInicial)+" 04:00:00",
								"2019-08-" + "0"+diaFinal+" 03:59:59");
						break;
					}
					case 30: {
						listaRegistros = regDAO.listarRegistros(equipamento, tabelaOrigem, "2019-08-"+Integer.toString(diaInicial)+" 04:00:00",
								"2019-09-01 03:59:59");
						break;
					}
					default:
						diaFinal = diaInicial + 1;
						listaRegistros = regDAO.listarRegistros(equipamento, tabelaOrigem, "2019-08-"+Integer.toString(diaInicial)+" 04:00:00",
								"2019-08-" + diaFinal+" 03:59:59");
						break;
					}
					
					System.out.println(
							"Equipamento: " + equipamento + " Lista de registros de "+ diaInicial + " a " + diaFinal +" : "+listaRegistros.size());

					//percorrer todos os registros diários de um equipamento
					for (Registro rg : listaRegistros) {
						System.out.println("Data: " + rg.getData_recebimento() +" Pontos: " +rg.getLatitude() + "," + rg.getLongitude());
						// encontrando um ponto de partida da viagem		
						if (pontoPartida == null) {
							double distanciaPartida = verificarDistanciaDoPontoPartida(rg);
							System.out.println("Distancia Part.: "+ distanciaPartida);
							if (distanciaPartida <= limite) {
								pontoPartida = rg;
								System.out.println("");
								System.out.println("=> Ponto Partida: " + pontoPartida.getLatitude() + "," + pontoPartida.getLongitude()
										+ " Distancia: " + distanciaPartida);
								System.out.println("");
							}
						} else {
							//Se tiver um ponto de partida válido, procura-se um ponto de chegada
							if (pontoChegada == null) {								 
								double distanciaChegadaAtual = verificarDistanciaDoPontoChegada(rg);
								System.out.println("Distancia Cheg: "+ distanciaChegadaAtual);
								if (distanciaChegadaAtual <= limite) {
									//verificar se o tempo de viagem ultrapassou um limite de tempo de viagem estipulado (limiteMaximoViagem)  
									verificarPontoChegada(pontoPartida, rg);
									if (verificarPontoChegada(pontoPartida, rg)) {
										System.out.println("");
										System.out.println("Viagem descartada: Partida: " + pontoPartida.getLatitude() + ","
												+ pontoPartida.getLongitude() + " Chegada: " + rg.getLatitude() + ","
												+ rg.getLongitude());
										System.out.println("");
										pontoPartida = null;
										pontoChegada = null;
									} else { 
										pontoChegada = rg;
										distanciaAnterior = 0;
										System.out.println("");
										System.out.println("=> Ponto Chegada: " + pontoChegada.getLatitude() + "," 
										+ pontoChegada.getLongitude()
												+ " Distancia: " + distanciaChegadaAtual);
										System.out.println("");
									}
								}else {									
									//Se o ponto de chegada não for válido, execute os passos abaixo.
									
									//valor inicial para a distancia de chegada anterior 
									if(distanciaAnterior == 0) {
										distanciaAnterior = distanciaChegadaAtual;
									}	
									
									System.out.println("");
									System.out.println("Distancia Cheg. atual: " + Math.ceil(distanciaChegadaAtual));
									System.out.println("Distancia Cheg. anterior: " + Math.ceil(distanciaAnterior));
									System.out.println("");
									// Caso a distancia de chegada comece a aumentar e não a diminuir, 
									 //não há um ponto de chegada válido para cadastrar a viagem.
									if(Math.ceil(distanciaChegadaAtual) > Math.ceil(distanciaAnterior)) {
										System.out.println("");
										System.out.println("=> Data Part."+ pontoPartida.getData_recebimento() +
												" Ponto Partida: " + pontoPartida.getLatitude() + "," 
										+ pontoPartida.getLongitude()+" foi descartado.");
										System.out.println("");
										//zerando as variáveis
										pontoPartida = null;
										distanciaAnterior=0;
										//verificando se o registro atual pode ser um possível ponto de partida
										double distanciaPartida = verificarDistanciaDoPontoPartida(rg);
										System.out.println("Distancia Part: "+ distanciaPartida);
										if (distanciaPartida <= limite) {
											pontoPartida = rg;
											System.out.println("");
											System.out.println("=> Ponto Partida: " + pontoPartida.getLatitude() 
											        + "," + pontoPartida.getLongitude()
													+ " Distancia: " + distanciaPartida);
											System.out.println("");
										}else {
											System.out.println("==== Procurando novo ponto de partida.");
										}
								     }else {
									// atualizando o valor da distancia anterior
								    distanciaAnterior = Math.ceil(distanciaChegadaAtual);
								    System.out.println("Atualizando Distancia Anterior: " + distanciaAnterior);
								    }
								    
								 }
							  }		
						   }
						//Se ponto de partida e chegada forem encontrados, cadastra uma viagem  
						if (pontoPartida != null && pontoChegada != null) {
							cadastrarViagem(pontoPartida, pontoChegada);
							qtdRegCadastrados++;
							pontoPartida = null;
							pontoChegada = null;
						}
					}
					pontoChegada = null;
					pontoPartida = null;
					distanciaAnterior = 0;
				}
				
				listaGeral.put(equipamento, qtdRegCadastrados);
				qtdRegCadastrados = 0;
			}

			System.out.println("------Resumo------");
			for (String key : listaGeral.keySet()) {
				int value = listaGeral.get(key);
				System.out.println("Equipamento: " + key + " Qtd Reg => " + value);
			}

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public static double verificarDistanciaDoPontoPartida(Registro rg) {
		String latitude1 = rg.getLatitude();
		String longitude1 = rg.getLongitude();
		double distancia = regService.formulaHaversine(latitudePartida, longitudePartida, latitude1, longitude1);
		return distancia;

	}

	public static double verificarDistanciaDoPontoChegada(Registro rg) {
		String latitude1 = rg.getLatitude();
		String longitude1 = rg.getLongitude();
		double distancia = regService.formulaHaversine(latitudeChegada, longitudeChegada, latitude1, longitude1);
		return distancia;

	}

	public static boolean verificarPontoChegada(Registro pontoPartida, Registro pontoChegada) {
		boolean descartarViagem = false;
		long tempoViagem = diferencaDatas(pontoChegada.getData_recebimento(), pontoPartida.getData_recebimento());
		if (tempoViagem > limiteMaximoViagem) {
			System.out.println("Tempo de viagem superior ao limite estipulado: " + tempoViagem);
			descartarViagem = true;
		}
		return descartarViagem;
	}

	public static boolean cadastrarViagem(Registro pontoPartida, Registro pontoChegada) throws Exception {
		Registro registroPartida = pontoPartida;
		Registro registroChegada = pontoChegada;

		RegistroViagem rv = new RegistroViagem();
		rv.setDataPartida(registroPartida.getData_recebimento());
		rv.setDataChegada(registroChegada.getData_recebimento());
		rv.setLinha(registroChegada.getLinha());
		long tempoViagem = diferencaDatas(registroChegada.getData_recebimento(), registroPartida.getData_recebimento());
		rv.setTempoViagem(tempoViagem);
		rv.setEquipamento(registroPartida.getEquipamento());
		System.out.println("#Cadastrar Viagem: Dt Partida: " + registroPartida.getData_recebimento() 
		        + " "+ registroPartida.getLatitude() + ","
				+ registroPartida.getLongitude() + 
				" Dt Chegada: " + registroChegada.getData_recebimento() + 
				" "+ registroChegada.getLatitude() + ","
				+ registroChegada.getLongitude());
		
		RegistroViagemDAO rvDAO = new RegistroViagemDAO();
		return rvDAO.cadastrarRegistro(rv, tabelaDestino);

	}

	public static long diferencaDatas(Date data1, Date data2) {
		Date dateCadguia1 = data2;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial.setTime(dateCadguia1);

		Date dateCadguia2 = data1;
		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(dateCadguia2);

		long diferenca = dataFinal.getTimeInMillis() - dataInicial.getTimeInMillis();
		long diferencaSeg = diferenca / 1000; // DIFERENCA EM SEGUNDOS

		return diferencaSeg;
	}

}

package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dao.RegistroDAO;
import model.Registro;

public class ImportarDadoSemDuplicidades {

	// TODO Auto-generated method stub
	// converter timestamp
	// https://www.timestampconvert.com/?go1=true&m=08&d=01&y=2019&hours=04&min=00&sec=00&Submit=++++++Convert+to+timestamp+++++&offset=3//

	// Havernise e Raio de Coordenadas
	// https://pt.stackoverflow.com/questions/55669/identificar-se-conjunto-de-coordenadas-est%C3%A1-dentro-de-um-raio-em-android/55710

	public static void main(String[] args) {

		// String path = "/home/tielle/Imagens/Dados_São
		// Paulo_2019/DADOS_TIPO_MO_AGOSTO_2019/";
		String arquivoCSV = "C:/Users/Tielle Alexandre/Desktop/MO_19710.txt";
		// String arquivoCSV = "/home/tielle/Imagens/AmostraAnalisada/SP/rota874.csv";
				
		//Linha 3160-10
		String tabela125 = "rota125";
		String tabela32893 = "rota32893";
		
		//Linha 9006-10
		String tabela874 = "rota874";
		String tabela33642 = "rota33642";
				
		BufferedReader bufferedReader = null;
		String linha;
		String caracterSplit = ",";
		int qtdLinhasArq = 0;
		
		//contadores
		// 125
		int qtdRegCadastrados125 = 0;
		int qtdMedidasRedundantes125 = 0;
		int qtdErros125 = 0;
		// 32893
		int qtdRegCadastrados32893 = 0;
		int qtdMedidasRedundantes32893 = 0;
		int qtdErros32893 = 0;
		
		// 874
		int qtdRegCadastrados874 = 0;
		int qtdMedidasRedundantes874 = 0;
		int qtdErros874 = 0;
		//33642
		int qtdRegCadastrados33642 = 0;
		int qtdMedidasRedundantes33642 = 0;
		int qtdErros33642 = 0;		
				
		
		try {

			/*
			 * List<String> nomesArq = new ArrayList<String>();
			 * nomesArq.add("MO_19812.txt"); nomesArq.add("MO_19813.txt");
			 * nomesArq.add("MO_19814.txt");
			 */

			// for (String arquivoCSV : nomesArq) {
			bufferedReader = new BufferedReader(new FileReader(arquivoCSV));
			
			//Hash map para controle dos registros por equipamentos
			HashMap<String, Date> ultimoRegistroPorEquipamento125 = new HashMap<String, Date>();
			HashMap<String, Date> ultimoRegistroPorEquipamento32893 = new HashMap<String, Date>();
			
			HashMap<String, Date> ultimoRegistroPorEquipamento874 = new HashMap<String, Date>();			
			HashMap<String, Date> ultimoRegistroPorEquipamento33642= new HashMap<String, Date>();
			
					
			boolean prosseguir = false;

			// Descarta a primeira linha do cabeçalho;
			linha = bufferedReader.readLine();
			linha = bufferedReader.readLine();

			RegistroDAO regDAO = new RegistroDAO();

			while (linha != null && linha.length() > 0) {
				qtdLinhasArq++;
				String[] arrayLinha = linha.split(caracterSplit);

				int tam = arrayLinha.length;
				if (tam == 8) {
					if (arrayLinha[2].equals("125") || arrayLinha[2].equals("32893") 
						|| arrayLinha[2].equals("874") || arrayLinha[2].equals("33642")) {
						
						System.out.println("Linha: "+ arrayLinha[2] + " Data_Recebimento: " + arrayLinha[0] + " Equipamento: " + arrayLinha[5]);
						/*
						 * System.out.println("Data_Criação: " + arrayLinha[2]);
						 * System.out.println("Linha: " + arrayLinha[3]);
						 * System.out.println("Latitude: " + arrayLinha[4]);
						 * System.out.println("Longitude: " + arrayLinha[5]);
						 * System.out.println("Equipamento: " + arrayLinha[6]);
						 * System.out.println("Estado: " + arrayLinha[7]);
						 * System.out.println("Ponto Interesse: " + arrayLinha[8]);
						 */

						Registro registro = new Registro();
						// DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// String data2 = arrayLinha[0].substring(1, arrayLinha[0].length() - 1);
						Date dataRecebimento = format.parse(arrayLinha[0]);
						Date dataCriacao = format.parse(arrayLinha[1]);
						registro.setData_recebimento(dataRecebimento);

						// String data1 = arrayLinha[1].substring(1, arrayLinha[1].length() - 1);

						registro.setData_recebimento(dataCriacao);
						registro.setData_criacao(dataCriacao);

						registro.setLinha(Integer.parseInt(arrayLinha[2]));
						registro.setLatitude(arrayLinha[3]);
						registro.setLongitude(arrayLinha[4]);
						registro.setEquipamento(arrayLinha[5]);
						registro.setEstado_viagem(Integer.parseInt(arrayLinha[6]));
						registro.setPonto_interesse(Integer.parseInt(arrayLinha[7]));

						
						//Buscando o último registro de um equipamento de uma linha 
					    Date dataUltimoRegistro = null; 
						switch (registro.getLinha()) {
							case 125: {
								dataUltimoRegistro = ultimoRegistroPorEquipamento125.get(registro.getEquipamento());
								break;
							}
						      case 32893: {
								dataUltimoRegistro = ultimoRegistroPorEquipamento32893.get(registro.getEquipamento());
								break;
							}
							case 874: {
								dataUltimoRegistro = ultimoRegistroPorEquipamento874.get(registro.getEquipamento());
								break;							
							}
							case 33642: {
								dataUltimoRegistro = ultimoRegistroPorEquipamento33642.get(registro.getEquipamento());
								break;							
							}						
							
						}
						
						//Inserindo o último regsitro do equipamento se ele for nulo
						if (dataUltimoRegistro == null) {
							switch (registro.getLinha()) {
							case 125: {
								ultimoRegistroPorEquipamento125.put(registro.getEquipamento(), registro.getData_recebimento());
								prosseguir = true;
								break;
							}
							case 32893: {
								ultimoRegistroPorEquipamento32893.put(registro.getEquipamento(), registro.getData_recebimento());
								prosseguir = true;
								break;
							}
							case 874: {
								ultimoRegistroPorEquipamento874.put(registro.getEquipamento(), registro.getData_recebimento());
								prosseguir = true;
								break;
							}
							case 33642: {
								ultimoRegistroPorEquipamento33642.put(registro.getEquipamento(), registro.getData_recebimento());
								prosseguir = true;
								break;
							}
							
						  }	
							
						} else {
							// Diferença entre a data em análise com a data anterior já anlaisada
							// última data/hora medida pelo equipamento atual
							Date dateCadguia1 = dataUltimoRegistro;
							Calendar dataInicial = Calendar.getInstance();
							dataInicial.setTime(dateCadguia1);

							// medição atual deste equipamento
							Date dateCadguia2 = registro.getData_recebimento();
							Calendar dataFinal = Calendar.getInstance();
							dataFinal.setTime(dateCadguia2);

							long diferenca = dataFinal.getTimeInMillis() - dataInicial.getTimeInMillis();
							long diferencaSeg = diferenca / 1000; // DIFERENCA EM SEGUNDOS
							// long diferencaMin = diferenca / (60 * 1000); // DIFERENCA EM MINUTOS
							// long diferencaHoras = diferenca / (60 * 60 * 1000);

							// System.out.println("Diferença em segundos:" + diferencaSeg);

							// pegar medições com intervalos de pelo menos de 30 seg
							if (diferencaSeg > 15) {
								prosseguir = true;
								switch (registro.getLinha()) {
								case 125: {
									ultimoRegistroPorEquipamento125.put(registro.getEquipamento(), registro.getData_recebimento());
									prosseguir = true;
									break;
								}
								case 32893: {
									ultimoRegistroPorEquipamento32893.put(registro.getEquipamento(), registro.getData_recebimento());
									prosseguir = true;
									break;
								}
								case 874: {
									ultimoRegistroPorEquipamento874.put(registro.getEquipamento(), registro.getData_recebimento());
									prosseguir = true;
									break;
								}
								case 33642: {
									ultimoRegistroPorEquipamento33642.put(registro.getEquipamento(), registro.getData_recebimento());
									prosseguir = true;
									break;
								}
								
							  }						
								
							} else {
								switch (registro.getLinha()) {
								case 125: {
									qtdMedidasRedundantes125++;
									break;
								}
								case 32893: {
									qtdMedidasRedundantes32893++;
									break;
								}
								case 874: {
									qtdMedidasRedundantes874++;
									break;
								}
								case 33642: {
									qtdMedidasRedundantes33642++;
									break;
								}
																
								/*
								 * System.out.println("Data/Hora anterior: " + dataUltimoRegistro);
								 * System.out.println("Data/Hora atual: " + registro.getData_recebimento());
								 * System.out.println("Diferença é menor que 300: " + "dif: " + diferencaSeg);
								 */
								}
								// atualizando o valor do último registro do equipamento

							}

							if (prosseguir) {
								switch (registro.getLinha()) {
								case 125: {
									if (regDAO.cadastrarRegistro(registro, tabela125) == false) {
										qtdRegCadastrados125++;
									} else {
										System.out.println("Erro ao cadastrar: " + linha);
										qtdErros125++;
									}
								}
								case 32893: {
									if (regDAO.cadastrarRegistro(registro, tabela32893) == false) {
										qtdRegCadastrados32893++;
									} else {
										System.out.println("Erro ao cadastrar: " + linha);
										qtdErros32893++;
									}
								}
									break;
								case 874: {
									if (regDAO.cadastrarRegistro(registro, tabela874) == false) {
										qtdRegCadastrados874++;
									} else {
										System.out.println("Erro ao cadastrar: " + linha);
										qtdErros874++;
									}
								}
									break;
								case 33642: {
									if (regDAO.cadastrarRegistro(registro, tabela33642) == false) {
										qtdRegCadastrados33642++;
									} else {
										System.out.println("Erro ao cadastrar: " + linha);
										qtdErros33642++;
									}
								}
									break;
								default:
									System.out.println("Linha não encontrada.");
								}

							}
						}
					}
				}
				linha = bufferedReader.readLine();
				prosseguir = false;
			}

			System.out.println("------Resumo------");
			System.out.println("Qtd. de registros: " + qtdLinhasArq);
			
			System.out.println("");
			System.out.println("Rota: " + tabela125);
			System.out.println("Qtd. de registros cadastrados: " + qtdRegCadastrados125);
			System.out.println("Qtd. de erros: " + qtdErros125);
			System.out.println("Qtd. de linhas redundantes: " + qtdMedidasRedundantes125);
			
			System.out.println("");
			System.out.println("Rota: " + tabela32893);
			System.out.println("Qtd. de registros cadastrados: " + qtdRegCadastrados32893);
			System.out.println("Qtd. de erros: " + qtdErros32893);
			System.out.println("Qtd. de linhas redundantes: " + qtdMedidasRedundantes32893);
			
					
			System.out.println("");
			System.out.println("Rota: " + tabela33642);
			System.out.println("Qtd. de registros cadastrados: " + qtdRegCadastrados33642);
			System.out.println("Qtd. de erros: " + qtdErros33642);
			System.out.println("Qtd. de linhas redundantes: " + qtdMedidasRedundantes33642);
			
			
			System.out.println("");
			System.out.println("Rota: " + tabela874);
			System.out.println("Qtd. de registros cadastrados: " + qtdRegCadastrados874);
			System.out.println("Qtd. de erros: " + qtdErros874);
			System.out.println("Qtd. de linhas redundantes: " + qtdMedidasRedundantes874);
			
												
			/*// últimos registros hash map
			System.out.println("últimos registros " + tabela125);
			for (String key : ultimoRegistroPorEquipamento125.keySet()) {
				Date value = ultimoRegistroPorEquipamento125.get(key);
				System.out.println(key + " = " + value);
			}*/
			// }

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					System.out.println("ERRO AO REALIZAR IMPORTACAO: " + e.getMessage());

				}
			}
		}

	}
}


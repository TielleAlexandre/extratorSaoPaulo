package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import dao.RegistroViagemDAO;
import model.Registro;
import model.RegistroViagem;
import service.RegistroService;

public class identificarViagensImportandoPorArq {

	static String latitudePartida = "-23.465048";
	static String longitudePartida = "-46.690897";

	static String latitudeChegada = "-23.474997";
	static String longitudeChegada = "-46.670197";
	
	static double limite = 0.02; //20 metros
	static String tabelaDestino = "rota874_viagem";

	static String caracterSplit = ",";
	static RegistroService regService = new RegistroService();

	public static void main(String[] args) {

		String arquivoCSV = "/home/tielle/Imagens/AmostraAnalisada/SP/rota874.csv";
		BufferedReader bufferedReader = null;
		String linha1;
		int qtdLinhasArq = 0;
		int qtdRegCadastrados = 0;
		
		try {

			bufferedReader = new BufferedReader(new FileReader(arquivoCSV));

			// Descarta a primeira linha do cabeçalho;
			linha1 = bufferedReader.readLine();
			linha1 = bufferedReader.readLine();

			String pontoPartida = null;
			String pontoChegada = null;
			while (linha1 != null && linha1.length() > 0) {
				qtdLinhasArq++;

				if (pontoPartida == null) {
					if (verificarDistanciaDoPontoPartida(linha1) <= limite) {
						pontoPartida = linha1;
					}
				} else {
					if (pontoChegada == null) {
						if (verificarDistanciaDoPontoChegada(linha1) <= limite) {
							pontoChegada = linha1;
						}
					}
				}

				if (pontoPartida != null && pontoChegada != null) {
					cadastrarViagem(pontoPartida, pontoChegada);
					qtdRegCadastrados++;
					pontoPartida = null;
					pontoChegada = null;
				}

				linha1 = bufferedReader.readLine();
			}

			System.out.println("------Resumo------");
			System.out.println("Qtd. de registros: " + qtdLinhasArq);
			System.out.println("Qtd. de registros cadastrados: " + qtdRegCadastrados);
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

	public static double verificarDistanciaDoPontoPartida(String linha) {
		String[] arrayLinha = linha.split(caracterSplit);
		String latitude1 = arrayLinha[4];
		String longitude1 = arrayLinha[5];
		double distancia = regService.formulaHaversine(latitudePartida, longitudePartida, latitude1, longitude1);
		return distancia;

	}

	public static double verificarDistanciaDoPontoChegada(String linha) {
		String[] arrayLinha = linha.split(caracterSplit);
		String latitude1 = arrayLinha[4];
		String longitude1 = arrayLinha[5];
		double distancia = regService.formulaHaversine(latitudeChegada, longitudeChegada, latitude1, longitude1);
		return distancia;

	}

	public static boolean cadastrarViagem(String pontoPartida, String pontoChegada) throws Exception {
		Registro registroPartida = montarRegistro(pontoPartida);
		Registro registroChegada = montarRegistro(pontoChegada);

		RegistroViagem rv = new RegistroViagem();
		rv.setDataPartida(registroPartida.getData_recebimento());
		rv.setDataChegada(registroChegada.getData_recebimento());
		rv.setLinha(registroChegada.getLinha());
		long tempoViagem = diferencaDatas(registroChegada.getData_recebimento(), registroPartida.getData_recebimento());
		rv.setTempoViagem(tempoViagem);
		rv.setEquipamento(registroPartida.getEquipamento());
		System.out.println("Cadastrar viagem: Dt Partida:"+ rv.getDataPartida()+" TV: "+ rv.getTempoViagem());
		RegistroViagemDAO rvDAO = new RegistroViagemDAO();
		return rvDAO.cadastrarRegistro(rv, tabelaDestino);

	}

	public static Registro montarRegistro(String linha) throws ParseException {
		String[] arrayLinha = linha.split(caracterSplit);
		Registro registro = new Registro();
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data2 = arrayLinha[1].substring(1, arrayLinha[1].length() - 1);
		Date dataRecebimento = format.parse(data2);
		registro.setData_recebimento(dataRecebimento);

		String data1 = arrayLinha[2].substring(1, arrayLinha[2].length() - 1);
		Date dataCriacao = format.parse(data1);
		registro.setData_recebimento(dataCriacao);
		registro.setData_criacao(dataCriacao);

		registro.setLinha(Integer.parseInt(arrayLinha[3]));
		registro.setLatitude(arrayLinha[4]);
		registro.setLongitude(arrayLinha[5]);
		registro.setEquipamento(arrayLinha[6]);
		registro.setEstado_viagem(Integer.parseInt(arrayLinha[7]));
		registro.setPonto_interesse(Integer.parseInt(arrayLinha[8]));

		return registro;

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

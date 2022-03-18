package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.RegistroDAO;
import model.Registro;

public class importarDados {

	// convertrr timestamp
	// https://www.timestampconvert.com/?go1=true&m=08&d=01&y=2019&hours=04&min=00&sec=00&Submit=++++++Convert+to+timestamp+++++&offset=3//

	// Havernise e Raio de Coordenadas
	// https://pt.stackoverflow.com/questions/55669/identificar-se-conjunto-de-coordenadas-est%C3%A1-dentro-de-um-raio-em-android/55710

	public static void main(String[] args) {

		// String arquivoCSV = "/home/tielle/amostra1";
		String arquivoCSV = "/home/tielle/Imagens/AmostraAnalisada/SP/x13";
		String tabela ="rota33642";
		BufferedReader bufferedReader = null;
		String linha;
		String caracterSplit = ",";
		int qtdLinhasArq = 0;
		int qtdRegCadastrados = 0;
		int qtdLinhasInconsistentes = 0;
		int qtdErros = 0;

		try {

			// Métodos que retornam o número de linhas nos arquivos txt.

			/*
			 * File arquivoLeitura = new File(arquivoCSV); LineNumberReader linhaLeitura =
			 * new LineNumberReader(new FileReader(arquivoLeitura));
			 * linhaLeitura.skip(arquivoLeitura.length()); int qtdLinha =
			 * linhaLeitura.getLineNumber(); System.out.println(qtdLinha);
			 */

			/*
			 * LineNumberReader lnr = new LineNumberReader(new FileReader(arquivoCSV));
			 * lnr.skip(Long.MAX_VALUE); int retorno = lnr.getLineNumber();
			 * System.out.println("Linha"+ retorno);
			 */

			// --------------------------------///------------------------------------------------

			bufferedReader = new BufferedReader(new FileReader(arquivoCSV));

			// Descarta a primeira linha do cabeçalho;
			linha = bufferedReader.readLine();
			linha = bufferedReader.readLine();

			RegistroDAO regDAO = new RegistroDAO();

			while (linha != null && linha.length() > 0) {
				qtdLinhasArq++;
				String[] arrayLinha = linha.split(caracterSplit);

				int tam = arrayLinha.length;
				if (tam == 8) {
					if (arrayLinha[2].equals("874")) {
						System.out.println("Data_Recebimento: " + arrayLinha[0]);
						System.out.println("Data_Criação: " + arrayLinha[1]);
						System.out.println("Linha: " + arrayLinha[2]);
						System.out.println("Latitude: " + arrayLinha[3]);
						System.out.println("Longitude: " + arrayLinha[4]);
						System.out.println("Equipamento: " + arrayLinha[5]);
						System.out.println("Estado: " + arrayLinha[6]);
						System.out.println("Ponto Interesse: " + arrayLinha[7]);

						Registro registro = new Registro();
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						Date dataRecebimento = format.parse(arrayLinha[0]);
						registro.setData_recebimento(dataRecebimento);

						Date dataCriacao = format.parse(arrayLinha[1]);
						registro.setData_recebimento(dataCriacao);
						registro.setData_criacao(dataCriacao);

						registro.setLinha(Integer.parseInt(arrayLinha[2]));
						registro.setLatitude(arrayLinha[3]);
						registro.setLongitude(arrayLinha[4]);
						registro.setEquipamento(arrayLinha[5]);
						registro.setEstado_viagem(Integer.parseInt(arrayLinha[6]));
						registro.setPonto_interesse(Integer.parseInt(arrayLinha[7]));

						if (regDAO.cadastrarRegistro(registro, tabela) == false) {
							qtdRegCadastrados++;
						} else {
							System.out.println("Erro ao cadastrar: " + linha);
							qtdErros++;
						}
					}
				}else {
					qtdLinhasInconsistentes++;
					System.out.println("Linha:" + qtdLinhasArq +" inconsistente: "+linha);				
				}

				linha = bufferedReader.readLine();
			}

			System.out.println("------Resumo------");
			System.out.println("Qtd. de registros: " + qtdLinhasArq);
			System.out.println("Qtd. de registros cadastrados: " + qtdRegCadastrados);
			System.out.println("Qtd. de erros: " + qtdErros);
			System.out.println("Qtd. de linhas inconsistentes: " + qtdLinhasInconsistentes);

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

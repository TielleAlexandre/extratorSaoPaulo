package main;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;



import dao.RegistroViagemDAO;

import model.RegistroViagem;

public class construirDiasUteisFeriados {
	
	/*TIMESTAMP [(fsp)]: um dado desse tipo é armazenado como a quantidade 
	 * de segundos que passou desde uma data de referência (no caso do MySQL 
	 * é 1970–01–01 00:00:00 UTC).*/
	

	public static void main(String[] args) throws ParseException {

		 String tabela = "rota33642_viagem_setembro";
		 int direcao=2;
		 
		try {

			RegistroViagemDAO rgDAO = new RegistroViagemDAO();
			List<String> listaEquipamentos = new ArrayList<String>();
			listaEquipamentos = rgDAO.listarEquipamentos(tabela);

			for (String equipamento : listaEquipamentos) {
				List<RegistroViagem> listaViagens = rgDAO.listarRegistrosViagem(equipamento, tabela);

				for (RegistroViagem rv : listaViagens) {
					// dia da semana
					String dia_semana =pegarDiaSemana(String.valueOf(rv.getDataPartida()));
					System.out.println("Data: "+ rv.getDataPartida() + " Dia da semana: " + dia_semana);
					rv.setDia_semana(dia_semana);
					 
					//timestamp do data/horário do ponto de partida
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dataPartida = format.parse(rv.getPartidaTimeStamp());
					long timestamp = dataPartida.getTime() / 1000L;
					rv.setTimeStamp(timestamp);
		    		System.out.println("Data Partida: "+rv.getPartidaTimeStamp()+ " Timestamp: "+timestamp);
					
		    		//timestamp do data/horário do ponto de chegada
					DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dataChegada = format2.parse(rv.getChegadaTimeStamp());
					long timestamp2 = dataChegada.getTime() / 1000L;
					rv.setTimeStamp2(timestamp2);
		    		System.out.println("Data Chegada: "+rv.getChegadaTimeStamp()+ " Timestamp: "+timestamp2);
							    			    		
					// dias do ano
					rv.setData_dias(qtdDiasAno(rv.getDataPartida()));
					System.out.println("Data: "+ rv.getDataPartida()+" DAY_OF_YEAR: " + rv.getData_dias());
		    		
					// de acordo com a rota
					rv.setDirecao(direcao);
		    		
					// não teve feriados em agosto/2019
					rv.setTipo_dia(1);
					
					//turno do dia
					//rv.setTurno_dia(0);
					
					
					rv.setHora_segundos(encontrarHora(rv.getPartidaTimeStamp()));					
		    		rgDAO.atualizarRegistro(rv,tabela);
					
				}

			}
		} catch (Exception e) {
			System.out.println("Erro ao construir atributos: "+e);
		}

	}
	
	
	public static Integer encontrarHoraEmSegundos(String data) {
		
		
		String [] arrayData = data.split(" ");
		String [] arrayHora =arrayData[1].split(":");
		
        Integer hora = Integer.valueOf(arrayHora[0])*3600;
        Integer minuto = Integer.valueOf(arrayHora[1])*60;
        Integer total = hora + minuto + Integer.valueOf(arrayHora[2]);
        
        System.out.println("HORA: "+ arrayData[1] + " - "+total);
        
        return total;
		
		
	}
	 
    public static Integer encontrarHora(String data) {
				
		String [] arrayData = data.split(" ");
		String [] arrayHora =arrayData[1].split(":");
		
        Integer hora = Integer.valueOf(arrayHora[0]); 
        System.out.println("HORA: "+ arrayData[1] + " - "+hora);
        
        return hora;		
		
	}

	public static String pegarDiaSemana(String date) { // ex 01/08/2019
		String dayWeek = "---";
		GregorianCalendar gc = new GregorianCalendar();
		try {
			gc.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			switch (gc.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				dayWeek = "7";
				break;
			case Calendar.MONDAY:
				dayWeek = "1";
				break;
			case Calendar.TUESDAY:
				dayWeek = "2";
				break;
			case Calendar.WEDNESDAY:
				dayWeek = "3";
				break;
			case Calendar.THURSDAY:
				dayWeek = "4";
				break;
			case Calendar.FRIDAY:
				dayWeek = "5";
				break;
			case Calendar.SATURDAY:
				dayWeek = "6";

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayWeek;
	}

	public static Timestamp converterTimestamp(String data) {
		return Timestamp.valueOf(data);
	}
	
	public static int qtdDiasAno(Date dataViagem) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(dataViagem);
		int dia = calendar.get(GregorianCalendar.DAY_OF_YEAR);
		return dia;
	}
	
}

package envasadoUnificado2024;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Fecha {


	public static int parserStringToInt(String bufferString) {

		int bufferInt = Integer.parseInt(bufferString);

		return bufferInt;
	}


	public static String calcularLote() {
		LocalDate fechaHoy = LocalDate.now();
		int diaDelAnio = fechaHoy.getDayOfYear();   // int con el valor del dia
		String lote = Integer.toString(diaDelAnio); // parsea lote para imprimirlo por label

		System.out.println("lote: "+lote);

		return lote;
	}


	public static String calcularVidaUtil(int vidaUtil) {

		//String fechaHoyFormateada = fechaHoy.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		LocalDate fechaVencimiento = LocalDate.now().plusMonths(vidaUtil);
		//String fechaVencFormateada = fechaVencimiento.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedLocalDate = fechaVencimiento.format(formatter);

		return formattedLocalDate;
	}

	public static String calcularVencimiento(String fieldVencimiento) {
		int vidaUtil = parserStringToInt(fieldVencimiento);
		String vencimiento = calcularVidaUtil(vidaUtil);

		return vencimiento;
	}

	public static String calcularVencimiento(int vidaUtil) {
		String vencimiento = calcularVidaUtil(vidaUtil);

		return vencimiento;
	}

	public static String fechaIngreso() {

		Date diaHoy = new Date();
//		DateFormat horaFormatoHMS = new SimpleDateFormat("HH:mm:ss");
//		String horaMinSeg = horaFormatoHMS.format(diaHoy);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String diaMesAnio = formatter.format(diaHoy);

		DateFormat horaFormatoHM = new SimpleDateFormat("HH:mm");
		String horaMinutos = horaFormatoHM.format(diaHoy);

		String fechaIngreso = diaMesAnio +" "+ horaMinutos;

		return fechaIngreso;
	}

	public static String formatterCalendarDate(Date fecha) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String formattedLocalDate = formatter.format(fecha);
		return formattedLocalDate;
	}
}

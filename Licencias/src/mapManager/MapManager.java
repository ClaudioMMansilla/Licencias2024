package mapManager;

import java.util.HashMap;
import java.util.Map;

import modelos.GuiaTransporte;

public class MapManager {

	/**
	 * get map key is contains that value
	 * @return int wich means the estadoHoja´s database ID
	 */
	public static Integer getKeyByValue(HashMap<Integer, String> map, String value) {
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey(); // Devuelve la clave (key) cuando se encuentra el valor (value)
			}
		}
		return null; // Valor no encontrado en el mapa
	}

	public static Integer returnDetailEstadoHojaSelectedWhereId(HashMap<Integer, String> map, String itemSelected) {
		if (map.containsValue(itemSelected)) {
			return mapManager.MapManager.getKeyByValue(map, itemSelected); // Llama a la función para obtener la clave
		}
		return null; // Valor no encontrado en el mapa
	}

	public static Integer getGuiaByIdcliente(HashMap<Integer, Integer> map, int keyParam) {
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (map.containsKey(keyParam)) {
				return entry.getValue(); 
			}
		}
		return null; 
	}

	public static HashMap<Integer, Integer> putGuiaClienteIntoMap(HashMap<Integer, Integer> map, int keyParam, int valueParam) {
	    if (!map.containsKey(keyParam)) {
	        map.put(keyParam, valueParam);
	    }
	    return map;
	}
	
	
	
	
}

package server.transformer;

import java.util.List;

import json.JSONParser;
import spark.ResponseTransformer;

public class ConsumosToJsonTransformer implements ResponseTransformer {

	@SuppressWarnings("unchecked")
	@Override
	public String render(Object clientesConConsumo) {
		JSONParser<Object> parser = new JSONParser<Object>();	
		return parser.listToJson((List<Object>) clientesConConsumo); 
	}

}

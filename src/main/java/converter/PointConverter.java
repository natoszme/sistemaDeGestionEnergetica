package converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.uqbar.geodds.Point;

@Converter
public class PointConverter implements AttributeConverter<Point, String> {

	private static final String SEPARATOR = ",";

	@Override
	public String convertToDatabaseColumn(Point point) {
		StringBuilder sb = new StringBuilder();
		sb.append(point.latitude()).append(SEPARATOR).append(point.longitude());
		return sb.toString();
	}

	@Override
	public Point convertToEntityAttribute(String stringPoint) {
		String[] ubicacion = stringPoint.split(SEPARATOR);
		return new Point(Double.parseDouble(ubicacion[0]), Double.parseDouble(ubicacion[1]));
	}
}
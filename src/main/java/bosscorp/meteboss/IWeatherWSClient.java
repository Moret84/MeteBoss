package bosscorp.meteboss;

import java.util.List;

public interface IWeatherWSClient
{
	List<String> refresh(String city, String country);
}

package bosscorp.meteboss;

import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.lang.Exception;
import android.util.Log;

public class WSXGlobalWeatherClient implements IWeatherWSClient
{
	private static String WSURL = "http://www.webservicex.net/globalweather.asmx/GetWeather";
	private static String ENCODING = "UTF-8";

	public WSXGlobalWeatherClient()
	{
	}

	public List<String> refresh(String city, String country)
	{
		XMLResponseHandler xmlResponseHandler = new XMLResponseHandler();
		List<String> results = new ArrayList<String>();
		try
		{
			String query = String.format("CityName=%s&CountryName=%s",
					URLEncoder.encode(city, ENCODING),
					URLEncoder.encode(country, ENCODING));

			URLConnection connection = new URL(WSURL + "?" + query).openConnection();
			connection.setRequestProperty("Accept-Charset", ENCODING);
			InputStream response = connection.getInputStream();

			return xmlResponseHandler.handleResponse(response, ENCODING);

		}
		catch(Exception e)
		{
			if(e.getMessage() != null)
				Log.e(this.getClass().getSimpleName(), e.getMessage());
		}
		return null;
	}
}

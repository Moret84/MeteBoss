package bosscorp.meteboss;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class CityActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_view);

		City city = (City) getIntent().getSerializableExtra(CityListActivity.CITY);

		TextView text = (TextView) findViewById(R.id.cityValue);
		text.setText(city.getName());

		text = (TextView) findViewById(R.id.countryValue);
		text.setText(city.getCountry());

		text = (TextView) findViewById(R.id.windValue);
		text.setText(city.getWindSpeed() + " km/h (dir. " + city.getWindDirection() + ")");

		text = (TextView) findViewById(R.id.pressureValue);
		text.setText(city.getPressure() + " hPa");

		text = (TextView) findViewById(R.id.temperatureValue);
		text.setText(city.getTemperature() + " °C");

		text = (TextView) findViewById(R.id.lastUpdateValue);
		text.setText(city.getLastUpdate());
	}
}

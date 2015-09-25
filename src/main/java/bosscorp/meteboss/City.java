package bosscorp.meteboss;

import java.util.Date;

public class City
{
	private String mName;
	private String mCountry;
	private Date mLastFetch;
	private double mWindSpeed;
	private String mWindDirection;
	private double mPressure;
	private double mTemperature;

	public City(String name, String country)
	{
		setName(name);
		setCountry(country);
	}

	public void setName(String name)
	{
		mName = name;
	}

	public void setCountry(String country)
	{
		mCountry = country;
	}

	public void setLastFetch(Date date)
	{
		mLastFetch = date;
	}

	public void setWindSpeed(double windSpeed)
	{
		mWindSpeed = windSpeed;
	}

	public void setWindDirection(String windDirection)
	{
		mWindDirection = windDirection;
	}

	public void setPressure(double pressure)
	{
		mPressure = pressure;
	}

	public void setTemperature(double temperature)
	{
		mTemperature = temperature;
	}

	public String getName()
	{
		return mName;
	}
	
	public String getCountry()
	{
		return mCountry;
	}

	public String toString()
	{
		return getName() + ", " + getCountry();
	}

}

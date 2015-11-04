package bosscorp.meteboss;

import android.database.Cursor;
import java.util.Date;
import java.io.Serializable;

public class City implements Serializable
{
	private String mName;
	private String mCountry;
	private String mLastFetch;
	private String mWind;
	private String mPressure;
	private String mTemperature;

	public City(Cursor c)
	{
		setName(c.getString(c.getColumnIndex(HelperQuiPese.NAME)));
		setCountry(c.getString(c.getColumnIndex(HelperQuiPese.COUNTRY)));
		setTemperature(c.getString(c.getColumnIndex(HelperQuiPese.TEMPERATURE)));
		setWind(c.getString(c.getColumnIndex(HelperQuiPese.WIND)));
		setPressure(c.getString(c.getColumnIndex(HelperQuiPese.PRESSURE)));
		setLastFetch(c.getString(c.getColumnIndex(HelperQuiPese.LAST_FETCH)));
	}

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

	public void setLastFetch(String date)
	{
		mLastFetch = date;
	}

	public void setWind(String wind)
	{
		mWind = wind;
	}

	public void setPressure(String pressure)
	{
		mPressure = pressure;
	}

	public void setTemperature(String temperature)
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

	public String getWind()
	{
		return mWind;
	}

	public String getPressure()
	{
		return mPressure;
	}

	public String getTemperature()
	{
		return mTemperature;
	}

	public String getLastUpdate()
	{
		return mLastFetch;
	}

	public String toString()
	{
		return getName() + ", " + getCountry();
	}
}

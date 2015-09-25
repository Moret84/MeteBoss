package bosscorp.meteboss;

import android.app.ListActivity;
import java.util.ArrayList;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class CityListActivity extends ListActivity
{
	private ArrayList<City> mCityList;
	private ListView mListView;
	private ArrayAdapter<City> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		mCityList = new ArrayList<City>();
		mCityList.add(new City("Michelville", "France"));
		mCityList.add(new City("Sardouland", "France"));

		mListView = (ListView) findViewById(android.R.id.list);
		mAdapter = new ArrayAdapter<City>(this, R.layout.city_list);
		mListView.setAdapter(mAdapter);
		mAdapter.addAll(mCityList);
    }
}

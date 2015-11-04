package bosscorp.meteboss;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.lang.InterruptedException;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class CityListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
	public final static String CITY = "bosscorp.meteboss.city";

	private void removeCity(int id)
	{
		Cursor cursor = ((CursorAdapter) getListView().getAdapter()).getCursor();
		cursor.moveToPosition(id);

		String city = cursor.getString(cursor.getColumnIndex(HelperQuiPese.NAME));

		getContentResolver().delete
			(
				ProviderQuiPese.buildCityUri(city, cursor.getString(cursor.getColumnIndex(HelperQuiPese.COUNTRY))),
				null, null
			);

		Toast.makeText(this, city + getResources().getString(R.string.removeToast), Toast.LENGTH_SHORT).show();
	}

	private void openAdd()
	{
		Intent intent = new Intent(this, AddCityActivity.class);
		startActivity(intent);
	}

	private void refreshAll()
	{
		Cursor c = getContentResolver().query(ProviderQuiPese.getAllCitiesUri(), null, null, null, null);

		while (c.moveToNext())
		{
			Intent intent = new Intent(this, GetData.class);
			String city = c.getString(c.getColumnIndex(HelperQuiPese.NAME));
			String country = c.getString(c.getColumnIndex(HelperQuiPese.COUNTRY));
			intent.putExtra("URI", ProviderQuiPese.buildCityUri(city, country));
			startService(intent);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		return new CursorLoader(this, ProviderQuiPese.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		((CursorAdapter) getListView().getAdapter()).swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		((CursorAdapter) getListView().getAdapter()).swapCursor(null);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getLoaderManager().initLoader(0, null, this);

		setListAdapter(
				new SimpleCursorAdapter(this,
					android.R.layout.simple_list_item_2,
					null,
					new String[]{HelperQuiPese.NAME, HelperQuiPese.COUNTRY},
					new int[]{android.R.id.text1, android.R.id.text2},
					0)
				);

		registerForContextMenu(getListView());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, CityActivity.class);
		Cursor cursor = ((CursorAdapter) l.getAdapter()).getCursor();
		cursor.moveToPosition(position);

		intent.putExtra
		(
				CITY,
				ProviderQuiPese.buildCityUri
				(
					cursor.getString(cursor.getColumnIndex(HelperQuiPese.NAME)),
					cursor.getString(cursor.getColumnIndex(HelperQuiPese.COUNTRY))
				)
		);

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.addCity:
				openAdd();
				return true;
			case R.id.refresh:
				refreshAll();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Remove");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if(item.getTitle() == "Remove")
			removeCity(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
		else
			return false;
		return true;
	}
}

package br.com.mcampos.android.tracker;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity
{
	private ListView listView;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		initList( );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater( ).inflate( R.menu.main, menu );
		return true;
	}

	private void initList( )
	{
		Cursor c = managedQuery( Contacts.CONTENT_URI, null, null, null, Contacts.DISPLAY_NAME + " DESC " );
		String[ ] cols = new String[ ] { Contacts.DISPLAY_NAME };
		int[ ] views = new int[ ] { android.R.id.text1 };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
				android.R.layout.simple_list_item_1,
				c, cols, views );
		getListview( ).setAdapter( adapter );
	}

	private ListView getListview( )
	{
		if ( listView == null ) {
			listView = (ListView) findViewById( R.id.listview );
		}
		return listView;
	}

}

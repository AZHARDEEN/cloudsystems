package br.com.mcampos.android.tracker.providers;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import br.com.mcampos.android.tracker.entities.ActionProviderMetaData;
import br.com.mcampos.android.tracker.entities.ActionProviderMetaData.ActionTableMetaData;

public class ActionProvider extends ContentProvider
{
	// Logging helper tag. No significance to providers.
	private static final String TAG = "ActionProvider";

	private DatabaseHelper mOpenHelper;

	// Setup projection Map
	// Projection maps are similar to "as" (column alias) construct
	// in an sql statement where by you can rename the
	// columns.
	private static HashMap<String, String> sActionssProjectionMap;

	static
	{
		sActionssProjectionMap = new HashMap<String, String>( );
		sActionssProjectionMap.put( ActionTableMetaData._ID, ActionTableMetaData._ID );
		// name, isbn, author
		sActionssProjectionMap.put( ActionTableMetaData.ACTION_NAME, ActionTableMetaData.ACTION_NAME );
		sActionssProjectionMap.put( ActionTableMetaData.ACTION_DESCRIPTION, ActionTableMetaData.ACTION_DESCRIPTION );
		// created date, modified date
	}

	private static final UriMatcher sUriMatcher;
	private static final int INCOMING_ACTION_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMING_SINGLE_ACTION_URI_INDICATOR = 2;
	static {
		sUriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
		sUriMatcher.addURI( ActionProviderMetaData.AUTHORITY, "actions", INCOMING_ACTION_COLLECTION_URI_INDICATOR );
		sUriMatcher.addURI( ActionProviderMetaData.AUTHORITY, "actions/#", INCOMING_SINGLE_ACTION_URI_INDICATOR );
	}

	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper( Context context )
		{
			super( context, ActionProviderMetaData.DATABASE_NAME, null, ActionProviderMetaData.DATABASE_VERSION );
		}

		@Override
		public void onCreate( SQLiteDatabase db )
		{
			Log.d( TAG, "DatabaseHelper inner oncreate called" );
			db.execSQL( "CREATE TABLE " + ActionTableMetaData.TABLE_NAME + " ("
					+ ActionTableMetaData._ID + " INTEGER PRIMARY KEY,"
					+ ActionTableMetaData.ACTION_NAME + " TEXT,"
					+ ActionTableMetaData.ACTION_DESCRIPTION + " TEXT"
					+ ");" );
		}

		@Override
		public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
		{
			Log.d( TAG, "DatabaseHelper inner onupgrade called" );
			Log.w( TAG, "DatabaseHelper Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data" );
			db.execSQL( "DROP TABLE IF EXISTS " + ActionTableMetaData.TABLE_NAME );
			onCreate( db );
		}
	}

	@Override
	public int delete( Uri uri, String where, String[ ] whereArgs )
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase( );
		int count;
		switch ( sUriMatcher.match( uri ) ) {
		case INCOMING_ACTION_COLLECTION_URI_INDICATOR:
			count = db.delete( ActionTableMetaData.TABLE_NAME, where, whereArgs );
			break;
		case INCOMING_SINGLE_ACTION_URI_INDICATOR:
			String rowId = uri.getPathSegments( ).get( 1 );
			count = db.delete( ActionTableMetaData.TABLE_NAME, ActionTableMetaData._ID + "=" + rowId
					+ ( !TextUtils.isEmpty( where ) ? " AND (" + where + ')' : "" ),
					whereArgs );
			break;
		default:
			throw new IllegalArgumentException( "Unknown URI " + uri );
		}
		getContext( ).getContentResolver( ).notifyChange( uri, null );
		return count;
	}

	@Override
	public String getType( Uri uri )
	{
		switch ( sUriMatcher.match( uri ) ) {
		case INCOMING_ACTION_COLLECTION_URI_INDICATOR:
			return ActionTableMetaData.CONTENT_TYPE;
		case INCOMING_SINGLE_ACTION_URI_INDICATOR:
			return ActionTableMetaData.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException( "Unknown URI " + uri );
		}
	}

	@Override
	public Uri insert( Uri uri, ContentValues initialValues )
	{
		if ( sUriMatcher.match( uri ) != INCOMING_ACTION_COLLECTION_URI_INDICATOR ) {
			throw new IllegalArgumentException( "Unknown URI " + uri );
		}
		ContentValues values;
		if ( initialValues != null ) {
			values = new ContentValues( initialValues );
		}
		else {
			values = new ContentValues( );
		}
		// Make sure that the fields are all set
		if ( values.containsKey( ActionTableMetaData.ACTION_NAME ) == false )
		{
			throw new SQLException( "Failed to insert row because Book Name is needed " + uri );
		}
		SQLiteDatabase db = mOpenHelper.getWritableDatabase( );
		long rowId = db.insert( ActionTableMetaData.TABLE_NAME, ActionTableMetaData.ACTION_NAME, values );
		if ( rowId > 0 ) {
			Uri insertedBookUri = ContentUris.withAppendedId( ActionTableMetaData.CONTENT_URI, rowId );
			getContext( ).getContentResolver( ).notifyChange( insertedBookUri, null );
			return insertedBookUri;
		}
		throw new SQLException( "Failed to insert row into " + uri );
	}

	@Override
	public boolean onCreate( )
	{
		Log.d( TAG, "main onCreate called" );
		mOpenHelper = new DatabaseHelper( getContext( ) );
		return true;
	}

	@Override
	public Cursor query( Uri uri, String[ ] projection, String selection, String[ ] selectionArgs, String sortOrder )
	{
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder( );
		switch ( sUriMatcher.match( uri ) ) {
		case INCOMING_ACTION_COLLECTION_URI_INDICATOR:
			qb.setTables( ActionTableMetaData.TABLE_NAME );
			qb.setProjectionMap( sActionssProjectionMap );
			break;
		case INCOMING_SINGLE_ACTION_URI_INDICATOR:
			qb.setTables( ActionTableMetaData.TABLE_NAME );
			qb.setProjectionMap( sActionssProjectionMap );
			qb.appendWhere( ActionTableMetaData._ID + "=" + uri.getPathSegments( ).get( 1 ) );
			break;
		default:
			throw new IllegalArgumentException( "Unknown URI " + uri );
		}
		// If no sort order is specified use the default
		String orderBy;
		if ( TextUtils.isEmpty( sortOrder ) ) {
			orderBy = ActionTableMetaData.DEFAULT_SORT_ORDER;
		}
		else {
			orderBy = sortOrder;
		}
		// Get the database and run the query
		SQLiteDatabase db = mOpenHelper.getReadableDatabase( );
		Cursor c = qb.query( db, projection, selection, selectionArgs, null, null, orderBy );
		// example of getting a count
		// int i = c.getCount( );
		// Tell the cursor what uri to watch,
		// so it knows when its source data changes
		c.setNotificationUri( getContext( ).getContentResolver( ), uri );
		return c;
	}

	@Override
	public int update( Uri uri, ContentValues values, String where, String[ ] whereArgs )
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase( );

		int count;
		switch ( sUriMatcher.match( uri ) ) {
		case INCOMING_ACTION_COLLECTION_URI_INDICATOR:
			count = db.update( ActionTableMetaData.TABLE_NAME, values, where, whereArgs );
			break;
		case INCOMING_SINGLE_ACTION_URI_INDICATOR:
			String rowId = uri.getPathSegments( ).get( 1 );
			count = db.update( ActionTableMetaData.TABLE_NAME, values, ActionTableMetaData._ID + "=" + rowId
					+ ( !TextUtils.isEmpty( where ) ? " AND (" + where + ')' : "" ), whereArgs );
			break;
		default:
			throw new IllegalArgumentException( "Unknown URI " + uri );
		}
		getContext( ).getContentResolver( ).notifyChange( uri, null );
		return count;
	}
}

package br.com.mcampos.android.tracker.entities;

import android.net.Uri;
import android.provider.BaseColumns;

public class ActionProviderMetaData
{
	public static final String AUTHORITY = "br.com.mcampos.android.tracker.providers.ActionProvider";

	public static final String DATABASE_NAME = "tracker.db";
	public static final int DATABASE_VERSION = 1;
	public static final String BOOKS_TABLE_NAME = "action";

	private ActionProviderMetaData( )
	{
	};

	public static final class ActionTableMetaData implements BaseColumns
	{

		public static final String TABLE_NAME = "books";
		public static final Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY + "/actions" );
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.mcampos.action";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.mcampos.action";
		public static final String DEFAULT_SORT_ORDER = "name asc";

		/*
		 * Integer
		 */
		public static final String _ID = "_id";
		/*
		 * String
		 */
		public static final String ACTION_NAME = "name";
		/*
		 * String
		 */
		public static final String ACTION_DESCRIPTION = "description";

	}

}

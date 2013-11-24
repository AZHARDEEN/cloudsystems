package br.com.mcampos.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LuceneUtils
{
	private static final Logger LOGGER = LoggerFactory.getLogger( LuceneUtils.class );
	private static final String INDEXER_PATH = "/var/local/jboss/lucene/indexer";

	public static void createDatabase( )
	{
		LOGGER.info( "Creating Lucene Database" );
		long start = System.currentTimeMillis( );

		try {
			Directory dir = MMapDirectory.open( new File( INDEXER_PATH ) );
			IndexWriterConfig cfg = new IndexWriterConfig( Version.LUCENE_45, new StandardAnalyzer( Version.LUCENE_45 ) );
			IndexWriter writer = new IndexWriter( dir, cfg );
			writer.close( );
			long end = System.currentTimeMillis( );
			LOGGER.info( "Database created in " + ( end - start ) + " millisenconds!" );
		}
		catch ( IOException e ) {
			LOGGER.info( "Error on create lucene database", e );
		}

	}
}

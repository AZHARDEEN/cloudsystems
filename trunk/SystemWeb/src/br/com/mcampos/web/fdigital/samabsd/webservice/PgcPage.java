package br.com.mcampos.web.fdigital.samabsd.webservice;

public class PgcPage
{
	private int pgc_id_in;
	private int ppg_book_id;
	private int ppg_page_id;

	public int getPgc_id_in( )
	{
		return this.pgc_id_in;
	}

	public void setPgc_id_in( int pgc_id_in )
	{
		this.pgc_id_in = pgc_id_in;
	}

	public int getPpg_book_id( )
	{
		return this.ppg_book_id;
	}

	public void setPpg_book_id( int ppg_book_id )
	{
		this.ppg_book_id = ppg_book_id;
	}

	public int getPpg_page_id( )
	{
		return this.ppg_page_id;
	}

	public void setPpg_page_id( int ppg_page_id )
	{
		this.ppg_page_id = ppg_page_id;
	}
}

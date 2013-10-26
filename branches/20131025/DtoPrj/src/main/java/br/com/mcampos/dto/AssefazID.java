package br.com.mcampos.dto;

import java.io.Serializable;

public class AssefazID implements Serializable
{
	private static final long serialVersionUID = -155219369157958471L;

	private String id;
	private Integer sequence;

	public AssefazID( )
	{
	}

	/* Assefaz Code Examples
	 	720016430001
		740006150001
		740006150002
		740006150003
		740006150004
		740006150005
	 */
	public AssefazID( String completeCode ) throws Exception
	{
		set( completeCode );
	}

	public void set( String completeCode ) throws Exception
	{
		String seq = completeCode.substring( completeCode.length( ) - 4 );
		try {
			setSequence( Integer.parseInt( seq ) );
			setId( completeCode.substring( 0, completeCode.length( ) - 4 ) );
		}
		catch ( Exception e ) {
			setSequence( 0 );
			setId( null );
			throw new Exception( "Assefaz internal code is invalid: " + completeCode );
		}
	}

	public String getId( )
	{
		return this.id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer sequencial )
	{
		this.sequence = sequencial;
	}
}

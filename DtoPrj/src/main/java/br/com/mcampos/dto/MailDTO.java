package br.com.mcampos.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class MailDTO implements Serializable
{
	private static final long serialVersionUID = 1783999515145339356L;
	protected ArrayList<String> recipients;
	protected String subject;
	protected String body;

	public MailDTO( )
	{
		super( );
	}

	public void setRecipients( ArrayList<String> recipients )
	{
		this.recipients = recipients;
	}

	public ArrayList<String> getRecipients( )
	{
		if ( this.recipients == null ) {
			this.recipients = new ArrayList<String>( );
		}
		return this.recipients;
	}

	public void addRecipient( String recipient )
	{
		getRecipients( ).add( recipient );
	}

	public void setSubject( String subject )
	{
		this.subject = subject;
	}

	public String getSubject( )
	{
		return this.subject;
	}

	public void setBody( String body )
	{
		this.body = body;
	}

	public String getBody( )
	{
		return this.body;
	}

}

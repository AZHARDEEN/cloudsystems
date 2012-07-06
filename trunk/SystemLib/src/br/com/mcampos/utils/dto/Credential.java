package br.com.mcampos.utils.dto;

import java.io.Serializable;
import java.util.Locale;

public class Credential implements Serializable
{
	private static final long serialVersionUID = -7831902621075757599L;
	private String sessionId;
	private String remoteAddr;
	private String remoteHost;
	private Locale locale;
	private String program;

	private String identification;
	private String password;

	public void setSessionId( String sessionId )
	{
		this.sessionId = sessionId;
	}

	public String getSessionId( )
	{
		return this.sessionId;
	}

	public void setRemoteAddr( String remoteAddr )
	{
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteAddr( )
	{
		return this.remoteAddr;
	}

	public void setRemoteHost( String remoteHost )
	{
		this.remoteHost = remoteHost;
	}

	public String getRemoteHost( )
	{
		return this.remoteHost;
	}

	public void setLocale( Locale locale )
	{
		this.locale = locale;
	}

	public Locale getLocale( )
	{
		return this.locale;
	}

	public void setIdentification( String identification )
	{
		this.identification = identification;
	}

	public String getIdentification( )
	{
		return this.identification;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getPassword( )
	{
		return this.password;
	}

	public void setProgram( String program )
	{
		this.program = program;
	}

	public String getProgram( )
	{
		return this.program;
	}

}

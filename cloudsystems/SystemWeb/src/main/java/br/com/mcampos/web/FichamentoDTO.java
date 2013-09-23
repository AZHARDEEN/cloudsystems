package br.com.mcampos.web;

import java.io.Serializable;

public class FichamentoDTO implements Serializable
{
	private static final long serialVersionUID = -8233202776869437594L;
	private String tema;
	private String autor;
	private String referencia;
	private String ficha;
	private String fonte;
	private String tipo;

	public String getTema( )
	{
		return tema;
	}

	public void setTema( String tema )
	{
		this.tema = tema;
	}

	public String getAutor( )
	{
		return autor;
	}

	public void setAutor( String autor )
	{
		this.autor = autor;
	}

	public String getReferencia( )
	{
		return referencia;
	}

	public void setReferencia( String referencia )
	{
		this.referencia = referencia;
	}

	public String getFicha( )
	{
		return ficha;
	}

	public void setFicha( String ficha )
	{
		this.ficha = ficha;
	}

	public String getFonte( )
	{
		return fonte;
	}

	public void setFonte( String fonte )
	{
		this.fonte = fonte;
	}

	public String getTipo( )
	{
		return tipo;
	}

	public void setTipo( String tipo )
	{
		this.tipo = tipo;
	}
}

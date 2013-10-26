package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class CorretorDTO implements Serializable
{
	private static final long serialVersionUID = 2158782883061995963L;
	private String cpf;
	private String email;
	private String nome;
	private Boolean coordenador;
	private int Tarefa;
	private int evento;
	private int user;

	public String getCpf( )
	{
		return this.cpf;
	}

	public void setCpf( String cpf )
	{
		this.cpf = cpf;
	}

	public String getEmail( )
	{
		return this.email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public String getNome( )
	{
		return this.nome;
	}

	public void setNome( String nome )
	{
		this.nome = nome;
	}

	public Boolean getCoordenador( )
	{
		return this.coordenador;
	}

	public void setCoordenador( Boolean coordenador )
	{
		this.coordenador = coordenador;
	}

	public int getTarefa( )
	{
		return this.Tarefa;
	}

	public void setTarefa( int tarefa )
	{
		this.Tarefa = tarefa;
	}

	public int getEvento( )
	{
		return this.evento;
	}

	public void setEvento( int evento )
	{
		this.evento = evento;
	}

	public int getUser( )
	{
		return this.user;
	}

	public void setUser( int user )
	{
		this.user = user;
	}
}

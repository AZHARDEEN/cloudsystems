package br.com.mcampos.dto.inep.relatorios;

public class NotasIndividuaisCorretorDTO extends BaseSubscriptionDTO
{
	private static final long serialVersionUID = 1931159060459664209L;
	private String cpf;
	private Integer tarefa;
	private Integer nota;

	public Integer getTarefa( )
	{
		return this.tarefa;
	}

	public void setTarefa( Integer tarefa )
	{
		this.tarefa = tarefa;
	}

	public Integer getNota( )
	{
		if ( this.nota == null ) {
			this.nota = 0;
		}
		return this.nota;
	}

	public void setNota( Integer nota )
	{
		this.nota = nota;
	}

	public String getCpf( )
	{
		return this.cpf;
	}

	public void setCpf( String cpf )
	{
		this.cpf = cpf;
	}

	@Override
	public String[ ] getFields( )
	{
		String[ ] fields = new String[ 4 ];
		fields[ 0 ] = getSubscription( );
		fields[ 1 ] = getTarefa( ).toString( );
		fields[ 2 ] = getCpf( );
		fields[ 3 ] = getNota( ).toString( );
		return fields;
	}
}

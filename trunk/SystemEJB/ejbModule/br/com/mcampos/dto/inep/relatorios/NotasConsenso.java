package br.com.mcampos.dto.inep.relatorios;

public class NotasConsenso extends BaseSubscriptionDTO
{
	private static final long serialVersionUID = -3249592127173411893L;
	private Integer Tarefa;
	private Integer notaConsenso;

	public Integer getTarefa( )
	{
		return this.Tarefa;
	}

	public void setTarefa( Integer tarefa )
	{
		this.Tarefa = tarefa;
	}

	public Integer getNotaConsenso( )
	{
		if ( this.notaConsenso == null ) {
			this.notaConsenso = 0;
		}
		return this.notaConsenso;
	}

	public void setNotaConsenso( Integer notaConsenso )
	{
		this.notaConsenso = notaConsenso;
	}

	@Override
	public String[ ] getFields( )
	{
		String[ ] fields = new String[ 3 ];
		fields[ 0 ] = getSubscription( );
		fields[ 1 ] = getTarefa( ).toString( );
		fields[ 2 ] = getNotaConsenso( ).toString( );
		return fields;
	}
}

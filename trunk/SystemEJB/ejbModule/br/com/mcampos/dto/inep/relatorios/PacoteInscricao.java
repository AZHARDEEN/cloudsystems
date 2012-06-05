package br.com.mcampos.dto.inep.relatorios;

public class PacoteInscricao extends BaseSubscriptionDTO
{
	private static final long serialVersionUID = -155284478245759330L;
	private Integer pacote;

	public Integer getPacote( )
	{
		return this.pacote;
	}

	public void setPacote( Integer pacote )
	{
		this.pacote = pacote;
	}

	@Override
	public String[ ] getFields( )
	{
		String[ ] fields = new String[ 2 ];
		fields[ 0 ] = getPacote( ).toString( );
		fields[ 1 ] = getSubscription( );
		return fields;
	}
}

package br.com.mcampos.sysutils;

abstract class BaseID
{
	public static String removeMask( String id )
	{
		String aux = null;
		if ( !SysUtils.isEmpty( id ) ) {
			aux = id.replaceAll( "\\.", "" );
			aux = aux.replaceAll( "-", "" );
		}
		return aux;
	}

}

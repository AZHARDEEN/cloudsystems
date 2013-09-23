package br.com.mcampos.sysutils;

import java.io.Serializable;

public class CPF extends BaseID implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -873123898678107661L;

	private static final int[ ] PESO_CPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	private String code;

	public CPF( )
	{
		super( );
	}

	private static int calcularDigito( String str, int[ ] peso )
	{
		int soma = 0;

		for ( int indice = str.length( ) - 1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt( str.substring( indice, indice + 1 ) );
			soma += digito * peso[ peso.length - str.length( ) + indice ];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValid( String cpf )
	{
		if ( ( cpf == null ) ) {
			return false;
		}
		String aux = removeMask( cpf );

		if ( aux.length( ) != 11 ) {
			return false;
		}
		Integer digito1 = calcularDigito( aux.substring( 0, 9 ), PESO_CPF );
		Integer digito2 = calcularDigito( aux.substring( 0, 9 ) + digito1, PESO_CPF );
		return aux.equals( aux.substring( 0, 9 ) + digito1.toString( ) + digito2.toString( ) );
	}

	public static String removeMask( String cpf )
	{
		String aux = null;

		if ( SysUtils.isEmpty( cpf ) == false ) {
			aux = cpf.replaceAll( "\\.", "" );
			aux = aux.replaceAll( "-", "" );
		}
		return aux;
	}

	public String getCode( )
	{
		return code;
	}

	public void setCode( String code )
	{
		this.code = code;
	}
}

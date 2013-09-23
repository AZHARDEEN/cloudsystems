package br.com.mcampos.sysutils;

import java.io.Serializable;

public class CNPJ extends BaseID implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6635738900874417578L;
	private static final int[ ] PESO_CNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	public CNPJ( )
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

	public static boolean isValid( String cnpj )
	{
		if ( ( cnpj == null ) || ( cnpj.length( ) != 14 ) ) {
			return false;
		}

		Integer digito1 = calcularDigito( cnpj.substring( 0, 12 ), PESO_CNPJ );
		Integer digito2 = calcularDigito( cnpj.substring( 0, 12 ) + digito1, PESO_CNPJ );
		return cnpj.equals( cnpj.substring( 0, 12 ) + digito1.toString( ) + digito2.toString( ) );
	}
}

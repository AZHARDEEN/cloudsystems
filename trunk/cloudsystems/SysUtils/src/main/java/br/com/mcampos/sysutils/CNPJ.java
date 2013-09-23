package br.com.mcampos.sysutils;

import java.io.Serializable;

public class CNPJ extends BaseID implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6635738900874417578L;
	private static final int[ ] PESO_CNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int VALID_SIZE = 14;
	private static final int MODULUS_VALUE = 11;
	private static final int DIGIT_POSITION = 12;

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
		soma = MODULUS_VALUE - soma % MODULUS_VALUE;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValid( String cnpj )
	{
		if ( SysUtils.isEmpty( cnpj ) || ( cnpj.length( ) != VALID_SIZE ) ) {
			return false;
		}

		Integer digito1 = calcularDigito( cnpj.substring( 0, DIGIT_POSITION ), PESO_CNPJ );
		Integer digito2 = calcularDigito( cnpj.substring( 0, DIGIT_POSITION ) + digito1, PESO_CNPJ );
		return cnpj.equals( cnpj.substring( 0, DIGIT_POSITION ) + digito1.toString( ) + digito2.toString( ) );
	}
}

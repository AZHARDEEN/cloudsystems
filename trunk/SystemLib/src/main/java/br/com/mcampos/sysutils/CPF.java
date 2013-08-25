package br.com.mcampos.sysutils;

import java.io.Serializable;

public class CPF implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -873123898678107661L;

	private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

	private String code;


	public CPF()
	{
		super();
	}




	private static int calcularDigito(String str, int[] peso)
	{
		int soma = 0;

		for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt(str.substring(indice,indice+1));
			soma += digito*peso[peso.length-str.length()+indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValid(String cpf)
	{
		if ( ( cpf == null ) ) {
			return false;
		}
		cpf = cpf.replaceAll( "\\.", "" );
		cpf = cpf.replaceAll( "-", "" );
		if ( cpf.length( ) != 11 ) {
			return false;
		}
		Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	public static String removeMask( String cpf )
	{
		if ( SysUtils.isEmpty( cpf ) == false ) {
			cpf = cpf.replaceAll( "\\.", "" );
			cpf = cpf.replaceAll( "-", "" );
		}
		return cpf;
	}

	public String getCode( )
	{
		return this.code;
	}

	public void setCode( String code )
	{
		this.code = code;
	}

	public String getFormattedCode( )
	{
		String formattedCode = getCode( );
		if ( SysUtils.isEmpty( formattedCode ) == false ) {
			/*
			 * TODO: How format a string
			 */
		}
		return formattedCode;
	}

}

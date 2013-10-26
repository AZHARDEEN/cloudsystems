package br.com.mcampos.ejb.core;

/**
 * Brief Esta é a interface base de todos os SessionBean no java ou seja, todas os SessionBeans devem derivar desta interface.
 */
public interface BaseSessionInterface
{
	void storeException( Exception e );
}

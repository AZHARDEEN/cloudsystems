package br.com.mcampos.ejb.core.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface Searchable
{
	public String columnName( );

	public String displayName( );

	public InputType type( ) default InputType.STRING;
}

package br.com.mcampos.ejb.core;

import javax.validation.constraints.NotNull;

import br.com.mcampos.dto.core.PrincipalDTO;

public interface PagingSessionInterface<T> extends ReadOnlySessionInterface<T>
{
	public int count(  PrincipalDTO auth );

	public int count(  PrincipalDTO auth, String filter );

	public int count(  PrincipalDTO auth, String filter, Object... params );
}

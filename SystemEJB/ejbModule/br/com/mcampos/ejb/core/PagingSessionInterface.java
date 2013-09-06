package br.com.mcampos.ejb.core;

import javax.validation.constraints.NotNull;

import br.com.mcampos.utils.dto.PrincipalDTO;

public interface PagingSessionInterface<T> extends ReadOnlySessionInterface<T>
{
	public int count( @NotNull PrincipalDTO auth );

	public int count( @NotNull PrincipalDTO auth, String filter );

	public int count( @NotNull PrincipalDTO auth, String filter, Object... params );
}

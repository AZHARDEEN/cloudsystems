package br.com.mcampos.ejb.product;

import java.security.InvalidParameterException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import br.com.mcampos.ejb.core.BaseCompanySessionBean;
import br.com.mcampos.jpa.product.Category;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

/**
 * Session Bean implementation class CategorySessionBean
 */
@Stateless( mappedName = "CategorySession", name = "CategorySession" )
@LocalBean
public class CategorySessionBean extends BaseCompanySessionBean<Category> implements CategorySession, CategorySessionLocal
{
	@Override
	protected Class<Category> getEntityClass( )
	{
		return Category.class;
	}

	@Override
	public Category add( @NotNull PrincipalDTO auth, Category newEntity )
	{
		if ( auth == null || newEntity == null ) {
			throw new InvalidParameterException( );
		}
		if ( newEntity.getId( ).getId( ) == null || newEntity.getId( ).getId( ).equals( 0 ) ) {
			Integer nextVal = getNextId( Category.getNextId, auth.getCompanyID( ) );
			newEntity.getId( ).setId( nextVal );
		}
		return super.add( auth, newEntity );
	}

}

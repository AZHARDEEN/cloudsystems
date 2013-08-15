package br.com.mcampos.ejb.gdf;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleCodedTable;
import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class CodedTableSessionBean
 */
@Stateless
@LocalBean
public class CodedTableSessionBean extends SimpleSessionBean<SimpleCodedTable<?>> implements CodedTableSession, CodedTableSessionLocal
{

	@Override
	protected Class getEntityClass( )
	{
		// TODO Auto-generated method stub
		return AdministrativeRegion.class;
	}

}

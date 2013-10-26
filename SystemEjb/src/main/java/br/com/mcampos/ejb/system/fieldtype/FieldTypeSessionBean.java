package br.com.mcampos.ejb.system.fieldtype;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.system.FieldType;

/**
 * Session Bean implementation class FieldTypeSessionBean
 */
@Stateless( name = "FieldTypeSession", mappedName = "FieldTypeSession" )
@LocalBean
public class FieldTypeSessionBean extends SimpleSessionBean<FieldType> implements FieldTypeSession, FieldTypeSessionLocal
{

	@Override
	protected Class<FieldType> getEntityClass( )
	{
		return FieldType.class;
	}

}

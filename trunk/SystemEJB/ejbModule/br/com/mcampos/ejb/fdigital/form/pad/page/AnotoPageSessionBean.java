package br.com.mcampos.ejb.fdigital.form.pad.page;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.fdigital.AnotoPageField;
import br.com.mcampos.ejb.system.fieldtype.FieldTypeSessionLocal;

/**
 * Session Bean implementation class AnotoPageSessionBean
 */
@Stateless
@LocalBean
public class AnotoPageSessionBean extends SimpleSessionBean<AnotoPage> implements AnotoPageSessionLocal
{
	@EJB
	private FieldTypeSessionLocal fieldTypeSession;

	@Override
	protected Class<AnotoPage> getEntityClass( )
	{
		// TODO Auto-generated method stub
		return AnotoPage.class;
	}

	@Override
	public AnotoPage merge( AnotoPage newEntity )
	{
		List<AnotoPageField> fields = new ArrayList<AnotoPageField>( );
		for ( AnotoPageField f : newEntity.getFields( ) ) {
			fields.add( f );
		}
		newEntity.getFields( ).clear( );
		AnotoPage page = super.merge( newEntity );
		for ( AnotoPageField f : fields ) {
			f.setType( this.fieldTypeSession.get( 1 ) );
			page.add( f );
		}
		return page;
	}

}

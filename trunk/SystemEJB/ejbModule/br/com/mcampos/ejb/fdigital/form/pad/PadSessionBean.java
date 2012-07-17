package br.com.mcampos.ejb.fdigital.form.pad;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPage;
import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPageSessionLocal;

/**
 * Session Bean implementation class PadSessionBean
 */
@Stateless
@LocalBean
public class PadSessionBean extends SimpleSessionBean<Pad> implements PadSessionLocal
{
	@EJB
	private AnotoPageSessionLocal pageSession;

	@Override
	protected Class<Pad> getEntityClass( )
	{
		return Pad.class;
	}

	@Override
	public Pad add( Pad pad, List<String> pages )
	{
		for ( String ip : pages ) {
			AnotoPage page = new AnotoPage( );
			page.getId( ).setId( ip );
			pad.add( page );
			this.pageSession.merge( page );
		}
		return pad;
	}

}

package br.com.mcampos.ejb.cloudsystem.anoto.page.session;


import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "AnotoPageSession", mappedName = "CloudSystems-EjbPrj-AnotoPageSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class AnotoPageSessionBean extends Crud<AnotoPagePK, AnotoPage> implements AnotoPageSessionLocal
{
    @Override
    public AnotoPage add( AnotoPage entity ) throws ApplicationException
    {
        entity = super.add( entity );
        return entity;
    }


    @Override
    public AnotoPage get( AnotoPagePK key ) throws ApplicationException
    {
        return super.get( AnotoPage.class, key );
    }

}

package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless( name = "MenuSession", mappedName = "CloudSystems-EjbPrj-MenuSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class MenuSessionBean extends Crud<Integer, Menu> implements MenuSessionLocal
{
    public MenuSessionBean()
    {
        super();
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Menu.class, key );
    }

    public Menu get( Integer key ) throws ApplicationException
    {
        Menu menu = get( Menu.class, key );
        if ( menu != null )
            getEntityManager().refresh( menu );
        return menu;
    }

    public List<Menu> getAll() throws ApplicationException
    {
        return ( List<Menu> )getResultList( Menu.findaAll );
    }

    public Integer getNextSequence( int parentId )
    {
        if ( SysUtils.isZero( parentId ) )
            return 0;
        Integer sequence;

        try {
            Query q;

            q = getEntityManager().createNamedQuery( "Menu.nexSequence" );
            q.setParameter( 1, parentId );
            sequence = ( Integer )q.getSingleResult();
            /*
             * In this case, we do not need increment. A native query do it by itsefl.
             */
        }
        catch ( NoResultException e ) {
            sequence = 1;
            e = null;
        }
        return sequence;
    }

    @Override
    public Menu update( Menu entity ) throws ApplicationException
    {
        return super.update( entity );
    }
}

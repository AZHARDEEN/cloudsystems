package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "MenuSession", mappedName = "CloudSystems-EjbPrj-MenuSession" )
@TransactionAttribute( TransactionAttributeType.SUPPORTS )
public class MenuSessionBean extends Crud<Integer, Menu> implements MenuSessionLocal
{
    public MenuSessionBean()
    {
        super();
    }

    @TransactionAttribute( TransactionAttributeType.MANDATORY )
    public void delete( Integer key ) throws ApplicationException
    {
        Menu menu = get( key );
        if ( menu != null )
            delete( Menu.class, key );
    }

    public Menu get( Integer key ) throws ApplicationException
    {
        Menu menu = get( Menu.class, key );
        return menu;
    }

    public List<Menu> getAll() throws ApplicationException
    {
        return ( List<Menu> )getResultList( Menu.findaAll );
    }

    public Integer getNextSequence( int parentId ) throws ApplicationException
    {
        Integer sequence;
        sequence = ( Integer )getSingleResult( Menu.nextSequence, parentId );
        if ( SysUtils.isZero( sequence ) )
            sequence = 1;
        return sequence;
    }


    public Integer getNextId() throws ApplicationException
    {
        return nextIntegerId( Menu.nextId );
    }

    @Override
    public Menu update( Menu entity ) throws ApplicationException
    {
        Menu menu = super.update( entity );
        setParent( menu );
        return menu;
    }

    @Override
    public Menu add( Menu entity ) throws ApplicationException
    {
        setParent( entity );
        Menu parent = entity.getParentMenu();
        Menu menu = getBySequence( parent, entity.getSequence() );
        if ( menu != null ) {
            menu.setSequence( getNextSequence( parent != null ? parent.getId() : 0 ) );
        }
        return super.add( entity );
    }

    private void setParent( Menu menu ) throws ApplicationException
    {
        if ( menu == null || menu.getParentMenu() == null )
            return;

        Menu parent = get( menu.getParentMenu().getId() );
        if ( parent != null ) {
            parent.addMenu( menu );
        }
    }

    private Menu getBySequence( Menu parent, Integer sequence ) throws ApplicationException
    {
        return ( Menu )getSingleResult( Menu.findSequence, parent != null ? parent.getId() : 0, sequence );
    }
}

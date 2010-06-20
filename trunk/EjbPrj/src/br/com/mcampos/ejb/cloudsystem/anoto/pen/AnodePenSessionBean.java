package br.com.mcampos.ejb.cloudsystem.anoto.pen;


import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session.AnotoPenUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AnodePenSession", mappedName = "CloudSystems-EjbPrj-AnodePenSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AnodePenSessionBean extends Crud<String, AnotoPen> implements AnodePenSessionLocal
{
    @EJB
    private AnotoPenUserSessionLocal penUserSession;

    @EJB
    private NewPersonSessionLocal personSession;

    public AnodePenSessionBean()
    {
    }

    public void delete( String key ) throws ApplicationException
    {
        super.delete( AnotoPen.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AnotoPen get( String key ) throws ApplicationException
    {
        return super.get( AnotoPen.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getAll() throws ApplicationException
    {
        return super.getAll( "Pen.findAll" );
    }

    @Override
    public AnotoPen add( AnotoPen entity, Person person ) throws ApplicationException
    {
        entity.setInsertDate( new Date() );
        entity = super.add( entity );
        person = personSession.get( person.getId() );
        if ( entity != null && person != null ) {
            AnotoPenUser user = new AnotoPenUser( entity, person );
            user = penUserSession.add( user );
        }
        return entity;
    }
}

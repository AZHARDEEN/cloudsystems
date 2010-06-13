package br.com.mcampos.ejb.cloudsystem.system.revisedstatus.session;


import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "RevisionStatusSession", mappedName = "CloudSystems-EjbPrj-RevisionStatusSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class RevisionStatusSessionBean extends Crud<Integer, RevisionStatus> implements RevisionStatusSessionLocal
{
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public RevisionStatus get( Integer key ) throws ApplicationException
    {
        return get( RevisionStatus.class, key );
    }
}

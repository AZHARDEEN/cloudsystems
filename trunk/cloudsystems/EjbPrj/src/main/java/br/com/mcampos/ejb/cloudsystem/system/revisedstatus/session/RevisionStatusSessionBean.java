package br.com.mcampos.ejb.cloudsystem.system.revisedstatus.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "RevisionStatusSession", mappedName = "RevisionStatusSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class RevisionStatusSessionBean extends Crud<Integer, RevisionStatus> implements RevisionStatusSessionLocal
{
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public RevisionStatus get( Integer key ) throws ApplicationException
    {
        return get( RevisionStatus.class, key );
    }
}

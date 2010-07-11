package br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity.PenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity.PenUserPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PenPageUserSession", mappedName = "CloudSystems-EjbPrj-PenPageUserSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class PenPageUserSessionBean extends Crud<PenUserPK, PenUser> implements PenPageUserSessionLocal
{

    public PenUser getCurrent( AnotoPenPage penPage ) throws ApplicationException
    {
        return ( PenUser )getSingleResult( PenUser.getCurrent, penPage );
    }
}

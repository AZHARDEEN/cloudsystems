package br.com.mcampos.ejb.cloudsystem.resale.dealer.session;


import br.com.mcampos.ejb.cloudsystem.resale.dealer.entity.Dealer;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.entity.DealerPK;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface DealerSessionLocal extends Serializable
{
    Dealer add( Dealer entity ) throws ApplicationException;

    Dealer update( Dealer entity ) throws ApplicationException;

    void delete( DealerPK key ) throws ApplicationException;

    void delete( Dealer entity ) throws ApplicationException;

    void delete( Resale resale, Integer sequence ) throws ApplicationException;

    Dealer get( DealerPK key ) throws ApplicationException;

    Dealer get( Resale resale, Integer sequence ) throws ApplicationException;

    /*
     * Highlander Law: There must be only one!
     */

    Dealer get( Person dealer ) throws ApplicationException;
    /*
     * END OF Highlander Law
     */

    List<Dealer> getAll( Resale resale ) throws ApplicationException;

    List<Dealer> getAll( Company owner ) throws ApplicationException;

    Dealer get( Resale resale, Person dealer ) throws ApplicationException;

}

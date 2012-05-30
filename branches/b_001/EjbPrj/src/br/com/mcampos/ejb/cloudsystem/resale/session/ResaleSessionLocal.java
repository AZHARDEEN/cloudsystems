package br.com.mcampos.ejb.cloudsystem.resale.session;


import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.resale.entity.ResalePK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ResaleSessionLocal extends Serializable
{
    Resale add( Resale entity ) throws ApplicationException;

    Resale update( Resale entity ) throws ApplicationException;

    void delete( ResalePK key ) throws ApplicationException;

    void delete( Company company, Integer sequence ) throws ApplicationException;

    Resale get( ResalePK key ) throws ApplicationException;

    Resale get( Company company, Integer sequence ) throws ApplicationException;

    List<Resale> getAll( Company company ) throws ApplicationException;

    Resale get( Company owner, Client resale ) throws ApplicationException;
}

package br.com.mcampos.ejb.cloudsystem.typing.iab.session;


import br.com.mcampos.ejb.cloudsystem.typing.iab.entity.DadosIab;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface IABSessionLocal extends Serializable
{
    DadosIab add( DadosIab entity ) throws ApplicationException;

    DadosIab update( DadosIab entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    DadosIab get( Integer key ) throws ApplicationException;

    List<DadosIab> getAll() throws ApplicationException;

    DadosIab getNextId() throws ApplicationException;

    DadosIab getPrimeiraDigitacao( String id ) throws ApplicationException;

    DadosIab getSegundaDigitacao( String id ) throws ApplicationException;

    List<DadosIab> getProblems() throws ApplicationException;

}

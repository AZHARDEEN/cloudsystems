package br.com.mcampos.ejb.cloudsystem.user.person;


import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface NewPersonSessionLocal extends Serializable
{
	Person add( Person entity ) throws ApplicationException;

	Person update( Person entity ) throws ApplicationException;

	void delete( Integer key ) throws ApplicationException;

	Person get( Integer key ) throws ApplicationException;
}

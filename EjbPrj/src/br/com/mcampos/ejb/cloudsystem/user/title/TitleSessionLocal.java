package br.com.mcampos.ejb.cloudsystem.user.title;


import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface TitleSessionLocal
{
	Title add( Title entity ) throws ApplicationException;

	Title update( Title entity ) throws ApplicationException;

	void delete( TitlePK key ) throws ApplicationException;

	Title get( TitlePK key ) throws ApplicationException;

	List<Title> getAll() throws ApplicationException;

	Integer getNextId() throws ApplicationException;

}

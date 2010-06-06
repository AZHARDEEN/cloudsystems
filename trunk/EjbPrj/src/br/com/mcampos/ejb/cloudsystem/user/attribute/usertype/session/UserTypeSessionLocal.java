package br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface UserTypeSessionLocal extends Serializable
{
	void delete( Integer key ) throws ApplicationException;

	UserType get( Integer key ) throws ApplicationException;

	List<UserType> getAll() throws ApplicationException;

	Integer nextIntegerId() throws ApplicationException;

	UserType add( UserType entity ) throws ApplicationException;

	UserType update( UserType entity ) throws ApplicationException;
}

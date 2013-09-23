package br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.session.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface UserStatusSessionLocal extends Serializable
{
	void delete( Integer key ) throws ApplicationException;

	UserStatus get( Integer key ) throws ApplicationException;

	List<UserStatus> getAll() throws ApplicationException;

	Integer nextIntegerId() throws ApplicationException;

	UserStatus add( UserStatus entity ) throws ApplicationException;

	UserStatus update( UserStatus entity ) throws ApplicationException;
}

package br.com.mcampos.ejb.cloudsystem.security.accesslog;


import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccessLogTypeSessionLocal
{
	AccessLogType add( AccessLogType entity ) throws ApplicationException;

	AccessLogType update( AccessLogType entity ) throws ApplicationException;

	void delete( Integer key ) throws ApplicationException;

	AccessLogType get( Integer key ) throws ApplicationException;

	List<AccessLogType> getAll() throws ApplicationException;

	Integer getNextId() throws ApplicationException;
}

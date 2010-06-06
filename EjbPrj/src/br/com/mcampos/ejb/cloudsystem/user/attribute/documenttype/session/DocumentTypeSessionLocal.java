package br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.entity.DocumentType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface DocumentTypeSessionLocal extends Serializable
{
	void delete( Integer key ) throws ApplicationException;

	DocumentType get( Integer key ) throws ApplicationException;

	List<DocumentType> getAll() throws ApplicationException;

	Integer nextIntegerId() throws ApplicationException;

	DocumentType add( DocumentType entity ) throws ApplicationException;

	DocumentType update( DocumentType entity ) throws ApplicationException;
}

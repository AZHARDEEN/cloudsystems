package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcFieldPK;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcFieldSessionLocal
{
	PgcField add( PgcField entity ) throws ApplicationException;

	PgcField update( PgcField entity ) throws ApplicationException;

	void delete( PgcFieldPK key ) throws ApplicationException;

	PgcField get( PgcFieldPK key ) throws ApplicationException;

	List<PgcField> getAll( PgcPage pgcPage ) throws ApplicationException;

}

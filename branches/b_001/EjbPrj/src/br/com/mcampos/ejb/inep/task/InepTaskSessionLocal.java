package br.com.mcampos.ejb.inep.task;


import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface InepTaskSessionLocal
{
    InepTask add( InepTask entity ) throws ApplicationException;

    InepTask update( InepTask entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    InepTask get( Integer key ) throws ApplicationException;

    List<InepTask> getAll() throws ApplicationException;

    Integer getNextId() throws ApplicationException;
}

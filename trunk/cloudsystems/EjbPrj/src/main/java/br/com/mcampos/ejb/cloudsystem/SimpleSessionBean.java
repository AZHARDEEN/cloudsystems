package br.com.mcampos.ejb.cloudsystem;

import javax.persistence.Query;

public abstract class SimpleSessionBean<T> extends BaseSessionBean<T>
{
    @Override
    protected void setParameters( Query query )
    {
    }
}

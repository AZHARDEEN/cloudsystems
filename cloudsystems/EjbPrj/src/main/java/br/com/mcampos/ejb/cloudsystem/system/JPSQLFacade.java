package br.com.mcampos.ejb.cloudsystem.system;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface JPSQLFacade extends Serializable
{
    List<Object> executeSelect( String sql ) throws Exception;
}

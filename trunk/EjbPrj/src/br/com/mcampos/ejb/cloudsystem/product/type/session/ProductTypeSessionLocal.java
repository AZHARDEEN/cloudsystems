package br.com.mcampos.ejb.cloudsystem.product.type.session;


import br.com.mcampos.ejb.cloudsystem.product.type.entity.ProductType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ProductTypeSessionLocal extends Serializable
{
    ProductType add( ProductType entity ) throws ApplicationException;

    ProductType update( ProductType entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    void delete( ProductType entity ) throws ApplicationException;

    ProductType get( Integer key ) throws ApplicationException;

    List<ProductType> getAll() throws ApplicationException;

    Integer nextId() throws ApplicationException;
}

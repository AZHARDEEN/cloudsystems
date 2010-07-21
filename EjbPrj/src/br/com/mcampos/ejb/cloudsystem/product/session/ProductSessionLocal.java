package br.com.mcampos.ejb.cloudsystem.product.session;


import br.com.mcampos.ejb.cloudsystem.product.entity.Product;
import br.com.mcampos.ejb.cloudsystem.product.entity.ProductPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ProductSessionLocal extends Serializable
{
    Product add( Product entity ) throws ApplicationException;

    Product update( Product entity ) throws ApplicationException;

    void delete( ProductPK key ) throws ApplicationException;

    void delete( Product entity ) throws ApplicationException;

    Product get( ProductPK key ) throws ApplicationException;

    List<Product> getAll( Company company ) throws ApplicationException;

    Integer nextId( Company company ) throws ApplicationException;
}

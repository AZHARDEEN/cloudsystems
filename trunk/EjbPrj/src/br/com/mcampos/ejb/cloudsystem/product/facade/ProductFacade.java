package br.com.mcampos.ejb.cloudsystem.product.facade;


import br.com.mcampos.dto.product.ProductDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ProductFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    ProductDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    ProductDTO add( AuthenticationDTO auth, ProductDTO dto ) throws ApplicationException;

    ProductDTO update( AuthenticationDTO auth, ProductDTO dto ) throws ApplicationException;

    List<ProductDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;
}

package br.com.mcampos.ejb.cloudsystem.product.type.facade;


import br.com.mcampos.dto.product.ProductTypeDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ProductTypeFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    ProductTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    ProductTypeDTO add( AuthenticationDTO auth, ProductTypeDTO dto ) throws ApplicationException;

    ProductTypeDTO update( AuthenticationDTO auth, ProductTypeDTO dto ) throws ApplicationException;

    List<ProductTypeDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;
}

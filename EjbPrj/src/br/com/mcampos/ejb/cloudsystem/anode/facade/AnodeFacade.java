package br.com.mcampos.ejb.cloudsystem.anode.facade;

import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodeFormSessionLocal;

import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AnodeFacade
{
    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    public void delete( AuthenticationDTO auth, Integer key ) throws ApplicationException;

    public FormDTO get( AuthenticationDTO auth, Integer key ) throws ApplicationException;

    public List<FormDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;
}

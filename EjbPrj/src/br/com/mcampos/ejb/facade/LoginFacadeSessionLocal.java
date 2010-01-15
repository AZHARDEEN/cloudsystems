package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import javax.ejb.Local;

@Local
public interface LoginFacadeSessionLocal
{
    Boolean add(RegisterDTO dto);
}

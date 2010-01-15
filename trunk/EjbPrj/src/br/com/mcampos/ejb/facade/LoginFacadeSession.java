package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import javax.ejb.Remote;

@Remote
public interface LoginFacadeSession
{
    Boolean add(RegisterDTO dto);
}

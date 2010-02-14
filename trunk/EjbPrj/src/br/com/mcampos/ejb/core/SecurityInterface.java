package br.com.mcampos.ejb.core;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import javax.persistence.EntityManager;

public interface SecurityInterface
{


    /**
     * EntityManager Getter. Every SessionBean should have one!, just like this:
     *      public EntityManager getEm()
     *      {
     *          return em;
     *      }
     *
     * @return EntityManager
     */
    EntityManager getEntityManager();

    /**
     * Autentica o usuário. Esta será a função mais usada de todas.
     * Para QUALQUER operacao, esta função deverá ser chamada antes. Entre os
     * testes a serem executados podemos listar:
     * 1) Existe um login no banco de dados com o UserId passado?
     * 2) Existe algum token no banco de dados igual ao token passado?
     *    O log existe? O log localizado é igual ao usuário corrente?
     * 3) O Token passado é valido?
     *
     * @param currentUser
     * @return boolean true para usuário autenticado ou false
     */
    void authenticate( AuthenticationDTO currentUser ) throws ApplicationException;


    /**
     * Autentica o usuário. Esta será a função mais usada de todas.
     * Para QUALQUER operacao, esta função deverá ser chamada antes.
     *
     *
     * @param currentUser - usuario autenticado
     * @param authorizedRole Id da role autorizada.
     */
    void authenticate( AuthenticationDTO currentUser, Integer authorizedRole ) throws ApplicationException;
}

package br.com.mcampos.ejb.cloudsystem;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;

public interface SecurityInterface
{

    /**
     * Autentica o usuário. Esta será a função mais usada de todas.
     * Para QUALQUER operacao, esta função deverá ser chamada antes. Entre os
     * testes a serem executados podemos listar:
     * 1) Existe um loginSession no banco de dados com o UserId passado?
     * 2) Existe algum token no banco de dados igual ao token passado?
     * O log existe? O log localizado é igual ao usuário corrente?
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

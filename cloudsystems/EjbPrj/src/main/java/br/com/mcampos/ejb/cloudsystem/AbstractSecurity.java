package br.com.mcampos.ejb.cloudsystem;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.user.login.accesslog.AccessLog;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.sysutils.SysUtils;

import javax.persistence.NoResultException;

import org.jasypt.util.text.BasicTextEncryptor;

public abstract class AbstractSecurity extends AbstractSystemMessage implements SecurityInterface
{

    private static final String encprytPassword = "Nj9nQ6jz6Bt3";


    public AbstractSecurity()
    {
        super();
    }

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
    public void authenticate( AuthenticationDTO currentUser ) throws ApplicationException
    {
        if ( currentUser == null || SysUtils.isZero( currentUser.getUserId() ) )
            throwCommomRuntimeException( 3 );


        AccessLog log = null;
        /*
         * Existe um login no banco de dados com o UserId passado?
         */
        Login login = getEntityManager().find( Login.class, currentUser.getUserId() );
        if ( login == null )
            throwCommomRuntimeException( 2 );

        /*
         * Existe algum token no banco de dados igual ao token passado?
         * O log existe? O log localizado é igual ao usuário corrente?
         */
        if ( SysUtils.isEmpty( currentUser.getAuthenticationId() ) )
            throwCommomRuntimeException( 3 );
        try {
            log = ( AccessLog )getEntityManager().createNamedQuery( "AccessLog.findToken" )
                    .setParameter( "token", currentUser.getAuthenticationId() ).setParameter( "userId", currentUser.getUserId() )
                    .getSingleResult();
        }
        catch ( NoResultException e ) {
            /*
             * O log não existe. Não existe este usuário com o token corrente...
             */
            throwCommomRuntimeException( 2 );
        }

        /*
         * O Token passado é valido?
         */
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword( encprytPassword );
        String descryptedId = encryptor.decrypt( log.getAuthenticationId() );
        if ( descryptedId.equals( currentUser.getSessionId() ) == false )
            throwCommomRuntimeException( 2 );
        if ( log.getSessionId().equals( currentUser.getSessionId() ) == false )
            throwCommomRuntimeException( 2 );
    }

    /**
     * Autentica o usuário. Esta será a função mais usada de todas.
     * Para QUALQUER operacao, esta função deverá ser chamada antes.
     *
     *
     * @param currentUser - usuario autenticado
     * @param authorizedRole Id da role autorizada.
     */
    public void authenticate( AuthenticationDTO currentUser, Integer authorizedRole ) throws ApplicationException
    {
        authenticate( currentUser );

        /*
         * TODO implementar a busca pela role do usuário.
         * Lembrar que uma role pode possui um relacionamento entre as roles.
         */
    }
}

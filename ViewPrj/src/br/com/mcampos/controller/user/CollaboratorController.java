package br.com.mcampos.controller.user;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.facade.CollaboratorFacade;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;

public class CollaboratorController extends LoggedBaseController
{
    public static final String clientParamName = "client";

    private CollaboratorFacade session;

    private Label labelCollaboratorTitle;
    private Listheader listHeaderCode;
    private Listheader listHeaderName;
    private Listheader headerId;

    private Listbox listboxRecord;

    private ClientDTO currentClient;

    /**
     * Mapeamento do botão cmdCreate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdCreate dispara o início do processo de inclusão em um formulário
     */
    private Button cmdCreate;

    /**
     * Mapeamento do botão cmdUpdate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdUpdate dispara o início do processo de alteração de um registro
     * O registro selecionado em um formulário.
     */
    private Button cmdUpdate;

    /**
     * Mapeamento do botão cmdDelete. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdDelete dispara o início do processo de exclusão de um registro em um formulário.
     */
    private Button cmdDelete;

    /**
     * Mapeamento do botão cmdRefresh. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdRefresh dispara o processo de atualização geral.
     */
    private Button cmdRefresh;

    public CollaboratorController( char c )
    {
        super( c );
    }

    public CollaboratorController()
    {
        super();
    }

    private void configureLabels()
    {
        setLabel( labelCollaboratorTitle );
        setLabel( listHeaderCode );
        setLabel( listHeaderName );

        setLabel( headerId );

        setLabel( cmdCreate );
        setLabel( cmdUpdate );
        setLabel( cmdDelete );
        setLabel( cmdRefresh );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        configureLabels();
        currentClient = ( ClientDTO )getParameter( clientParamName );
        List < ;
        if ( currentClient != null ) {

        }
    }

    public CollaboratorFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorFacade )getRemoteSession( CollaboratorFacade.class );
        return session;
    }
}

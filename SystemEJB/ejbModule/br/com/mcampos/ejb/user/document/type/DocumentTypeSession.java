package br.com.mcampos.ejb.user.document.type;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface DocumentTypeSession extends BaseSessionInterface<DocumentType>
{

}
package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.entity.user.UserDocument;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserDocumentSessionLocal
{
    UserDocument find ( UserDocumentDTO dto );
}

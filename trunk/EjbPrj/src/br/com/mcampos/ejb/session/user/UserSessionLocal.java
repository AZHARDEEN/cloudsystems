package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.entity.user.Users;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserSessionLocal
{
    Long getRecordCount ();

    Long getClientRecordCount ( Integer owner );


    List<ListUserDTO> getUsersByRange( int firstResult, int maxResults );
    Boolean documentExists ( String document );
    UserDTO get ( Integer id );
    UserDTO getUserByDocument( String document, Integer documentId );
    List<ListUserDTO> getBusinessList (Integer userId);
}

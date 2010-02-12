package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SystemFacade
{
    /**
     * Obtem a lista dos menus de primeiro nível, ou seja,
     * aqueles menus que não possuem nenhum menu pai.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return lista com os menus
     */
    List<MenuDTO> getMenuList( AuthenticationDTO auth );


    /**
     * Atualiza o menu.
     * Esta função é usada para atualizar um dto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - o item a ser atualizado.
     */
    void updateMenu( AuthenticationDTO auth, MenuDTO dto );
}

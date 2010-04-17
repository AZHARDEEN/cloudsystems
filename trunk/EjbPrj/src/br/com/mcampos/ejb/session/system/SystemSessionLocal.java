package br.com.mcampos.ejb.session.system;


import br.com.mcampos.ejb.cloudsystem.security.util.MenuInterface;
import br.com.mcampos.ejb.core.AccessLogTypeInterface;

import javax.ejb.Local;

@Local
public interface SystemSessionLocal extends MenuInterface, AccessLogTypeInterface
{

}

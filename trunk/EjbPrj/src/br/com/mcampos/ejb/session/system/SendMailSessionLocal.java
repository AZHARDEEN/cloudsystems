package br.com.mcampos.ejb.session.system;

import javax.ejb.Local;

@Local
public interface SendMailSessionLocal
{
    void sendMail ( );
}

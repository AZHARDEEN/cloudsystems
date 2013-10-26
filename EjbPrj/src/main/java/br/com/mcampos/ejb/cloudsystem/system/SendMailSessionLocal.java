package br.com.mcampos.ejb.cloudsystem.system;


import br.com.mcampos.dto.system.SendMailDTO;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface SendMailSessionLocal extends Serializable
{
    void sendMail( SendMailDTO dto );
}

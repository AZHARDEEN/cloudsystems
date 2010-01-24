package br.com.mcampos.ejb.mdb;

import javax.ejb.MessageDriven;

import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven( mappedName = "jms/CloudSystemQueue" )
public class MailSessionBean implements MessageListener
{
    public void onMessage( Message message )
    {
    }
}

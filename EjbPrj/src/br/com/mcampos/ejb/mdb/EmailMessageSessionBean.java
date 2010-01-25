package br.com.mcampos.ejb.mdb;

import javax.annotation.Resource;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven( mappedName = "jms/CloudSystemQueue",
                activationConfig = {
                    @ActivationConfigProperty(
                        propertyName="destinationType",
                        propertyValue="javax.jms.Queue"
                    ),
                    @ActivationConfigProperty(
                        propertyName="destinationName",
                        propertyValue="jms/CloudSystemQueue"
                    )
                } 
)
public class EmailMessageSessionBean implements MessageListener
{
    public void onMessage( Message message )
    {
    }
}

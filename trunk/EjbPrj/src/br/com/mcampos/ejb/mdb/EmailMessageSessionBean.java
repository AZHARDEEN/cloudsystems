package br.com.mcampos.ejb.mdb;

import javax.annotation.Resource;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

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
        if ( message instanceof TextMessage ) 
        {
            TextMessage txtMsg = (TextMessage) message;
            String msg;

            try {
                msg = txtMsg.getText();
            }
            catch ( JMSException e ) {
            }
        }
    }
}

package br.com.mcampos.dto.system;

import java.io.Serializable;

import java.util.ArrayList;

public class SendMailDTO implements Serializable
{
    protected ArrayList<String> recipients;
    protected String subject;
    protected String body;
    
    
    public SendMailDTO ()
    {
        super();
    }

    public void setRecipients ( ArrayList<String> recipients )
    {
        this.recipients = recipients;
    }

    public ArrayList<String> getRecipients ()
    {
        if ( recipients == null )
            recipients = new ArrayList<String> ();
        return recipients;
    }
    
    public void addRecipient ( String recipient )
    {
        getRecipients().add( recipient );
    }

    public void setSubject ( String subject )
    {
        this.subject = subject;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setBody ( String body )
    {
        this.body = body;
    }

    public String getBody ()
    {
        return body;
    }
}

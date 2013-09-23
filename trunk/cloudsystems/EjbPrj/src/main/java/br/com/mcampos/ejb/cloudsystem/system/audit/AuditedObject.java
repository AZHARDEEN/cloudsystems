package br.com.mcampos.ejb.cloudsystem.system.audit;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@MappedSuperclass
public class AuditedObject
{
    public static ThreadLocal currentUser = new ThreadLocal();

    @Column( name = "AUDIT_USER" )
    protected String auditUser;
    @Column( name = "AUDIT_TIMESTAMP" )
    protected Calendar auditTimestamp;

    public String getAuditUser()
    {
        return auditUser;
    }

    public void setAuditUser( String auditUser )
    {
        this.auditUser = auditUser;
    }

    public Calendar getAuditTimestamp()
    {
        return auditTimestamp;
    }

    public void setAuditTimestamp( Calendar auditTimestamp )
    {
        this.auditTimestamp = auditTimestamp;
    }

    @PrePersist
    @PreUpdate
    public void updateAuditInfo()
    {
        setAuditUser( ( String )AuditedObject.currentUser.get() );
        setAuditTimestamp( Calendar.getInstance() );
    }
}

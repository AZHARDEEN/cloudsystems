package br.com.mcampos.ejb.inep.task;


import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.inep.dto.InepTaskDTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = InepTask.getAll, query = "select o from InepTask o" ),
                 @NamedQuery( name = InepTask.getNextId, query = "select max(o.id) from InepTask o" )} )
@Table( name = "inep.inep_task" )
public class InepTask implements Serializable, EntityCopyInterface<InepTaskDTO>
{
    public static final String getAll = "InepTask.findAll";
    public static final String getNextId = "Gender.getNextId";

    @Column( name = "tsk_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "tsk_id_in", nullable = false )
    private Integer id;

    public InepTask()
    {
    }

    public InepTask( Integer tsk_id_in, String tsk_description_ch )
    {
        this.description = tsk_description_ch;
        this.id = tsk_id_in;
    }

    public InepTask( Integer tsk_id_in )
    {
        this.id = tsk_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String tsk_description_ch )
    {
        this.description = tsk_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer tsk_id_in )
    {
        this.id = tsk_id_in;
    }

    @Override
    public InepTaskDTO toDTO()
    {
        InepTaskDTO dto = new InepTaskDTO ( getId(), getDescription() );
        return dto;
    }
}

package br.com.mcampos.dto.security;


import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.List;

public class TaskDTO extends SimpleTableDTO
{
    private List<TaskDTO> subtasks;
    private TaskDTO parent;
    private Integer parentId;


    public TaskDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public TaskDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public TaskDTO( Integer integer )
    {
        super( integer );
    }

    public TaskDTO()
    {
        super();
    }

    public void setSubtasks( List<TaskDTO> subtasks )
    {
        this.subtasks = subtasks;
    }

    public List<TaskDTO> getSubtasks()
    {
        return subtasks;
    }

    public void add( TaskDTO subtask )
    {
        if ( getSubtasks().contains( subtask ) == false )
            getSubtasks().add( subtask );
    }

    public void remove( TaskDTO subtask )
    {
        getSubtasks().remove( subtask );
    }

    public void setParent( TaskDTO parent )
    {
        this.parent = parent;
        setParentId( parent != null ? parent.getId() : 0 );
    }

    public TaskDTO getParent()
    {
        return parent;
    }

    public void setParentId( Integer parentId )
    {
        this.parentId = parentId;
    }

    public Integer getParentId()
    {
        return parentId;
    }
}

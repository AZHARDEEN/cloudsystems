package br.com.mcampos.controller.admin.system.config.task;

import br.com.mcampos.dto.security.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class TaskRootNode
{
    protected List<TaskDTO> tasks;


    public TaskRootNode()
    {
        super();
        tasks = new ArrayList<TaskDTO>();
    }

    public TaskRootNode( List<TaskDTO> rootTasks )
    {
        setTasks( rootTasks );
    }

    public void setTasks( List<TaskDTO> tasks )
    {
        this.tasks = tasks;
    }

    public List<TaskDTO> getTasks()
    {
        return tasks;
    }
}

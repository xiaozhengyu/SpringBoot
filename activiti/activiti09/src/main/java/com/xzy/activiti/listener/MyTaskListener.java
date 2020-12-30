package com.xzy.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author xzy
 * @date 2020-12-30 20:26
 * 说明：evection_listener.bpmn 定义的流程使用的监听器
 */
public class MyTaskListener implements TaskListener {

    /**
     * 1.在任务创建时指定任务负责人
     *
     * @param delegateTask -
     */
    @Override
    public void notify(DelegateTask delegateTask) {

        if ("create".equals(delegateTask.getEventName()) && delegateTask.getName().equals("创建出差申请")) {
            delegateTask.setAssignee("张三");
        }

    }
}

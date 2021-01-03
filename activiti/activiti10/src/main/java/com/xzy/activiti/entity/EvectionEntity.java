package com.xzy.activiti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xzy
 * @date 2021/1/3 17:48
 * 说明：出差信息
 * note: 必须实现Serializable接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvectionEntity implements Serializable {
    private static final long serialVersionUID = -8120840213906787537L;
    private String id;
    /**
     * 申请人
     */
    private String name;
    /**
     * 出差天数
     */
    private Double days;
    /**
     * 出差开始日期
     */
    private Date beginDate;
    /**
     * 出差结束日期
     */
    private Date engDate;
    /**
     * 备注信息
     */
    private String remark;


}

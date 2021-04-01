package com.xzy.mybatis.repository;

import com.xzy.mybatis.entity.Employee;

/**
 * @author xzy
 * @date 2021-04-01 15:06
 * 说明：EmployeeMapper.xml中定义的mapper与本接口中的方法对应
 * <mapper namespace -> 本接口全限定名
 * <select id -> 方法名称
 */
public interface EmployeeRepository {

    /**
     * 根据ID查询人员信息
     *
     * @param id - 人员ID
     * @return - 对应的人员信息
     */
    Employee findById(Integer id);
}

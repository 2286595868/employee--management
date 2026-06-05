package com.example.employee.mapper;

import com.example.employee.dto.EmployeeQueryDTO;
import com.example.employee.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeMapper {

    long countByQuery(@Param("query") EmployeeQueryDTO query);

    List<Employee> selectPageByQuery(@Param("query") EmployeeQueryDTO query,
                                     @Param("offset") long offset,
                                     @Param("pageSize") int pageSize);

    Employee selectById(@Param("id") Long id);

    long countByEmployeeNo(@Param("employeeNo") String employeeNo);

    long countByEmployeeNoExcludeId(@Param("employeeNo") String employeeNo, @Param("id") Long id);

    int insert(Employee employee);

    int update(Employee employee);

    int logicalDeleteById(@Param("id") Long id, @Param("updateTime") LocalDateTime updateTime);
}

package com.example.employee.mapper;

import com.example.employee.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {

    Department selectById(@Param("id") Long id);

    List<Department> selectByIds(@Param("ids") List<Long> ids);

    List<Department> selectActiveForDropdown();
}

package com.example.employee.service.impl;

import com.example.employee.entity.Department;
import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.service.DepartmentService;
import com.example.employee.vo.DepartmentVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentVO> listActiveDepartments() {
        return departmentMapper.selectActiveForDropdown().stream()
                .map(this::toDepartmentVO)
                .collect(Collectors.toList());
    }

    private DepartmentVO toDepartmentVO(Department department) {
        DepartmentVO vo = new DepartmentVO();
        vo.setId(department.getId());
        vo.setName(department.getName());
        vo.setParentId(department.getParentId());
        vo.setSortOrder(department.getSortOrder());
        vo.setStatus(department.getStatus());
        return vo;
    }
}

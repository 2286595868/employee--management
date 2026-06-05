package com.example.employee.service;

import com.example.employee.entity.Department;
import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.service.impl.DepartmentServiceImpl;
import com.example.employee.vo.DepartmentVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void listActiveDepartmentsMapsToVo() {
        Department department = new Department();
        department.setId(1L);
        department.setName("技术部");
        department.setParentId(null);
        department.setSortOrder(1);
        department.setStatus(1);
        when(departmentMapper.selectActiveForDropdown()).thenReturn(List.of(department));

        List<DepartmentVO> result = departmentService.listActiveDepartments();

        assertEquals(1, result.size());
        assertEquals("技术部", result.get(0).getName());
    }
}

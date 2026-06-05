package com.example.employee.controller;

import com.example.employee.service.DepartmentService;
import com.example.employee.vo.DepartmentVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@Import(com.example.employee.exception.GlobalExceptionHandler.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    void listReturnsActiveDepartments() throws Exception {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setId(1L);
        departmentVO.setName("技术部");
        departmentVO.setSortOrder(1);
        departmentVO.setStatus(1);
        when(departmentService.listActiveDepartments()).thenReturn(List.of(departmentVO));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data[0].name").value("技术部"));
    }
}

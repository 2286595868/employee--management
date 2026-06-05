package com.example.employee.controller;

import com.example.employee.common.ErrorCode;
import com.example.employee.dto.EmployeeCreateDTO;
import com.example.employee.dto.EmployeeQueryDTO;
import com.example.employee.dto.EmployeeUpdateDTO;
import com.example.employee.exception.BusinessException;
import com.example.employee.exception.GlobalExceptionHandler;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVO;
import com.example.employee.vo.PageResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(GlobalExceptionHandler.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void pageReturnsUnifiedPagedResponse() throws Exception {
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setId(1L);
        employeeVO.setEmployeeNo("E001");
        employeeVO.setName("张三");
        employeeVO.setDepartmentName("技术部");
        PageResultVO<EmployeeVO> pageResult = new PageResultVO<>(1L, List.of(employeeVO));
        when(employeeService.pageEmployees(any(EmployeeQueryDTO.class))).thenReturn(pageResult);

        mockMvc.perform(get("/api/employees")
                        .param("page", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.records[0].employeeNo").value("E001"))
                .andExpect(jsonPath("$.data.records[0].departmentName").value("技术部"));
    }

    @Test
    void createValidationErrorReturnsParameterError() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"张三",
                                  "gender":1,
                                  "status":1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("employeeNo: 员工编号不能为空"));
    }

    @Test
    void deleteMissingEmployeeReturnsNotFound() throws Exception {
        doThrow(new BusinessException(ErrorCode.NOT_FOUND, "员工不存在"))
                .when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("员工不存在"));
    }

    @Test
    void updateReturnsUnifiedSuccessResponse() throws Exception {
        doNothing().when(employeeService).updateEmployee(org.mockito.ArgumentMatchers.eq(1L), any(EmployeeUpdateDTO.class));

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employeeNo":"E002",
                                  "name":"李四",
                                  "gender":0,
                                  "phone":"13800000000",
                                  "email":"lisi@example.com",
                                  "departmentId":1,
                                  "position":"开发",
                                  "status":1,
                                  "hireDate":"2026-06-01"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }
}

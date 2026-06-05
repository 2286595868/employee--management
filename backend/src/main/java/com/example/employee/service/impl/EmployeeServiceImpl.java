package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.dto.EmployeeCreateDTO;
import com.example.employee.dto.EmployeeQueryDTO;
import com.example.employee.dto.EmployeeUpdateDTO;
import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVO;
import com.example.employee.vo.PageResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper, DepartmentMapper departmentMapper) {
        this.employeeMapper = employeeMapper;
        this.departmentMapper = departmentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResultVO<EmployeeVO> pageEmployees(EmployeeQueryDTO query) {
        long total = employeeMapper.countByQuery(query);
        if (total == 0) {
            return new PageResultVO<>(0, Collections.emptyList());
        }

        long offset = (long) (query.getPage() - 1) * query.getPageSize();
        List<Employee> employees = employeeMapper.selectPageByQuery(query, offset, query.getPageSize());
        Map<Long, String> departmentNameMap = loadDepartmentNameMap(employees);
        List<EmployeeVO> records = employees.stream()
                .map(employee -> toEmployeeVO(employee, departmentNameMap.get(employee.getDepartmentId())))
                .collect(Collectors.toList());
        return new PageResultVO<>(total, records);
    }

    @Override
    @Transactional
    public void createEmployee(EmployeeCreateDTO dto) {
        if (employeeMapper.countByEmployeeNo(dto.getEmployeeNo()) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "员工编号已存在");
        }
        validateDepartment(dto.getDepartmentId());

        LocalDateTime now = LocalDateTime.now();
        Employee employee = toEmployee(dto);
        employee.setDeleted(0);
        employee.setCreateTime(now);
        employee.setUpdateTime(now);
        employeeMapper.insert(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(Long id, EmployeeUpdateDTO dto) {
        Employee existing = employeeMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "员工不存在");
        }
        if (!Objects.equals(existing.getEmployeeNo(), dto.getEmployeeNo())
                && employeeMapper.countByEmployeeNoExcludeId(dto.getEmployeeNo(), id) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "员工编号已存在");
        }
        validateDepartment(dto.getDepartmentId());

        LocalDateTime now = LocalDateTime.now();
        Employee employee = toEmployee(dto);
        employee.setId(id);
        employee.setDeleted(existing.getDeleted());
        employee.setCreateTime(existing.getCreateTime());
        employee.setUpdateTime(now);
        int updated = employeeMapper.update(employee);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "员工不存在");
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee existing = employeeMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "员工不存在");
        }
        int updated = employeeMapper.logicalDeleteById(id, LocalDateTime.now());
        if (updated == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "员工不存在");
        }
    }

    private Employee toEmployee(EmployeeCreateDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeNo(dto.getEmployeeNo());
        employee.setName(dto.getName());
        employee.setGender(dto.getGender());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());
        employee.setDepartmentId(dto.getDepartmentId());
        employee.setPosition(dto.getPosition());
        employee.setStatus(dto.getStatus());
        employee.setHireDate(dto.getHireDate());
        return employee;
    }

    private EmployeeVO toEmployeeVO(Employee employee, String departmentName) {
        EmployeeVO vo = new EmployeeVO();
        vo.setId(employee.getId());
        vo.setEmployeeNo(employee.getEmployeeNo());
        vo.setName(employee.getName());
        vo.setGender(employee.getGender());
        vo.setPhone(employee.getPhone());
        vo.setEmail(employee.getEmail());
        vo.setDepartmentId(employee.getDepartmentId());
        vo.setDepartmentName(departmentName);
        vo.setPosition(employee.getPosition());
        vo.setStatus(employee.getStatus());
        vo.setHireDate(employee.getHireDate());
        return vo;
    }

    private Map<Long, String> loadDepartmentNameMap(List<Employee> employees) {
        List<Long> departmentIds = employees.stream()
                .map(Employee::getDepartmentId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (departmentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return departmentMapper.selectByIds(departmentIds).stream()
                .collect(Collectors.toMap(Department::getId, Department::getName, (left, right) -> left));
    }

    private void validateDepartment(Long departmentId) {
        if (departmentId == null) {
            return;
        }
        Department department = departmentMapper.selectById(departmentId);
        if (department == null || !Objects.equals(department.getDeleted(), 0) || !Objects.equals(department.getStatus(), 1)) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "部门不存在");
        }
    }
}

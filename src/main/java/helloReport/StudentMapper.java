package helloReport;

import java.util.List;

public interface StudentMapper {
    
    List<Student> selectByDepartmentId(int departmentId);

}

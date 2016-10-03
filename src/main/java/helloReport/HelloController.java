package helloReport;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.sf.jasperreports.engine.JRException;

@Controller
public class HelloController {
    
    @Autowired StudentMapper studentMapper;
    @Autowired SimpleDriverDataSource dataSource;
    
    @RequestMapping("/studentList1.do")
    public String studentList1() {
        return "studnetList1";
    }
    
    @RequestMapping("/studentList2.do")
    public String studentList2() {
        return "studnetList2";
    }   
    
    @RequestMapping("/report/studentList.do")
    public void studentListReport(@RequestParam("did") int did, @RequestParam("type") String type, 
            HttpServletRequest request, HttpServletResponse response) throws JRException, IOException, SQLException {
        ReportBuilder reportBuilder = new ReportBuilder("studentList", request, response);
        reportBuilder.setConnection(dataSource.getConnection());
        reportBuilder.setParameter("departmentId", did);
        reportBuilder.addSubReport("coursesOfStudent.jasper");
        reportBuilder.build(type);
    }
}

package helloReport;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.jasperreports.engine.JRException;

@Controller
public class HelloController {
    
    @Autowired StudentMapper studentMapper;
    
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
            HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
        List<Student> list = studentMapper.selectByDepartmentId(did);
        ReportBuilder reportBuilder = new ReportBuilder("studentList", list, request, response);
        reportBuilder.build(type);
    }
}

package helloReport;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;

public class ReportBuilder {
    static String reportFolderPath;
    HttpServletRequest request;
    HttpServletResponse response;
    JasperReport report;
    List<String> subReports;
    Connection connection;
    Map<String,Object> params = new HashMap<String,Object>();
    
    public ReportBuilder(String reportFileName, 
            HttpServletRequest request, HttpServletResponse response) throws JRException {
        this.request = request;
        this.response = response;        
        if (reportFolderPath == null)
            reportFolderPath = request.getSession().getServletContext().getRealPath("/WEB-INF/report");
        String reportFilePath = reportFolderPath + "/" + reportFileName + ".jasper";
        this.report = (JasperReport)JRLoader.loadObjectFromFile(reportFilePath);
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void addSubReport(String fileName) {
        if (subReports == null) subReports = new ArrayList<String>();
        subReports.add(fileName);
    }
    
    public void setParameter(String name, Object value) {
        params.put(name, value);
    }
    
    private void addSubReportToParams(Map<String,Object> params) {
        if (subReports != null && subReports.size() > 0)
            for (String s : subReports) {
                String path = reportFolderPath + "/" + s;
                params.put(s, path);
            }        
    }
    
    public void build(String type) throws JRException, IOException {
        switch (type) {
        case "pdf": buildPDFReport(); break;
        case "xlsx": buildXlsxReport(); break;
        default: buildHtmlReport(); break;
        }        
    }
    
    public void buildHtmlReport() throws JRException, IOException {
        response.setCharacterEncoding("UTF-8");
        params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        addSubReportToParams(params);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, connection);
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(jasperPrint);
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
        configuration.setIgnorePageMargins(true);
        configuration.setBorderCollapse("collapse");
        configuration.setSizeUnit(HtmlSizeUnitEnum.POINT);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    
    public void buildXlsxReport() throws IOException, JRException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");        
        params.put(JRParameter.IS_IGNORE_PAGINATION, true);        
        addSubReportToParams(params);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, connection);
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(jasperPrint);
        JRXlsxExporter  exporter = new JRXlsxExporter (); 
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();        
        configuration.setOnePagePerSheet(false);
        configuration.setIgnorePageMargins(true);
        configuration.setIgnoreTextFormatting(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    
    public void buildPDFReport() throws JRException, IOException {
        addSubReportToParams(params);
        byte[] bytes = null;
        bytes = JasperRunManager.runReportToPdf(report, params, connection);
        response.setContentType("application/pdf");        
        response.setContentLength(bytes.length);
        ServletOutputStream ouputStream = response.getOutputStream();
        ouputStream.write(bytes, 0, bytes.length);
        ouputStream.flush();
        ouputStream.close();
    }      
}

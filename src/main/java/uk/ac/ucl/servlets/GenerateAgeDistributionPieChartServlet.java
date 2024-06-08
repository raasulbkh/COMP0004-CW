package uk.ac.ucl.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ChartGenerator;
import uk.ac.ucl.model.ModelFactory;

@WebServlet("/generateAgeDistributionPieChart")
public class GenerateAgeDistributionPieChartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model model = ModelFactory.getModel();
            JFreeChart chart = ChartGenerator.generatePieChart(model.getAgeDistribution());

            String realPath = getServletContext().getRealPath("/charts/ageDistributionPie.png");
            File file = new File(realPath);
            file.getParentFile().mkdirs();

            ChartUtils.saveChartAsPNG(file, chart, 600, 400);

            response.sendRedirect("showChart.jsp?chartPath=charts/ageDistributionPie.png");
        } catch (Exception e) {
            throw new ServletException("Problem generating chart", e);
        }
    }
}


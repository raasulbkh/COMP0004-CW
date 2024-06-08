package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addPatientServlet")
public class AddPatientServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Model model = ModelFactory.getModel();
            Map<String, String> patientData = new HashMap<>();
            patientData.put("ID", request.getParameter("ID"));
            patientData.put("BIRTHDATE", request.getParameter("BIRTHDATE"));
            patientData.put("DEATHDATE", request.getParameter("DEATHDATE"));
            patientData.put("SSN", request.getParameter("SSN"));
            patientData.put("DRIVERS", request.getParameter("DRIVERS"));
            patientData.put("PASSPORT", request.getParameter("PASSPORT"));
            patientData.put("PREFIX", request.getParameter("PREFIX"));
            patientData.put("FIRST", request.getParameter("FIRST"));
            patientData.put("LAST", request.getParameter("LAST"));
            patientData.put("SUFFIX", request.getParameter("SUFFIX"));
            patientData.put("MAIDEN", request.getParameter("MAIDEN"));
            patientData.put("MARITAL", request.getParameter("MARITAL"));
            patientData.put("RACE", request.getParameter("RACE"));
            patientData.put("ETHNICITY", request.getParameter("ETHNICITY"));
            patientData.put("GENDER", request.getParameter("GENDER"));
            patientData.put("BIRTHPLACE", request.getParameter("BIRTHPLACE"));
            patientData.put("ADDRESS", request.getParameter("ADDRESS"));
            patientData.put("CITY", request.getParameter("CITY"));
            patientData.put("STATE", request.getParameter("STATE"));
            patientData.put("ZIP", request.getParameter("ZIP"));

            model.addPatient(patientData);
            model.saveToCSV("updatedPatients.csv");
            response.sendRedirect("patientList.html");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to add patient: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}

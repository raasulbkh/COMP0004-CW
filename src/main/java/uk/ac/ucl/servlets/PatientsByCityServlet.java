package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet("/patientsByCity")
public class PatientsByCityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        List<String> cities = model.getCities();
        request.setAttribute("cities", cities);

        // If a specific city is selected, fetch and set the list of patients in that city
        String selectedCity = request.getParameter("city");
        if (selectedCity != null && !selectedCity.isEmpty()) {
            List<String> patients = model.getPatientsInCity(selectedCity);
            request.setAttribute("patients", patients);
            request.setAttribute("selectedCity", selectedCity);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/patientsByCity.jsp");
        dispatcher.forward(request, response);
    }
}



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

@WebServlet("/patientDetails.html")
public class PatientDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the index of the patient from the request
        int patientIndex = Integer.parseInt(request.getParameter("index"));

        Model model = ModelFactory.getModel();
        Map<String, String> patientDetails = model.getPatientDetails(patientIndex);
        request.setAttribute("patientDetails", patientDetails);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/patientDetails.jsp");
        dispatch.forward(request, response);
    }
}



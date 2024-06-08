package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/livingPatientsSortedByAge")
public class LivingPatientsSortedByAgeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        List<String> patientsSortedByAge = model.getListOfPatientsSortedByAge();
        request.setAttribute("patientsSortedByAge", patientsSortedByAge);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/listOfLivingPatientsSortedByAge.jsp");
        dispatcher.forward(request, response);
    }
}

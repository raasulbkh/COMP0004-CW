package uk.ac.ucl.servlets;

import uk.ac.ucl.model.DataFrame;
import uk.ac.ucl.model.JSONWriter;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/saveDataFrameToJson")
public class SaveDataFrameToJsonServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Model model = ModelFactory.getModel();
        DataFrame dataFrame = model.getDataFrame();
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=dataFrame.json");

        // Converting DataFrame to JSON and writing directly to the response's OutputStream
        try (OutputStream out = response.getOutputStream()) {
            JSONWriter.writeDataFrameToJson(dataFrame, out);
        }
    }
}

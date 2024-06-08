package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONWriter {

    public static void writeDataFrameToJson(DataFrame dataFrame, OutputStream outputStream) throws IOException {
        List<Map<String, String>> data = dataFrame.toList();
        ObjectMapper mapper = new ObjectMapper();
        //for readability
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("patients", data);

        mapper.writeValue(outputStream, wrapper);
    }
}

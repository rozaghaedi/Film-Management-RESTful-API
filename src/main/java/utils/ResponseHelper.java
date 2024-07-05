package utils;

import com.google.gson.Gson;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * The ResponseHelper class provides utility methods for sending HTTP responses in various formats.
 */

public class ResponseHelper {
	
    //Constructs a ResponseHelper instance.
     // @param gson The Gson instance for JSON serialization.
    
    public ResponseHelper(Gson gson) {
    	//Sends an HTTP response with the given object serialized in the specified format.

    }

    public static void sendResponse(Object object, String format, HttpServletResponse response, Gson gson) throws IOException {
        response.setContentType(getContentType(format));
        PrintWriter out = response.getWriter();
        if ("text/xml".equals(format)) {
            // XML serialization logic
        } else if ("text/string".equals(format)) {
            out.print(object.toString());
        } else {
            out.print(gson.toJson(object));
        }
        out.close();
    }
        //Determines the content type based on the specified format.
    private static String getContentType(String format) {
        if ("text/xml".equals(format)) {
            return "text/xml";
        } else if ("text/string".equals(format)) {
            return "text/plain";
        } else {
            return "application/json";
        }
    }
   //Sends an error response with the specified message and status code.
    
    public static void sendError(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(message);
        out.close();
    }
    //Sends a success response with the specified message and status code.

    public static void sendSuccess(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(message);
        out.close();
    }
}

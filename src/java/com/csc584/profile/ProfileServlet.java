package com.csc584.profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Receives the form data (HTTP POST) from index.html, validates and cleans it,
 * then forwards it to profile.jsp for display.
 *
 * Note: uses the javax.* namespace (Tomcat 9 / NetBeans / GlassFish 5).
 * For Tomcat 10+ change the imports to jakarta.servlet.*
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Make sure special characters are read correctly
        request.setCharacterEncoding("UTF-8");

        // 2. Read the submitted form data
        String name      = clean(request.getParameter("name"));
        String studentId = clean(request.getParameter("studentId"));
        String program   = clean(request.getParameter("program"));
        String email     = clean(request.getParameter("email"));
        String intro     = clean(request.getParameter("intro"));
        String[] hobbies = request.getParameterValues("hobbies"); // null if none ticked

        // 3. Simple server-side validation (never trust the browser alone)
        if (name.isEmpty() || studentId.isEmpty() || program.isEmpty()
                || email.isEmpty() || intro.isEmpty()) {
            response.sendRedirect("index.html");
            return;
        }

        // 4. Escape values to prevent HTML/script injection (XSS) when displayed
        List<String> hobbyList = new ArrayList<>();
        if (hobbies != null) {
            for (String h : hobbies) {
                hobbyList.add(escape(h));
            }
        }

        // 5. Pass the processed data to the JSP
        request.setAttribute("name", escape(name));
        request.setAttribute("initial", escape(name.substring(0, 1).toUpperCase()));
        request.setAttribute("studentId", escape(studentId));
        request.setAttribute("program", escape(program));
        request.setAttribute("email", escape(email));
        request.setAttribute("intro", escape(intro).replace("\n", "<br>"));
        request.setAttribute("hobbies", hobbyList);

        // 6. Forward (server-side) to the JSP view
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    /** Opening profile URL directly (GET) just sends the user back to the form. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    private static String clean(String s) {
        return s == null ? "" : s.trim().replace("\r", "");
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}

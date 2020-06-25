package com.urice.webapp.web;

import com.urice.webapp.Config;
import com.urice.webapp.model.Resume;
import com.urice.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello" : "Hello " + name + '!');
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>\n" +
                        "<head>\n" +
                        "<style>\n" +
                        "table, th, td {\n" +
                        "  border: 1px solid black;\n" +
                        "  border-collapse: collapse;\n" +
                        "}\n" +
                        "th, td {\n" +
                        "  padding: 5px;\n" +
                        "}" +
                        "th {\n" +
                        "  text-align: left;\n" +
                        "}" +
                        "</style>\n" +
                        "</head>" +
                        "<body>\n" +
                        "\n" +
                        "<h2>Resume HTML Table</h2>\n" +
                        "\n" +
                        "<table style=\"width:100%\">\n" +
                        "  <tr>\n" +
                        "    <th>uuid</th>\n" +
                        "    <th>FullName</th> \n" +
                        "  </tr>\n");
        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>" +
                            "<td>" + resume.getUuid() + "</td>" +
                            "<td>" + resume.getFullName() + "</td>" +
                            "</tr>"
            );
        }
        writer.write(
                "</table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>"
        );
    }
}

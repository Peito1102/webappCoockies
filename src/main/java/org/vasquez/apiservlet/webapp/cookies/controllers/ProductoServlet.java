package org.vasquez.apiservlet.webapp.cookies.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vasquez.apiservlet.webapp.cookies.models.Producto;
import org.vasquez.apiservlet.webapp.cookies.services.LoginService;
import org.vasquez.apiservlet.webapp.cookies.services.LoginServiceImpl;
import org.vasquez.apiservlet.webapp.cookies.services.ProductoService;
import org.vasquez.apiservlet.webapp.cookies.services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService service = new ProductoServiceImpl();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceImpl();
        Optional<String> cookieOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("     <head>");
            out.println("         <meta charset=\"UTF-8\">");
            out.println("         <title>Listado de Productos</title>");
            out.println("     </head>");
            out.println("     <body>");
            out.println("         <h1>Listado de Productos!</h1>");
            cookieOptional.ifPresent(s -> out.println("         <div style='color: blue;'>Hola: " + s + "</div>"));
            out.println("           <table>");
            out.println("               <tr>");
            out.println("                   <th>id</th>");
            out.println("                   <th>nombre</th>");
            out.println("                   <th>tipo</th>");
            if (cookieOptional.isPresent()) {
                out.println("                   <th>precio</th>");
            }
            out.println("               </tr>");
            productos.forEach(p -> {
                out.println("               <tr>");
                out.println("                   <td>" + p.getId() + "</td>");
                out.println("                   <td>" + p.getNombre() + "</td>");
                out.println("                   <td>" + p.getTipo() + "</td>");
                if (cookieOptional.isPresent()){
                    out.println("                   <td>" + p.getPrecio() + "</td>");
                }
                out.println("               </tr>");
            });
            out.println("           </table>");
            out.println("         <p><a href='"+ req.getContextPath() +"/index.html'>Volver</a></p>");
            out.println("     </body>");
            out.println("</html>");
        }

    }
}

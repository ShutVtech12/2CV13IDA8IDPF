/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.ipn.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class MenuCRUD extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String ruta = request.getRealPath("/");
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "preguntas.xml");
        try (PrintWriter out = response.getWriter()) {
            String[] prueba={"Ejercicio 1", "Ejercicio 2", "Ejercicio 3"};
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Bienvenido</title>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align='center'>Crear, Altas, Bajas y Cambios</h1>");
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("pregunta");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Preguntas</th>");
            out.println("<th>Acciones</th>");
            out.println("</tr>");
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                out.println("<tr>");
                out.println("<td>");
                out.println(node.getChildText("nombre"));
                out.println("</td>");
                out.println("<td>");
                out.println("<a href='Leer?id="+node.getAttributeValue("id")+"' class='btnV'>Mostrar Ejercicio</a>");
                out.println("<a href='Actualizar?id="+node.getAttributeValue("id")+"' class='btnM'>Modificar Ejercicio</a>");
                out.println("<a href='Eliminar?id="+node.getAttributeValue("id")+"' class='btnEli'>Eliminar Ejercicio</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            out.println("<p align='center'><a href='crearForm.html' class='btnE'>Crear Nueva Pregunta</a></p>");
            out.println("<p align='center'><a href='"+request.getContextPath()+"' class='btnE'>Voler al menu principal</a></p>");
            out.println("</body>");
            out.println("</html>");
        } catch (JDOMException ex) {
            Logger.getLogger(MenuCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

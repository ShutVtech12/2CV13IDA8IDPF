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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Leer extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ruta = request.getRealPath("/");
        int id = Integer.parseInt(request.getParameter("id"));
        int idI;
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "preguntas.xml");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Ver Ejercicio</title>");
        out.println("<link href='style.css' rel='stylesheet' type='text/css'/>");
        out.println("</head>");
        out.println("<h3 align='center'>Ejercicios Numero "+id+"</h3>");
        out.println("<body>");
        try {
            
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List idPregunta= rootNode.getChildren("pregunta");
            
            for(int z=0;z<idPregunta.size();z++){
                Element pre = (Element) idPregunta.get(z);
                
                idI = Integer.parseInt(pre.getAttributeValue("id"));
                
                if(id==idI){
                    Element node = (Element) idPregunta.get(z);
                    
                    //out.println("<h3 align='center'> fome despues elemnt</h3>");
                    out.println("<form name='frmDatos' class='formchic' enctype='multipart/form-data'>");
                    out.println("<div>");
                    out.println("<input type='text' name='txtNombre' value='" + node.getChildText("nombre") + "' readonly='readonly'>");
                    out.println("</div>");
                    out.println("<div>");
                    out.println("<input type='text' name='txtPregunta' value='" + node.getAttributeValue("texto") + "' readonly='readonly'>");
                    out.println("</div>");
                    out.println("<div>");
                    out.println("<input type='text' name='txtRespuesta' value='" + node.getAttributeValue("respuesta") + "' readonly='readonly'>");
                    out.println("</div>");
                    //error de element
                    //node.getChild("triangulo").getAttribute("co")
                    //Element tri = (Element) node.getChildren("triangulo");
                    out.println("<div>");
                    out.println("<input type='text' name='txtCatetoO' value='" + node.getChild("triangulo").getChildText("co") + "' readonly='readonly'>");
                    out.println("</div>");
                    
                    out.println("<div>");
                    out.println("<input type='text' name='txtCatetoA' value='" + node.getChild("triangulo").getChildText("ca") + "' readonly='readonly'>");
                    out.println("</div>");
                    
                    out.println("<div>");
                    out.println("<input type='text' name='txtAngulo' value='" + node.getChild("triangulo").getChildText("angulo") + "' readonly='readonly'>");
                    out.println("</div>");
                    //tri.getAttributeValue("angulo")

                    out.println("<div align='center'>");
                    out.println("<p><a class='btnE' aria-current='page' href='MenuCRUD'>Regresar</a></p>");
                    out.println("</div>");
                    out.println("</form>");
                    
                }
            }
            
            out.println("</body>");
            out.println("</html>");

        } catch (IOException | JDOMException io) 
          {
		System.out.println(io.getMessage());
	  } 
    }

    

}

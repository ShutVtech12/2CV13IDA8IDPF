/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.ipn.web;

import java.io.File;
import java.io.FileWriter;
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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Eliminar extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String filePath = request.getRealPath("/");
            int id = Integer.parseInt(request.getParameter("id"));
            int idI,cont=0;
            String di = request.getParameter("id");
            SAXBuilder builder = new SAXBuilder();     
            File xmlFile = new File(filePath+"preguntas.xml");                
            Document doc = builder.build(xmlFile);
            Element root=doc.getRootElement();
            List idPregunta= root.getChildren("pregunta"); 
            for(int z=0;z<idPregunta.size();z++){
                Element pre = (Element) idPregunta.get(z);
                idI = Integer.parseInt(pre.getAttributeValue("id"));
                if(id==idI){
                    out.println("<h1 align='center'>Entra al if</h1>");
                    root.removeChild("pregunta");
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    FileWriter writer = new FileWriter(filePath+"preguntas.xml");                
                    xmlOutput.output(doc, writer);
                    writer.flush();
                    writer.close();
                }
                //else{
                    //pre.getAttribute("id").setValue(Integer.toString(z+1));
                    //cont++;
                //}     
            }
             for(int z1=0;z1<idPregunta.size();z1++){
                 Element pre = (Element) idPregunta.get(z1);
                 pre.getAttribute("id").setValue(Integer.toString(z1+1));
                 
             }
            
            
            
            //root.removeContent(id);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            FileWriter writer = new FileWriter(filePath+"preguntas.xml");                
            xmlOutput.output(doc, writer);
            writer.flush();
            writer.close();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CUIDADO</title>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align='center'>Se Elimino Correctamente</h1>");
            out.println("<p align='center'><a href='MenuCRUD' class='btnE'>Regresar</a></p>");
            out.println("</body>");
            out.println("</html>");
        } catch (JDOMException ex) {
            Logger.getLogger(Eliminar.class.getName()).log(Level.SEVERE, null, ex);
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

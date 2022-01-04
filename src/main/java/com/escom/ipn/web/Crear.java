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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Crear extends HttpServlet {   
    
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024*1024;
    private int maxMemSize = 50 * 1024*1024;
    private File file ;
    private String[] extens = {".png", ".jpg", ".jpeg",};
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //drg imgA-D targ imgTA
        int aumento;
        String nombresArchivos[] = new String[4*2];
        String dragsTargetsTexto[] = new String[4*2];
        int idxFiles=0, idxFields = 0;
        String nomPregunta = request.getParameter("txtNombre");//cambiar a nombre de campo
        String textoPregunta = request.getParameter("txtPregunta");
        String respuestaPregunta = request.getParameter("txtRespuesta");
        String co = request.getParameter("catetoO");
        String ca = request.getParameter("catetoA");
        String angulo = request.getParameter("angulo");
        String nomImagen = request.getParameter("nomImg");
        String img = request.getParameter("img");


        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css'/>");
            //La ruta de nuestra proyecto
            filePath = request.getRealPath("/");
            SAXBuilder builder = new SAXBuilder();     
            File xmlFile = new File(filePath+"preguntas.xml");
            
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("pregunta");
            if(list.size()!=0)
            {
                aumento=list.size()+1;
            } else {
                aumento = 1;
            }
            Document doc = builder.build(xmlFile);
            Element root=doc.getRootElement();
            Element pregunta= new Element("pregunta");
            pregunta.setAttribute(new Attribute("id",Integer.toString(aumento)));
            pregunta.setAttribute(new Attribute("texto",textoPregunta));
            pregunta.setAttribute(new Attribute("respuesta",respuestaPregunta));
            
            Element nombreP = new Element("nombre");
            nombreP.setText(nomPregunta);
            
            Element triangulo = new Element("triangulo");
            
            Element catOp = new Element("co");
            catOp.setText(co);
            
            Element catAd = new Element("ca");
            catAd.setText(ca);
            
            Element aangulo = new Element("angulo");
            aangulo.setText(angulo);
            
            //triangulo.setAttribute(new Attribute("ca",ca));
            //triangulo.setAttribute(new Attribute("co",co));
            //triangulo.setAttribute(new Attribute("angulo",angulo));
            

            
            Element imagenT = new Element("imagen");
            imagenT.setAttribute("Imagen", img);
            imagenT.setText(nomImagen);
            
            
            
           

            triangulo.addContent(catOp);
            triangulo.addContent(catAd);
            triangulo.addContent(aangulo);
            

            
            pregunta.addContent(nombreP);
            pregunta.addContent(triangulo);
            pregunta.addContent(imagenT);

            root.addContent(pregunta);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            FileWriter writer = new FileWriter(filePath+"preguntas.xml");                
            xmlOutput.output(doc, writer);
            writer.flush();
            writer.close();
            //Nos regresaun falso o un verdadero
            isMultipart = ServletFileUpload.isMultipartContent(request);
            response.setContentType("text/html");
            if (!isMultipart) {
                out.println("<title>Error</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1 align='center'>Imagen no subida. Reintete</h1>");
                out.println("<p align='center'><a href='javascript: history.go(-1)' class='btnE'>Regresar</a></p>");
            }
            //Instaciamos un tipo de identificador del objeto
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Establecemos el tamaño maximo gracias al objeto
            factory.setSizeThreshold(maxMemSize);

            //AQUI SE DIRA DONDE SE GUARDARA 
            factory.setRepository(new File(filePath));

            //Instaciamos un objeto Factory llamado upload
            ServletFileUpload upload = new ServletFileUpload(factory);

            //Indicamos el tamaño maximo de los archivos
            upload.setSizeMax(maxFileSize);

            try {
                //Recuperamos toda la parte de nuestro formulario.
                List fileItems = upload.parseRequest(request);

                //Recorremos los items del archivo
                Iterator i = fileItems.iterator();
                
                //Con el while vamos pasando por los items recuperados.
                //while solo se ocupa cuando no sabemos cuando terminar
                while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    if (!fi.isFormField()) {
                        // Get the uploaded file parameters
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();

                        // Write the file
                        if (fileName.lastIndexOf("\\") >= 0) {
                            file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                        } else {
                            file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                        }
                        fi.write(file);
                        nombresArchivos[idxFiles++] = fieldName;
                        out.println("Archivo subido: " + nombresArchivos[idxFiles-1] + "<br />");
                    } else if (fi.isFormField()) {
                        String nombre = fi.getFieldName();
                        out.println("<p>OBTENEMOS LOS DATOS DED LOS CAMPOS: " + nombre + "</p>");
                        if (nombre.contains("nombrePregunta")) {
                            nomPregunta = fi.getString();
                        }
                        if (nombre.contains("pregunta")) {
                            textoPregunta = fi.getString();
                        }
                        if (nombre.contains("respuesta")) {
                            respuestaPregunta = fi.getString();
                        }
                        if (nombre.contains("co")) {
                            respuestaPregunta = fi.getString();
                        }
                        if (nombre.contains("ca")) {
                            respuestaPregunta = fi.getString();
                        }
                        if (nombre.contains("angulo")) {
                            respuestaPregunta = fi.getString();
                        }
                        if (nombre.contains("imagen")) {
                            respuestaPregunta = fi.getString();
                        }
                    }
                }
            } catch (Exception ex) {
                out.println("Falle 10");
                System.out.println(ex);
            }
            out.println("</body>");
            out.println("</html>");
        } catch (JDOMException ex) {
            Logger.getLogger(Crear.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
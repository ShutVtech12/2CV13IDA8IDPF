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


public class DActualizar extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        int id = Integer.parseInt(request.getParameter("idP"));
        int idI; 
        
        
        //String idP= request.getParameter("idP");
        String nomPregunta = request.getParameter("txtNombre");//cambiar a nombre de campo
        String textoPregunta = request.getParameter("txtPregunta");
        String respuestaPregunta = request.getParameter("txtRespuesta");
        String co = request.getParameter("txtCatetoO");
        String ca = request.getParameter("txtCatetoA");
        String angulo = request.getParameter("txtAngulo");
        //String nomImagen = request.getParameter("nomImg");
        //String img = request.getParameter("img");
        
                String ruta=request.getRealPath("/");
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>DActualizar</title>");   
                out.println("<link href='style.css' rel='stylesheet' type='text/css'/>");
                out.println("</head>");
                out.println("<body>");
                
                try{
                    
                    SAXBuilder builder = new SAXBuilder();
                    File xmlFile = new File(ruta+"preguntas.xml");

                    Document doc = (Document) builder.build(xmlFile);
                    Element rootNode = doc.getRootElement();
                    
                    //Element pregunta = rootNode.getChild("pregunta");
                    List idPregunta= rootNode.getChildren("pregunta");
                    
                    for(int z=0;z<idPregunta.size();z++){
                        Element pre = (Element) idPregunta.get(z);
                        
                        idI = Integer.parseInt(pre.getAttributeValue("id"));
                        
                        if(id==idI){
                            //out.println("<h1 align='center'>"+pre.getAttributeValue("id")+"</h1>");
                            
                            pre.getAttribute("texto").setValue(textoPregunta);
                            pre.getAttribute("respuesta").setValue(respuestaPregunta);
                            Element nombreP = pre.getChild("nombre");
                            nombreP.setText(nomPregunta);
                            
                            Element tri = pre.getChild("triangulo");
                            
                            Element catOp = tri.getChild("co");
                            catOp.setText(co);
                            
                            Element catAd = tri.getChild("ca");
                            catAd.setText(ca);
                            
                            Element tAngulo = tri.getChild("angulo");
                            tAngulo.setText(angulo);
                            
                            
                            //pre.getChild("triangulo").getAttribute("ca").setValue(ca);
                            //tri.getAttribute("co").setValue(co);
                            //tri.getAttribute("angulo").setValue(angulo);
                            
                            out.println("<h3 align='center'> fome despues triangulo</h3>");

                            //tri.getAttribute("co").setValue(co);
                            //tri.getAttribute("ca").setValue(ca);
                            //tri.getAttribute("angulo").setValue(angulo);
                            
                            
                            
                                
                        
                            XMLOutputter xmlOutput = new XMLOutputter();

                            xmlOutput.setFormat(Format.getPrettyFormat());
                            FileWriter writer = new FileWriter(ruta+"preguntas.xml");                
                            xmlOutput.output(doc, writer);
                            writer.flush();
                            writer.close();  
                        
                        }
                    
                    }
                    
                    
                }
                catch(IOException e){
                    e.printStackTrace();
                } catch (JDOMException ex) {
                ex.printStackTrace();
            }
                
                
                //out.println("<h1 align='center'>"+respuestaPregunta+"</h1>");
                out.println("<h1 align='center'>Se Actualizo Correctamente</h1>");
                out.println("<p align='center'><a href='MenuCRUD' class='btnE'>Regresar</a></p>");
                out.println("</body>");
                out.println("</html>");
                
            
        
        
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

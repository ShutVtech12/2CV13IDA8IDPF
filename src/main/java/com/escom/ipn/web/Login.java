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

public class Login extends HttpServlet {

    private PrintWriter outter;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        outter = response.getWriter();
                 response.setContentType("application/json");
      response.addHeader("Access-Control-Allow-Origin", "*");
 String usr = request.getParameter("User");
        String pass = request.getParameter("password");
        try (PrintWriter out = response.getWriter()) {
            String ruta=request.getRealPath("/");                
	  SAXBuilder builder = new SAXBuilder();
         boolean b = true;
	  File xmlFile = new File(ruta+"usuarios.xml");
        try {
		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("usr");             
		for (int i = 0; i < list.size(); i++) 
                {
		   Element node = (Element) list.get(i);                 
                   if (node.getAttributeValue("usuario").equals(usr)  && node.getAttributeValue("password").equals(pass)) {
                         b = false;
                         outter.write(devolverJSON(node));
                    }
		}
                if(b){
             outter.write(devolverJSONError());
                }
	  } 
          catch (IOException io) 
          {
		System.out.println(io.getMessage());
	  } 
          catch (JDOMException jdomex) 
          {
		System.out.println(jdomex.getMessage());
	  }       
        }
        
    }

    private String devolverJSONError() {
        StringBuilder json = new StringBuilder();
        json.append("[");
        json.append("{");
        json.append(jsonValue("usuario", "error"));
        json.append("}");
        json.append("]");
        return json.toString();
    }
    private String devolverJSON(Element node) {
        StringBuilder json = new StringBuilder();
        
        json.append("[");
        json.append("{");
        json.append(jsonValue("usuario", node.getAttributeValue("usuario")));
        json.append("}");
        json.append("]");
        return json.toString();
    }
    private String jsonValue(String key, Object value) {
        return new StringBuilder()
                .append("\"")
                .append(key)
                .append("\" : \"")
                .append(value)
                .append("\"")
                .toString();
    }

}

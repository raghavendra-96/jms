		package com.jmsdemo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jms.*;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GlobalServlet1
 */
public class GlobalServlet1 extends HttpServlet {
		protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
			
	Connection connection=null;		
	try{
		String ss=request.getParameter("name");
		Context iniCtx=new InitialContext();
		Queue que=(Queue)iniCtx.lookup("java:/zensar_queue");
		//javax.jms.Queue que=(javax.jms.Queue)iniCtx.lookup("java:/zensar_queue");
	    Destination dest=(Destination) que;
	
	   QueueConnectionFactory qcf=(QueueConnectionFactory) iniCtx.lookup("java:/ConnectionFactory");     //step1
	connection =qcf.createConnection();                    //step2
	Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  //step3
	MessageProducer producer=session.createProducer(dest);       //step4
	TextMessage message=session.createTextMessage(ss);  //step5
	System.out.println("sending message:"+message.getText());
	producer.send(message);
	
	//Queue
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	out.println("<html><body>");
	out.println("message"+message.getText()+"sent successfully");
	out.println("to receive message please <a href=ReceiveServlet>click here</a>");
	out.println("</body></html>");
	}
	catch(Exception e)
	{
		System.err.println("Exception occured"+e.toString());
	}
	finally{
		try{
			connection.close();
			}
		catch(JMSException e)
		{
			e.printStackTrace();
		}
	}

	
	}
		
		}



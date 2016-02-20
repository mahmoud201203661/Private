package com.controller.sql;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.DatastorePreparedStatement;
import com.controller.SqlPreparedStatement;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Download extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private static final int BUFFER_SIZE = 4096;   
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {
    	Connection conn = null;
    	try{
    		String ID = req.getParameter("ID");
    		System.out.println("ID====="+ID);
    		conn = SqlPreparedStatement.getConnection();
	    	String sql = "select * from Upload where File_ID="+ID;
	        PreparedStatement statement = conn.prepareStatement(sql);
	        
	        ResultSet result = statement.executeQuery();
	        if (result.next()) {
	            // gets file name and file blob data
		        String contentType = result.getString("Type");
		        String type = "."+contentType.split("/")[1];
	            String fileName = result.getString("User_ID")+type;
	            Blob blob = result.getBlob("File");
	            InputStream inputStream = blob.getBinaryStream();
	            int fileLength = inputStream.available();
	             
	            System.out.println("fileLength = " + fileLength);
	
	            ServletContext context = getServletContext();
	
	            // sets MIME type for the file download
	            String mimeType = context.getMimeType(fileName);
	            
	            if (mimeType == null) {        
	                mimeType = "application/octet-stream";
	            }              
	             
	            // set content properties and header attributes for the response
	            res.setContentType(mimeType);
	            res.setContentLength(fileLength);
	            String headerKey = "Content-Disposition";
	            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
	            res.setHeader(headerKey, headerValue);
	
	            // writes the file to the client
	            OutputStream outStream = res.getOutputStream();
	             
	            byte[] buffer = new byte[BUFFER_SIZE];
	            int bytesRead = -1;
	             
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outStream.write(buffer, 0, bytesRead);
	            }
//	            buffer = new byte[fileLength];
//	            inputStream.read(buffer);
//	            outStream.write(buffer);
	            inputStream.close();
	            outStream.close();             
	        } else {
	            // no file found
	            res.getWriter().print("File not found for the id: ");  
	        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        res.getWriter().print("SQL Error: " + ex.getMessage());
    } catch (IOException ex) {
        ex.printStackTrace();
        res.getWriter().print("IO Error: " + ex.getMessage());
    } finally {
        if (conn != null) {
            // closes the database connection
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }          
    }

            //http://localhost:8888/serve?blob-key=mWdxiDuqpf6IaP-Fgkl1vA
        }
}
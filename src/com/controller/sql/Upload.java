package com.controller.sql;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.controller.SqlPreparedStatement;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.models.User;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.BlobFromLocator;

@MultipartConfig
public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	
    	InputStream inputStream = null;
    	try{
    		Part part = req.getPart("myFile") ;
    		inputStream = part.getInputStream();
    	    String header[] = part.getHeader("content-disposition").split("; ");
    	    String name = header[2].split("=")[1];
    	    name = name.replace("\"", "");
    	    //name = name.replace(" ", "/");
    	    System.out.print(name);
    		Connection con = SqlPreparedStatement.getConnection();
    		String query = "insert into Upload(File_ID, User_ID, File, Name, Type)values(?, ?, ?, ?, ?)"; 
    		PreparedStatement st = con.prepareStatement(query);
    		String ID = String.format("%s", SqlPreparedStatement.increment("Files", 1));
    		st.setString(1, ID);
    		st.setString(2, User.getCurrentActiveUser().getID());
    		st.setBlob(3, inputStream);
    		st.setString(4, name);
    		st.setString(5, part.getContentType());
    		st.execute();
    		PrintWriter out = res.getWriter();
    		out.print(name);
    	}catch(Exception e){
    		System.out.println("nooooooooo");
    	}
    	
    	
    	
//        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
//        List<BlobKey> blobKeys = blobs.get("myFile");
//        //BlobKey blobKey = new BlobKey(req.getParameter("myFile"));
//        if (blobKeys == null) {
//            res.sendRedirect("/");
//        } else {
//            res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
//        }
    }
}
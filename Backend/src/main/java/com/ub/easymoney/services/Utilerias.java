/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.services;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.ub.easymoney.models.commons.reponses.Response;
import com.ub.easymoney.utils.UtilsService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

/**
 * servicios de utilerias
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/utilerias")
public class Utilerias {

    public static final String FOLDER_PATH_TO_UPLOAD = "/opt/files/";

    /**
     * Returns the server date
     *
     * @return instancia DateClass
     */
    @Path("/date")
    @GET
    public Response<Date> serverDate() {
        Response<Date> res = new Response<>();
        res.setData(new Date());
        return res;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response<String> uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        Response<String> r = new Response();
        try {
            String fileName = System.currentTimeMillis() + fileDetail.getFileName().replace(" ", "_");
            String uploadedFileLocation = FOLDER_PATH_TO_UPLOAD + fileName;
            writeToFile(uploadedInputStream, uploadedFileLocation);
            String output = "File uploaded to : " + uploadedFileLocation;
            r.setData(fileName);
            UtilsService.setOkResponse(r, output);            
        } catch (Exception ex) {
            UtilsService.setErrorResponse(r, ex);
        }
        return r;
    }

    @GET
    @Path("/download/{fileName}")
    public javax.ws.rs.core.Response downloadFile(@PathParam("fileName") String fileName) {
        StreamingOutput fileStream = (output) -> {
            try {
                java.nio.file.Path path = Paths.get(FOLDER_PATH_TO_UPLOAD + fileName);
                byte[] data = Files.readAllBytes(path);
                output.write(data);
                output.flush();
            } catch (IOException e) {
                throw new WebApplicationException("File Not Found !!");
            }
        };
        return javax.ws.rs.core.Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = " + fileName)
                .build();
    }

    @GET
    @Path("/getFile/{fileName}")
    public javax.ws.rs.core.Response getFile(@PathParam("fileName") String fileName) {
        StreamingOutput fileStream = (output) -> {
            try {
                java.nio.file.Path path = Paths.get(FOLDER_PATH_TO_UPLOAD + fileName);
                byte[] data = Files.readAllBytes(path);
                output.write(data);
                output.flush();
            } catch (IOException e) {
                throw new WebApplicationException("File Not Found !!");
            }
        };
        return javax.ws.rs.core.Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .build();
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation)
            throws FileNotFoundException, IOException {
        OutputStream out;
        int read;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }

}

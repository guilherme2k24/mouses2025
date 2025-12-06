package br.unitins.tp1.resource;

import org.jboss.resteasy.reactive.PartType;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;

public class FileUploadForm {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    private String fileName;

    @FormParam("mimeType")
    @PartType(MediaType.TEXT_PLAIN)
    private String mimeType;

    @FormParam("fileSize")
    @PartType(MediaType.TEXT_PLAIN)
    private Long fileSize;

    
    @FormParam("tipo")
    @PartType(MediaType.TEXT_PLAIN)
    private String tipo;

    
    public InputStream getFile() { return file; }
    public void setFile(InputStream file) { this.file = file; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
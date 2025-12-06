package br.unitins.tp1.dto;

public class FileUploadDTO {
    private String fileName;
    private String fileUrl;
    private String contentType;
    private Long size;

    public FileUploadDTO() {}

    public FileUploadDTO(String fileName, String fileUrl, String contentType, Long size) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.contentType = contentType;
        this.size = size;
    }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }
}
package org.resumeatsscore;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import java.io.File;

// Import LangChain4j Document tools
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Path("/api")
public class AuditResource {

    @Inject
    ResumeAiService aiService;

    @POST
    @Path("/audit")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String performAudit(
            @RestForm("jd") String jd, 
            @RestForm("resume") FileUpload file) throws Exception {
        
        // 1. Get the uploaded file
        File pdfFile = file.uploadedFile().toFile();
        
        // 2. Load and parse the PDF using LangChain4j's PDFBox integration
        Document document = loadDocument(pdfFile.toPath(), new ApachePdfBoxDocumentParser());
        String resumeText = document.text();
        
        // --- TERMINAL DEBUGGING ---
        System.out.println("========== DEBUG INFO ==========");
        System.out.println("JD Length: " + (jd != null ? jd.length() : 0));
        System.out.println("Resume Length: " + (resumeText != null ? resumeText.length() : 0));
        if (resumeText != null && resumeText.trim().length() > 0) {
            System.out.println("Preview: " + resumeText.substring(0, Math.min(100, resumeText.length())).replace("\n", " "));
        }
        System.out.println("================================");

        // Fail fast if the PDF is an image/scan
        if (resumeText == null || resumeText.trim().isEmpty()) {
            return "🚨 Error: Could not extract any text from this PDF. Please ensure it is a text-based PDF and not a scanned image.";
        }
        
        // 3. Send to Gemini
        return aiService.audit(jd, resumeText);
    }
}
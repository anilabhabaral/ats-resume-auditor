package org.resumeatsscore;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V; 
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@ApplicationScoped
public interface ResumeAiService {

    @SystemMessage("""
        You are a Senior Technical Recruiter with 15 years of experience.
        Analyze the provided Resume against the Job Description (JD).
        
        Requirements:
        1. Give a Match Score out of 100.
        2. Identify Critical Gaps (missing skills).
        3. Provide 5-7 Actionable Fixes using "Action Verb + Task + Result".
        4. List 10 Keywords for ATS optimization.
        5. Verdict: One-sentence conclusion.
        """)
    @UserMessage("Analyze this JD: {jd_text} and this Resume: {resume_text}")
    String audit(@V("jd_text") String jd, @V("resume_text") String resume);
}
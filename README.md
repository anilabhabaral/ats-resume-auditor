# 🚀 ATS Resume Auditor

An AI-powered application designed to brutally and honestly evaluate your resume against a specific job description. By leveraging Google's Gemini LLM, this tool acts as an expert Technical Recruiter, providing you with a concrete match score, identifying critical skill gaps, and rewriting your bullet points to help you bypass Applicant Tracking Systems (ATS).

## 📝 Brief Description

The **ATS Resume Auditor** bridges the gap between job seekers and automated resume-filtering systems. Users upload their resume (as a PDF) and paste a target Job Description (JD). The backend processes the document, extracts the text, and feeds it into an advanced AI prompt. 

The output gives you:
1. **Match Score:** A realistic score out of 100.
2. **Critical Gaps:** Missing "Must-Have" skills or certifications.
3. **Actionable Fixes:** Bullet point rewrites using the "Action Verb + Task + Quantifiable Result" formula.
4. **ATS Optimization:** 10 essential keywords you need to include.
5. **Verdict:** A simple "Apply as-is" or "Overhaul first" conclusion.

## 🛠️ Tech Stack Used

* **Backend:** Java 21, [Quarkus](https://quarkus.io/) (Supersonic Subatomic Java)
* **AI Orchestration:** [LangChain4j](https://github.com/langchain4j/langchain4j)
* **LLM Provider:** Google Gemini API (`gemini-1.5-flash-latest`)
* **Document Parsing:** Apache PDFBox (via LangChain4j integration)
* **Frontend:** Vanilla HTML, CSS (Custom Dark Theme), and JavaScript
* **Containerization:** Docker & Docker Compose

## ⚙️ How It Works

1. **Upload & Submit:** The user accesses the web interface, pastes the target Job Description, and uploads their resume in `.pdf` format.
2. **File Processing:** The Quarkus backend receives the multipart form data and uses LangChain4j's built-in `ApachePdfBoxDocumentParser` to accurately extract selectable text from the PDF.
3. **AI Evaluation:** The parsed resume text and the JD are injected into a highly specific `@SystemMessage` prompt using LangChain4j's declarative AI services. 
4. **Insights Generation:** Gemini evaluates the data acting as a Senior ATS Specialist and returns a structured, brutal audit.
5. **Display:** The plain-text response is sent back to the frontend and rendered in the browser.

## 💻 How to Build & Run Locally

### Prerequisites
* Java 21+ installed
* Maven 3.9+ installed
* A valid [Google Gemini API Key](https://aistudio.google.com/)

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/anilabhabaral/ats-resume-auditor.git
   cd ats-resume-auditor
   ```

# Set your API Key as an environment variable:

- **Linux/macOS:**

  ```bash
  export GEMINI_API_KEY="your_api_key_here"
  ```

- **Windows (CMD):**

  ```cmd
  set GEMINI_API_KEY="your_api_key_here"
  ```

- **Windows (PowerShell):**

  ```powershell
  $env:GEMINI_API_KEY="your_api_key_here"
  ```

# Start the Quarkus development server:

```bash
mvn clean quarkus:dev
```

# Access the application:
Open your browser and navigate to [http://localhost:8080](http://localhost:8080).

# 🐳 How to Run Using Docker Compose
If you prefer not to install Java and Maven on your local machine, you can run the entire application using Docker.

1. Clone the repository and navigate to the root directory.
2. Create an `.env` file in the project root to securely store your API key:
   
   ```plaintext
   # .env
   GEMINI_API_KEY=your_actual_api_key_here
   ```
3. Build and start the container:
   
   ```bash
docker-compose up --build -d
   ```
4. Access the application:
Navigate to [http://localhost:8080](http://localhost:8080) in your web browser.
5. To stop the application:
   
   ```bash
docker-compose down
   ```
(Note: The Dockerfile includes a specific configuration `-Djava.net.preferIPv4Stack=true` to prevent IPv6 networking issues between Docker and Google's APIs.)

# 🌟 Advantages & Real-Life Issues Solved
**The Problem:** Most job applicants throw their resumes into the "ATS black hole" without realizing that automated systems reject them simply because they are missing specific keywords or format their achievements poorly. Tailoring a resume for every single job manually takes hours.

**The Solution:**

- **Bypasses the Black Hole:** Tells you exactly which keywords the specific ATS filter will be looking for based on the JD.

- **Forces Impactful Writing:** Translates weak "responsible for X" statements into powerful, metric-driven achievements.

- **Saves Time:** Automates the most tedious part of the job application process—the tailoring—in about 30 seconds.

- **Objective Feedback:** Friends and family might give polite feedback; this tool gives brutal, actionable recruitment truths.

# ⚠️ Limitations
- **No Scanned Images:** The application utilizes Apache PDFBox to read embedded text characters. If your PDF is an exported image or a scanned photograph of a physical paper, the tool cannot extract the text.
- **Format Restriction:** Currently only accepts `.pdf` files. Word documents (`.docx`) or image files are not supported.
- **API Dependency & Timeouts:** The application relies entirely on the availability and responsiveness of the Google Gemini API. Exceptionally long JDs or heavily dense, multi-page resumes may occasionally cause a timeout.
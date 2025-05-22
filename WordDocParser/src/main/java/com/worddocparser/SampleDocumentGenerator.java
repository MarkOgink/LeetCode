package com.worddocparser;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class to generate a sample Word document with headings for testing.
 */
public class SampleDocumentGenerator {

    /**
     * Main method to generate a sample document.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        String outputPath = "samples/sample_document.docx";
        
        try {
            generateSampleDocument(outputPath);
            System.out.println("Sample document created successfully at: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error creating sample document: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generates a sample Word document with various heading levels.
     *
     * @param outputPath The path where the document will be saved
     * @throws IOException If there's an error writing the file
     */
    public static void generateSampleDocument(String outputPath) throws IOException {
        try (XWPFDocument document = new XWPFDocument()) {
            // Add Heading 1
            addHeading(document, "Chapter 1: Introduction", 1);
            addParagraph(document, "This is the introduction to the document. It provides an overview of the content.");
            
            // Add Heading 2
            addHeading(document, "Section 1.1: Background", 2);
            addParagraph(document, "This section provides background information on the topic.");
            
            // Add Heading 3
            addHeading(document, "Section 1.1.1: Historical Context", 3);
            addParagraph(document, "This subsection discusses the historical context of the topic.");
            
            // Add Heading 2
            addHeading(document, "Section 1.2: Objectives", 2);
            addParagraph(document, "This section outlines the objectives of the document.");
            
            // Add Heading 1
            addHeading(document, "Chapter 2: Methodology", 1);
            addParagraph(document, "This chapter describes the methodology used in the research.");
            
            // Add Heading 2
            addHeading(document, "Section 2.1: Research Design", 2);
            addParagraph(document, "This section outlines the research design employed in the study.");
            
            // Add Heading 2
            addHeading(document, "Section 2.2: Data Collection", 2);
            addParagraph(document, "This section describes the data collection methods used.");
            
            // Add Heading 3
            addHeading(document, "Section 2.2.1: Surveys", 3);
            addParagraph(document, "This subsection details the survey methodology.");
            
            // Add Heading 3
            addHeading(document, "Section 2.2.2: Interviews", 3);
            addParagraph(document, "This subsection explains the interview process.");
            
            // Add Heading 1
            addHeading(document, "Chapter 3: Results", 1);
            addParagraph(document, "This chapter presents the results of the research.");
            
            // Add Heading 2
            addHeading(document, "Section 3.1: Findings", 2);
            addParagraph(document, "This section presents the key findings from the research.");
            
            // Add Heading 1
            addHeading(document, "Chapter 4: Conclusion", 1);
            addParagraph(document, "This chapter concludes the document and summarizes the key points.");
            
            // Write the document
            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                document.write(out);
            }
        }
    }
    
    /**
     * Adds a heading to the document.
     */
    private static void addHeading(XWPFDocument document, String text, int level) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setStyle("Heading" + level);
        
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(true);
        
        // Set font size based on heading level
        switch (level) {
            case 1:
                run.setFontSize(16);
                break;
            case 2:
                run.setFontSize(14);
                break;
            case 3:
                run.setFontSize(12);
                break;
            default:
                run.setFontSize(11);
        }
    }
    
    /**
     * Adds a regular paragraph to the document.
     */
    private static void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }
}
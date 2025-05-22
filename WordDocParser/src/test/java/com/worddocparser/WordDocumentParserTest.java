package com.worddocparser;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for WordDocumentParser.
 */
public class WordDocumentParserTest {
    
    private static final String TEST_DOCX_PATH = "src/test/resources/test_document.docx";
    private WordDocumentParser parser;
    
    @Before
    public void setUp() throws IOException {
        parser = new WordDocumentParser();
        createTestDocument();
    }
    
    @Test
    public void testParseDocument() throws IOException {
        File testFile = new File(TEST_DOCX_PATH);
        assertTrue("Test document should exist", testFile.exists());
        
        List<Heading> headings = parser.parseDocument(testFile);
        
        // Verify that headings were extracted correctly
        assertNotNull("Headings list should not be null", headings);
        assertFalse("Headings list should not be empty", headings.isEmpty());
        
        // Verify the number of headings
        assertEquals("Should have extracted 6 headings", 6, headings.size());
        
        // Verify the first heading
        assertEquals("First heading should be 'Chapter 1: Introduction'", 
                "Chapter 1: Introduction", headings.get(0).getText());
        assertEquals("First heading should be level 1", 1, headings.get(0).getLevel());
        
        // Verify a level 2 heading
        assertEquals("Second heading should be 'Section 1.1: Background'", 
                "Section 1.1: Background", headings.get(1).getText());
        assertEquals("Second heading should be level 2", 2, headings.get(1).getLevel());
    }
    
    /**
     * Creates a test Word document with various heading levels.
     */
    private void createTestDocument() throws IOException {
        File file = new File(TEST_DOCX_PATH);
        if (file.exists()) {
            return; // Don't recreate if it already exists
        }
        
        // Ensure the directory exists
        file.getParentFile().mkdirs();
        
        try (XWPFDocument document = new XWPFDocument()) {
            // Add Heading 1
            addHeading(document, "Chapter 1: Introduction", 1);
            addParagraph(document, "This is the introduction to the document.");
            
            // Add Heading 2
            addHeading(document, "Section 1.1: Background", 2);
            addParagraph(document, "This section provides background information.");
            
            // Add Heading 3
            addHeading(document, "Section 1.1.1: Historical Context", 3);
            addParagraph(document, "This subsection discusses historical context.");
            
            // Add Heading 1
            addHeading(document, "Chapter 2: Methodology", 1);
            addParagraph(document, "This chapter describes the methodology.");
            
            // Add Heading 2
            addHeading(document, "Section 2.1: Research Design", 2);
            addParagraph(document, "This section outlines the research design.");
            
            // Add Heading 2
            addHeading(document, "Section 2.2: Data Collection", 2);
            addParagraph(document, "This section describes data collection methods.");
            
            // Write the document
            try (FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
            }
        }
    }
    
    /**
     * Adds a heading to the document.
     */
    private void addHeading(XWPFDocument document, String text, int level) {
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
    private void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }
}
package com.worddocparser;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for HeadingTableGenerator.
 */
public class HeadingTableGeneratorTest {
    
    private static final String OUTPUT_DOCX_PATH = "src/test/resources/output_document.docx";
    private HeadingTableGenerator generator;
    private List<Heading> headings;
    
    @Before
    public void setUp() {
        generator = new HeadingTableGenerator();
        headings = createSampleHeadings();
    }
    
    @Test
    public void testGenerateDocument() throws IOException {
        // Generate the document
        generator.generateDocument(headings, OUTPUT_DOCX_PATH);
        
        // Verify the document was created
        File outputFile = new File(OUTPUT_DOCX_PATH);
        assertTrue("Output document should exist", outputFile.exists());
        
        // Verify the content of the document
        try (FileInputStream fis = new FileInputStream(outputFile);
             XWPFDocument document = new XWPFDocument(fis)) {
            
            // Check that the document has a title
            boolean hasTitleParagraph = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (paragraph.getText().contains("Document Headings")) {
                    hasTitleParagraph = true;
                    break;
                }
            }
            assertTrue("Document should have a title paragraph", hasTitleParagraph);
            
            // Check that the document has a table
            assertFalse("Document should have tables", document.getTables().isEmpty());
            
            // Get the first table
            XWPFTable table = document.getTables().get(0);
            
            // Check the table has the correct number of rows (headings + header row)
            assertEquals("Table should have the correct number of rows", 
                    headings.size() + 1, table.getNumberOfRows());
            
            // Check the header row
            XWPFTableRow headerRow = table.getRow(0);
            assertEquals("First column header should be 'Level'", 
                    "Level", headerRow.getCell(0).getText());
            assertEquals("Second column header should be 'Heading Text'", 
                    "Heading Text", headerRow.getCell(1).getText());
            
            // Check a sample data row
            XWPFTableRow firstDataRow = table.getRow(1);
            assertEquals("First row level should be '1'", 
                    "1", firstDataRow.getCell(0).getText());
            assertTrue("First row heading text should contain 'Chapter 1'", 
                    firstDataRow.getCell(1).getText().contains("Chapter 1: Introduction"));
        }
    }
    
    /**
     * Creates a sample list of headings for testing.
     */
    private List<Heading> createSampleHeadings() {
        List<Heading> sampleHeadings = new ArrayList<>();
        
        sampleHeadings.add(new Heading("Chapter 1: Introduction", 1));
        sampleHeadings.add(new Heading("Section 1.1: Background", 2));
        sampleHeadings.add(new Heading("Section 1.1.1: Historical Context", 3));
        sampleHeadings.add(new Heading("Chapter 2: Methodology", 1));
        sampleHeadings.add(new Heading("Section 2.1: Research Design", 2));
        sampleHeadings.add(new Heading("Section 2.2: Data Collection", 2));
        
        return sampleHeadings;
    }
}
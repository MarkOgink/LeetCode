package com.worddocparser;

import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Generates a Word document containing a table of headings.
 */
public class HeadingTableGenerator {
    private static final Logger logger = LoggerFactory.getLogger(HeadingTableGenerator.class);

    /**
     * Generates a new Word document with a table of headings.
     *
     * @param headings   The list of headings to include in the table
     * @param outputPath The path where the output document will be saved
     * @throws IOException If there's an error writing the file
     */
    public void generateDocument(List<Heading> headings, String outputPath) throws IOException {
        logger.info("Generating output document with {} headings", headings.size());
        
        try (XWPFDocument document = new XWPFDocument()) {
            // Add a title
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("Document Headings");
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.addBreak();
            
            // Create a table with headings
            createHeadingsTable(document, headings);
            
            // Write the document to the output file
            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                document.write(out);
            }
        }
        
        logger.info("Output document generated successfully at: {}", outputPath);
    }

    /**
     * Creates a table with the document headings.
     *
     * @param document The document to add the table to
     * @param headings The list of headings to include in the table
     */
    private void createHeadingsTable(XWPFDocument document, List<Heading> headings) {
        if (headings.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("No headings found in the document.");
            return;
        }
        
        // Create a table with 2 columns: Level and Heading Text
        XWPFTable table = document.createTable(headings.size() + 1, 2);
        table.setWidth("100%");
        
        // Set table header
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Level");
        headerRow.getCell(1).setText("Heading Text");
        
        // Style the header row
        for (int i = 0; i < 2; i++) {
            XWPFParagraph paragraph = headerRow.getCell(i).getParagraphs().get(0);
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.getRuns().get(0);
            run.setBold(true);
            run.setFontSize(12);
        }
        
        // Add headings to the table
        for (int i = 0; i < headings.size(); i++) {
            Heading heading = headings.get(i);
            XWPFTableRow row = table.getRow(i + 1);
            
            // Add level
            row.getCell(0).setText(String.valueOf(heading.getLevel()));
            
            // Add heading text with indentation based on level
            XWPFParagraph paragraph = row.getCell(1).getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();
            
            // Add indentation based on heading level
            StringBuilder indentedText = new StringBuilder();
            for (int j = 1; j < heading.getLevel(); j++) {
                indentedText.append("    "); // 4 spaces per level
            }
            indentedText.append(heading.getText());
            
            run.setText(indentedText.toString());
        }
        
        logger.debug("Created table with {} rows", table.getNumberOfRows());
    }
}
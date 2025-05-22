package com.worddocparser;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for extracting headings from Word documents.
 * Supports both .doc and .docx formats.
 */
public class WordDocumentParser {
    private static final Logger logger = LoggerFactory.getLogger(WordDocumentParser.class);

    /**
     * Parses a Word document and extracts all headings.
     *
     * @param file The Word document file to parse
     * @return A list of headings found in the document
     * @throws IOException If there's an error reading the file
     */
    public List<Heading> parseDocument(File file) throws IOException {
        logger.info("Parsing document: {}", file.getAbsolutePath());
        
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".docx")) {
            return parseDocx(file);
        } else if (fileName.endsWith(".doc")) {
            return parseDoc(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .doc and .docx files are supported.");
        }
    }

    /**
     * Parses a .docx file to extract headings.
     *
     * @param file The .docx file to parse
     * @return A list of headings found in the document
     * @throws IOException If there's an error reading the file
     */
    private List<Heading> parseDocx(File file) throws IOException {
        List<Heading> headings = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {
            
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String styleName = paragraph.getStyle();
                
                // Check if the paragraph has a heading style
                if (styleName != null && styleName.startsWith("Heading")) {
                    try {
                        // Extract the heading level from the style name (e.g., "Heading 1" -> 1)
                        int level = Integer.parseInt(styleName.substring(styleName.length() - 1));
                        String text = paragraph.getText();
                        
                        if (text != null && !text.trim().isEmpty()) {
                            headings.add(new Heading(text, level));
                            logger.debug("Found heading level {}: {}", level, text);
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("Could not parse heading level from style: {}", styleName);
                    }
                }
            }
        }
        
        logger.info("Found {} headings in .docx document", headings.size());
        return headings;
    }

    /**
     * Parses a .doc file to extract headings.
     *
     * @param file The .doc file to parse
     * @return A list of headings found in the document
     * @throws IOException If there's an error reading the file
     */
    private List<Heading> parseDoc(File file) throws IOException {
        List<Heading> headings = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(file);
             HWPFDocument document = new HWPFDocument(fis)) {
            
            Range range = document.getRange();
            
            for (int i = 0; i < range.numParagraphs(); i++) {
                Paragraph paragraph = range.getParagraph(i);
                
                // Check if the paragraph has a heading style
                if (paragraph.isInList()) {
                    continue; // Skip list items
                }
                
                int level = getHeadingLevelFromParagraph(paragraph);
                if (level > 0) {
                    String text = paragraph.text().trim();
                    if (!text.isEmpty()) {
                        headings.add(new Heading(text, level));
                        logger.debug("Found heading level {}: {}", level, text);
                    }
                }
            }
        }
        
        logger.info("Found {} headings in .doc document", headings.size());
        return headings;
    }

    /**
     * Determines the heading level of a paragraph in a .doc file.
     * This is a simplified implementation that checks the paragraph style.
     *
     * @param paragraph The paragraph to check
     * @return The heading level (1-9) or 0 if not a heading
     */
    private int getHeadingLevelFromParagraph(Paragraph paragraph) {
        // Get the style index and check if it corresponds to a heading
        int styleIndex = paragraph.getStyleIndex();
        
        // In .doc files, heading styles typically have specific style indices
        // This is a simplified approach and may need adjustment for different documents
        if (styleIndex >= 1 && styleIndex <= 9) {
            return styleIndex;
        }
        
        return 0; // Not a heading
    }
}
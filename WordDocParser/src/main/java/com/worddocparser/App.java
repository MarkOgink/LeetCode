package com.worddocparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Word Document Heading Parser
 * This application parses a Word document and generates a new document
 * containing a table of all headings with their levels.
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting Word Document Heading Parser");
        
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.print("Enter the path to the Word document to parse: ");
            String inputPath = scanner.nextLine();
            
            System.out.print("Enter the path for the output document: ");
            String outputPath = scanner.nextLine();
            
            scanner.close();
            
            File inputFile = new File(inputPath);
            if (!inputFile.exists() || !inputFile.isFile()) {
                logger.error("Input file does not exist: {}", inputPath);
                System.err.println("Error: Input file does not exist: " + inputPath);
                return;
            }
            
            // Parse the document and extract headings
            WordDocumentParser parser = new WordDocumentParser();
            List<Heading> headings = parser.parseDocument(inputFile);
            
            // Generate the output document with a table of headings
            HeadingTableGenerator generator = new HeadingTableGenerator();
            generator.generateDocument(headings, outputPath);
            
            logger.info("Document processing completed successfully");
            System.out.println("Document processing completed successfully");
            System.out.println("Output document saved to: " + outputPath);
            
        } catch (Exception e) {
            logger.error("Error processing document", e);
            System.err.println("Error processing document: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

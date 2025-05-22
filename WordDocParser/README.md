# Word Document Heading Parser

A Java application that parses a Word document and generates a new Word document containing a table of all headings with their levels.

## Features

- Parses Microsoft Word documents (.docx and .doc formats)
- Extracts headings of all levels (Heading 1 through Heading 9)
- Generates a new Word document with a table containing:
  - Heading level (1-9)
  - Heading text (with indentation based on level)
- Supports both interactive mode and programmatic usage

## Requirements

- Java 17 or higher
- Maven 3.6 or higher (for building)

## Building the Application

To build the application, run:

```bash
mvn clean package assembly:single
```

This will create an executable JAR file with all dependencies included at `target/WordDocParser-1.0-SNAPSHOT-jar-with-dependencies.jar`.

## Running the Application

### Using the Executable JAR

To run the application directly, use:

```bash
java -jar target/WordDocParser-1.0-SNAPSHOT-jar-with-dependencies.jar
```

The application will prompt you for:
1. The path to the Word document to parse
2. The path where the output document should be saved

### Using the Convenience Script

For easier usage, you can use the provided shell script:

```bash
./run_app.sh
```

This script will:
1. Check if the JAR file exists and build it if necessary
2. Create a samples directory if it doesn't exist
3. Generate a sample document if needed
4. Run the application

The script is especially useful for first-time users or for quick testing.

## Sample Document Generation

The project includes a utility to generate a sample Word document with various heading levels for testing:

```bash
mvn compile exec:java -Dexec.mainClass="com.worddocparser.SampleDocumentGenerator"
```

This will create a sample document at `samples/sample_document.docx`.

## Project Structure

- `App.java` - Main application class
- `Heading.java` - Model class representing a heading
- `WordDocumentParser.java` - Parser for extracting headings from Word documents
- `HeadingTableGenerator.java` - Generator for creating the output document with the headings table
- `SampleDocumentGenerator.java` - Utility for generating a sample document

## Dependencies

- Apache POI - For working with Microsoft Office documents
- SLF4J - For logging facade
- Log4j2 - For logging implementation
- JUnit - For testing

## License

This project is licensed under the MIT License - see the LICENSE file for details.
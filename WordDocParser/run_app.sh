#!/bin/bash

# Script to run the Word Document Parser application

# Navigate to the project directory
cd "$(dirname "$0")"

# Check if the JAR file exists
if [ ! -f "target/WordDocParser-1.0-SNAPSHOT-jar-with-dependencies.jar" ]; then
    echo "Building the application..."
    mvn clean package assembly:single
fi

# Check if sample directory exists
if [ ! -d "samples" ]; then
    echo "Creating samples directory..."
    mkdir -p samples
    
    # Generate a sample document if it doesn't exist
    echo "Generating sample document..."
    java -cp target/WordDocParser-1.0-SNAPSHOT-jar-with-dependencies.jar com.worddocparser.SampleDocumentGenerator samples/sample_document.docx
fi

# Run the application
echo "Running Word Document Parser application..."
java -jar target/WordDocParser-1.0-SNAPSHOT-jar-with-dependencies.jar

echo "Application execution completed."
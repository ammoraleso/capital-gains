package utils;

import application.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Reads a file and returns a list of lines
     * @param filePath is the path to the file
     * @return list of lines
     */
    public static List<String> readLines(String filePath){

        String file = filePath;
        ArrayList<String> lines = new ArrayList<>();
        try {
            deleteFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine ;
            while((currentLine = reader.readLine())!=null){
                if(!currentLine.isEmpty()){
                    lines.add(currentLine);
                }
            }
        }catch (FileNotFoundException e) {
            logger.error("File not found");
        } catch (IOException e) {
            logger.error("Error reading file " + e.getMessage());
        }
        return lines;
    }

    /**
     * Writes a list of lines to a file
     * @param content is the list of lines
     * @param filePath is the path to the file
     */
    public static void writeToFile(String content, String filePath) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Deletes the file
     */
    public static void deleteFile() {
        File output = new File("src/main/resources/output.txt");
        output.delete();
    }
}
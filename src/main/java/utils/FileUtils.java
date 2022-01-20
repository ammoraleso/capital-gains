package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * Reads a file and returns a list of lines
     * @param filePath is the path to the file
     * @return list of lines
     */
    public static List<String> readLines(String filePath){
        String file = filePath;
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine ;
            while((currentLine = reader.readLine())!=null){
                if(!currentLine.isEmpty()){
                    lines.add(currentLine);
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
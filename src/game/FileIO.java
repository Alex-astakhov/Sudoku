package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Astakhov on 31.05.2016.
 */
public class FileIO {
    protected static final String PATH = System.getProperty("user.dir");

    protected List<String> readFile(String filePath){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            List<String> content = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
            reader.close();
            return content;
        }catch (FileNotFoundException e){
            System.out.println("Файл " + filePath + " поврежден или не существует");
        } catch (IOException e) {
        }
    return null;
    }

    protected void writeToFileLn(String filePath, List<String> text) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            for (String s: text) {
                writer.append(s);
                writer.newLine();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + filePath + "не найден, он будет создан");
            for (String s: text) {
                try {
                    writer.append(s);
                    writer.newLine();
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }catch (IOException e){ e.printStackTrace();}

    }

    protected void writeToFile(String filePath, List<String> text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
        for (String s: text) {
            writer.append(s);
        }
        writer.close();
    }

    protected void updateFileLn(String filePath, String text){
        List<String> fileContent = readFile(filePath);
        try {
            fileContent.add(text);
            writeToFileLn(filePath, fileContent);
        }catch (NullPointerException e){
            fileContent = new ArrayList<>();
            fileContent.add(text);
            writeToFileLn(filePath, fileContent);
        }

    }

    protected void updateFile(String filePath, String text) throws IOException {
        List<String> fileContent = readFile(filePath);
        fileContent.add(text);
        writeToFileLn(filePath, fileContent);
    }

    protected boolean recordIsPresent(String filePath, String record){
        try {
            List <String> content = readFile(filePath);
            for (String s: content){
                if (s.contains(record)){
                    return true;
                }
            }
        }catch (NullPointerException e){return false;}

        return false;
    }
    
}

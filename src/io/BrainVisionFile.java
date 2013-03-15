/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Administrator
 */
public class BrainVisionFile {
    private File source;
    private final String[] bvData = {"eeg", "avg"};
    private final String[] bvHeader = {"vhdr", "vmrk"};
    private final String[] refs = {"DataFile", "MarkerFile"};
    
    private String path;
    private String name1;
    private String name2;
    private String extension;
     
    public BrainVisionFile(File source) {
        this.source = source;
        path      = FilenameUtils.getFullPath(source.getAbsolutePath());
        
        String[] name      = FilenameUtils.getBaseName(source.getName()).split("-");
        System.out.println(Arrays.toString(name));
        if (name != null && name.length > 1) {
            name1 = name[0];
            name2 = "-" + name[1];
        } else {
            name1 = FilenameUtils.getBaseName(source.getName());
            name2 = "";
        }
        
        extension = FilenameUtils.getExtension(source.getName());
    }
    
    
    
    public void rename(String newName) throws FileNotFoundException, IOException {
        // Separate file name
       
        String fullNewName = path + newName + name2 + "." + extension;
       // System.out.println("New name: " + fullNewName);
        if (Arrays.asList(bvHeader).contains(extension)) {
            correctReferences(newName);
        } else {
            source.renameTo(new File(fullNewName));
        }
    }
    
    
    public void correctReferences(String newName) throws FileNotFoundException, IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
         reader = new BufferedReader(new FileReader(source));
         writer = new BufferedWriter(new FileWriter(path + newName + name2 +  "." + extension));
         String line;
         String lineOut;
         while ((line = reader.readLine()) != null) {
             lineOut = line;
             
             for (String ref: refs) {
                 
                 // line contains reference to other file
                 if (line.contains(ref)) {
                     String[] splitLine = line.split("[=.]");
                     if (!splitLine[0].equals(ref))
                         throw new IOException("Unexpected pattern: " + splitLine[0]);
                     else splitLine[1] = newName;
                     lineOut = "";
                     lineOut = splitLine[0] + "=" + splitLine[1] + name2 + "." + splitLine[2];
                 }
             }
             writer.append(lineOut + "\n");
             
         }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        }
    
    }
    
   
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brainvisionrenamer;

import io.BrainVisionFile;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class BrainVisionRenamer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length < 2)
             Logger.getLogger(BrainVisionRenamer.class.getName()).log(Level.SEVERE, "Not enough params" );
        else {
            try {
                // get old and new zip archive name containing BV files
                String oldName = args[0];
                String newName = args[1];
                
                // open the archive and get the file names
                // ZipFile bvArchive = new ZipFile(oldName);
                // Enumeration e = bvArchive.entries();
                
                File dir = new File(oldName);
                Logger.getLogger(BrainVisionRenamer.class.getName()).log(Level.INFO, "Opening: " + oldName  + " isDir: " + dir.isDirectory());
                File[] files = null;
                if (dir.isDirectory()) {
                    
                    files = dir.listFiles();
                }
                
                if (files == null)
                    return;
                for (File currFile: files) {
                     Logger.getLogger(BrainVisionRenamer.class.getName()).log(Level.INFO, "Next file" + currFile);
                     BrainVisionFile bv = new BrainVisionFile(currFile);     
                     bv.rename(newName);
                }
              
            } catch (IOException ex) {
                Logger.getLogger(BrainVisionRenamer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}

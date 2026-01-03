package textEditors.model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager
{
    public List<String> onLoadFile()
    {
        List<String> lines = new ArrayList<>();
        JFileChooser chooser = new JFileChooser();
        File file = null;
        try
        {
            file = new File(new File(".").getCanonicalPath());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        chooser.setCurrentDirectory(file);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Plain text", "txt");
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try {
                lines = Files.readAllLines(f.toPath(), Charset.forName("UTF-8"));
             //   taSource.setText(String.join("\n", lines));
             //   lblSource.setText("Source file: " + f.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return lines;
    }

}

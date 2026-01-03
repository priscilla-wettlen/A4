package textEditors.view;

import textEditors.main.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class MainPanel extends JPanel {
    private  int width;
    private  int height ;
    private Controller controller;

    private JPanel pnlFindReplace = new JPanel(new GridLayout(3, 2, 5, 5));
    private JLabel lblFind = new JLabel("Find:", JLabel.TRAILING);
    private JTextField tfFind = new JTextField();
    private JLabel lblReplaceWith = new JLabel("Replace with:", JLabel.TRAILING);
    private JTextField tfReplaceWith = new JTextField();

    private JPanel pnlSourceDest = new JPanel(new GridLayout(1, 2, 5, 5));

    private JPanel pnlSource = new JPanel(new BorderLayout(5, 5));
    private JLabel lblSource = new JLabel("Source:");
    private JTextArea taSource = new JTextArea();
    private JScrollPane spSource = new JScrollPane(taSource);

    private JPanel pnlDest = new JPanel(new BorderLayout(5, 5));
    private JLabel lblDest = new JLabel("Destination:");
    private JTextArea taDest = new JTextArea();
    private JScrollPane spDest = new JScrollPane(taDest);

    private JButton btnLoadFile = new JButton("Load file");
    private JButton btnFindReplace = new JButton("Find and replace");
    private JLabel lblStatus = new JLabel("Status");
    private JLabel lblEmpty = new JLabel("Empty");

    PanelSouth pnlSouth;

    /**
     * Default constructor.
     */
    public MainPanel(Controller controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout(5, 5));

        //find & replace
        GUIUtilities.setPanelBorder(pnlFindReplace, "Find & Replace",10);
        pnlFindReplace.add(lblFind);
        pnlFindReplace.add(tfFind);
        pnlFindReplace.add(lblReplaceWith);
        pnlFindReplace.add(tfReplaceWith);

        //buttons
        pnlFindReplace.add(btnLoadFile);

        btnLoadFile.addActionListener(e -> updateSource());


        btnFindReplace.addActionListener(e -> onFindReplace());
        pnlFindReplace.add(btnFindReplace);

         add(pnlFindReplace, BorderLayout.NORTH);

        //Source and destination
        GUIUtilities.setPanelBorder(pnlSourceDest, "Editor",6);

        pnlSource.add(lblSource, BorderLayout.NORTH);
        pnlSource.add(spSource, BorderLayout.CENTER);

        pnlDest.add(lblDest, BorderLayout.NORTH);
        pnlDest.add(spDest, BorderLayout.CENTER);

        pnlSourceDest.add(pnlSource);
        pnlSourceDest.add(pnlDest);
        add(pnlSourceDest, BorderLayout.CENTER);


        pnlSouth = new PanelSouth(controller, getWidth(), (int)(0.3*height));
        //pnlSouth.addTestItems();
        add(pnlSouth, BorderLayout.SOUTH);
    }
    private void onFindReplace()
    {

        controller.execute(taSource.getText().split("\n"), tfFind.getText(), tfReplaceWith.getText());

    }


    //method that marks all the words that contain the string we want to replace
    public void markWord(String findString){
        try {
            //get the text from the source file
            String text = taSource.getText();
            int lastIndex = 0;
            //loop through the whole text to find the string we are looking for
            while(lastIndex != -1){
                lastIndex = text.indexOf(findString, lastIndex);
                if(lastIndex != -1){
                    Highlighter highlighter = taSource.getHighlighter();
                    Highlighter.HighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
                    //get the start and the end position of the string
                    int a = lastIndex;
                    int b = a + findString.length();
                    //once the string is found, highlight that string
                    highlighter.addHighlight(a, b, highlightPainter);
                    //update the lastIndex
                    lastIndex += findString.length();
                }
            }
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSourceText(List<String> lines)
    {
        taSource.removeAll();
        for (String str : lines)
            taSource.append(str + "\n");

    }
    public void updateSource()
    {
        controller.loadFileOnlyForTest();
    }

    public void setDestinationText(List<String> lines){
        taDest.setText("");
        for (String str : lines)
            taDest.append(str + "\n");
    }

    public PanelSouth getPanelSouth() {
        return pnlSouth;
    }
}


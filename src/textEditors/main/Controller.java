package textEditors.main;

import textEditors.model.*;
import textEditors.view.MainFrame;
import textEditors.view.PanelSouth;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller
{
    private List<String> sourceStrings;
    private MainFrame view;
    private PanelSouth panelSouth;
    private SharedBuffer buffer;
    private List<String> sourceLines;
    private List<String> outputLines = new ArrayList<>();

    public Controller()
    {

        view = new MainFrame(this);
        panelSouth = view.getPanelSouth();
    }

    public void execute(String[] text, String find, String replace)
    {
        // Prepare source and destination
        sourceLines = Arrays.asList(text);
        outputLines.clear();

        // Create shared bounded buffer
        buffer = new SharedBuffer(20);

        // Clear logbook (optional but recommended)
        //view.clearLogbook();

        // Highlight words in source text (GUI action → Controller → View)
        view.markWord(find);
        view.markReplacedWord(replace);

        for (int i = 0; i < 3; i++)
        {
            new Writer(sourceLines, buffer,this).start();
        }

        for (int i = 0; i < 4; i++)
        {
            new Modifier(buffer,find,replace,this).start();
        }

        new Reader(buffer,outputLines,sourceLines.size(), this).start();
    }



    public void setSourceText(List<String> lines)
    {
        view.setSourceText(lines);
    }

    public void loadFileOnlyForTest()
    {
        FileManager fileManager = new FileManager();
        sourceLines = fileManager.onLoadFile();
        setSourceText(sourceLines);

    }

    public void readerFinished()
    {
        SwingUtilities.invokeLater(() -> {
            view.setDestinationText(outputLines);
        });
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            panelSouth.setStatusText(message);
        });
    }

}

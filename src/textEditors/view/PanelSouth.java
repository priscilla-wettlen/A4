package textEditors.view;

import textEditors.main.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class PanelSouth extends JPanel
{
    private JList<String> list;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private Controller controller;
    private int width, height;

    private JLabel lblLogbook = new JLabel("Threads on duty!");

       public PanelSouth(Controller controller, int width, int height)
    {
        this.controller = controller;

        this.width = width;
        this.height = height;
        int margin = 6;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));

          // Create a JList and set its model to the DefaultListModel
        JList<String> list = new JList<>(listModel);

        JScrollPane s = GUIUtilities.setListScrollPanes(list, width+margin,height);
        GUIUtilities.setPanelBorder(this," logbook ", margin);

      // addListener();  You don't need it
        add(s,BorderLayout.NORTH);
      }

    public void updateListsBox(String [] stringList)
    {
        list.setListData(stringList);
    }
    public int getListIndex()
    {
        return list.getSelectedIndex();
    }

    public void addListener()
    {
        list.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent evt)
            {
                //int index = evt.getFirstIndex();
                int index = list.getSelectedIndex();
                if ( index > -1)
                {
                    //controller.listChanged(index);
                }
            }});
    }



//    public void addTestItems()
//    {
//        ArrayList<String> items = new ArrayList<>();
//
//        for (int i=0; i < 10; i++)
//        {
//            listModel.addElement("Thread status -  line " + i);
//        }
//        for (String item : items)
//            listModel.addElement(item);
//    }

    public void setStatusText(String infoString)
    {

       listModel.addElement(infoString);
    }
    public void clearLogbook()
    {
        listModel.clear();
    }
}

package textEditors.model;
import textEditors.main.Controller;

import java.util.List;


public class Writer extends Thread {
    private List<String> source;
    private SharedBuffer buffer;
    private static int sourceIndex = 0;
    private Controller controller;

    public Writer(List<String> source, SharedBuffer buffer, Controller controller) {
        this.source = source;
        this.buffer = buffer;
        this.controller = controller;
    }

    public void run() {
        while (true) {
            String line;

            synchronized (Writer.class) {
                if (sourceIndex >= source.size())
                    break;
                line = source.get(sourceIndex);
                sourceIndex++;
            }

            try {
                buffer.write(line);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            controller.log("Writer " + getName() + " wrote line " + line);
        }
    }

}

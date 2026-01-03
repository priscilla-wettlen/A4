package textEditors.model;

import textEditors.main.Controller;

import java.util.List;

public class Reader extends Thread {

    private final SharedBuffer buffer;
    private final List<String> output;
    private final int totalLines;
    private final Controller controller;

    public Reader(SharedBuffer buffer, List<String> output,
                  int totalLines, Controller controller) {
        this.buffer = buffer;
        this.output = output;
        this.totalLines = totalLines;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalLines; i++) {
                String line = buffer.read();
                output.add(line);
                controller.log("Reader consumed line " + i);
            }
            controller.readerFinished();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


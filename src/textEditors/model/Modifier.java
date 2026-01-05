package textEditors.model;

import textEditors.main.Controller;

public class Modifier extends Thread
{
    private final SharedBuffer buffer;
    private final String find;
    private final String replace;
    Controller controller;

    public Modifier(SharedBuffer buffer, String find, String replace, Controller controller) {
        this.buffer = buffer;
        this.find = find;
        this.replace = replace;
        this.controller = controller;
    }

            @Override
            public void run() {
                try {
                    while (true) {
                        boolean replaced = buffer.modify(find, replace);
                        if (replaced) {
                            controller.log("Modifier " + getName() + " replaced '" + find + "' with '" + replace + "'");
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
}

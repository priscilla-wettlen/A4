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
        while (true)
        {
            try {
                Thread.sleep(100);
                buffer.modify(find, replace);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            controller.log("Modifier "  + " modified line");
            controller.log("Modifier " + getName() + " changed line " + "'" + find + "' " + "with " + "'" + replace + "'");
        }
    }
//@Override
//public void run() {
//    try {
//        while (true) {
//            boolean modified = buffer.modify(find, replace);
//            if (modified) {
//                controller.log(
//                        "Modifier " + getName() + " changed line " + "'" + find + "' " + "with " + "'" + replace + "'"
//                );
//            } else {
//                break; // no more work to do
//            }
//        }
//    } catch (InterruptedException e) {
//        Thread.currentThread().interrupt();
//    }
//}
}

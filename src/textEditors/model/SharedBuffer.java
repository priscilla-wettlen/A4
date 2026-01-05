package textEditors.model;

public class SharedBuffer {

    private String[] buffer;
    private BufferStatus[] status;
    private int writePos = 0;
    private int modifyPos = 0;
    private int readPos = 0;

    public SharedBuffer(int size) {
        buffer = new String[size];
        status = new BufferStatus[size];
        for (int i = 0; i < size; i++)
            status[i] = BufferStatus.EMPTY;
    }

    public synchronized void write(String line) throws InterruptedException {
         while(status[writePos] != BufferStatus.EMPTY)
            wait();

        buffer[writePos] = line;
        status[writePos] = BufferStatus.NEW;
        writePos = (writePos + 1) % buffer.length;
        notifyAll();
    }

public synchronized boolean modify(String find, String replace)
        throws InterruptedException {

    while (status[modifyPos] != BufferStatus.NEW)
        wait();

    boolean replaced = false;

    if (buffer[modifyPos].contains(find)) {
        buffer[modifyPos] = buffer[modifyPos].replace(find, replace);
        replaced = true;
    }

    status[modifyPos] = BufferStatus.CHECKED;
    modifyPos = (modifyPos + 1) % buffer.length;
    notifyAll();

    return replaced;
}

    public synchronized String read() throws InterruptedException {
        while(status[readPos] != BufferStatus.CHECKED)
            wait();


        String readLine = buffer[readPos];
        status[readPos] = BufferStatus.EMPTY;
        readPos = (readPos + 1) % buffer.length;
        notifyAll();

        return readLine;
    }
}

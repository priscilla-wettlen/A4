package textEditors.model;

public class SharedBuffer {

    private String[] buffer;
    private BufferStatus[] status;
    private int writePos = 0;
    private int findPos = 0;
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

    public synchronized void modify(String find, String replace) throws InterruptedException {
        while(status[findPos] != BufferStatus.NEW)
            wait();

        if (buffer[findPos].contains(find))
            buffer[findPos] = buffer[findPos].replace(find, replace);

        status[findPos] = BufferStatus.CHECKED;
        findPos = (findPos + 1) % buffer.length;
        notifyAll();
    }

    public synchronized String read() throws InterruptedException {
        while(status[readPos] != BufferStatus.CHECKED)
            wait();


        String result = buffer[readPos];
        status[readPos] = BufferStatus.EMPTY;
        readPos = (readPos + 1) % buffer.length;
        notifyAll();

        return result;
    }
}

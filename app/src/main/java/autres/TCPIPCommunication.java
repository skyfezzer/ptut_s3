package autres;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;

public class TCPIPCommunication extends AsyncTask{
    private Socket socket;
    private BufferedWriter bw;

    public TCPIPCommunication(String ip) throws IOException {
        socket = new Socket(ip, 9999);
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String command) throws IOException {
            bw.write(command);
            bw.flush();
    }

    public void close() {
        try {
            bw.close();
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}

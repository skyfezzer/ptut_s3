package autres;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Created by alexi on 29/11/2017.
 */

public class TCPIPCommunication {
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

}


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class DNS {
    private HashMap<String, Integer> bankPorts = new HashMap<>();

    public DNS(int dnsPort) throws IOException {
        ServerSocket serverSocket = new ServerSocket(dnsPort);
        createThread(serverSocket);
    }

    public int getBankServerPort(String bankName) {
        for (String name : bankPorts.keySet()) {
            if (name.equals(bankName))
                return bankPorts.get(name);
        }
        return -1;
    }


    private void createThread(ServerSocket serverSocket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket clientSocket;
                while (true) {
                    try {
                        clientSocket = serverSocket.accept();
                        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                        String input = inputStream.readUTF();
                        if (input.startsWith("client ")) {
                            outputStream.writeUTF(Integer.toString(getPort(input.substring(7))));
                            outputStream.flush();
                        } else {
                            String[] bank = input.split("&");
                            bankPorts.put(bank[0], Integer.parseInt(bank[1]));
                            outputStream.writeUTF("Just ignore me!");
                            outputStream.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private int getPort(String bankName) {
        for (String name : bankPorts.keySet()) {
            if (name.equals(bankName))
                return bankPorts.get(name);
        }
        return -1;
    }


}

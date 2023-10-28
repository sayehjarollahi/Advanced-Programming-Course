
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class BankClient {
    private int bankPort;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    //private static final String PATH = "/src/";          // intellij
    private static final String PATH = "./src/main/java/";  // quera

    public BankClient(String bankName, int dnsServerPort) throws IOException {
        Socket dns = new Socket("localhost", dnsServerPort);
        DataInputStream input = new DataInputStream(new BufferedInputStream(dns.getInputStream()));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(dns.getOutputStream()));
        output.writeUTF("client " + bankName);
        output.flush();
        String bankId = input.readUTF();
        bankPort =Integer.parseInt(bankId);
        Socket bankSocket = new Socket("localhost", Integer.parseInt(bankId));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(bankSocket.getOutputStream()));
        dataInputStream = new DataInputStream(new BufferedInputStream(bankSocket.getInputStream()));
        dataOutputStream.writeUTF("new ");
        dataOutputStream.flush();
        dataInputStream.readUTF();
    }


    public synchronized void sendTransaction(int userId, int amount) {
        try {
            dataOutputStream.writeUTF(userId + " " + amount);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void sendAllTransactions(String fileName, final int timeBetweenTransactions) {
        final File file = new File(PATH + fileName);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String[] number = scanner.nextLine().split(" ");
                        dataOutputStream.writeUTF(number[0] + " " + number[1]);
                        dataOutputStream.flush();
                        dataInputStream.readUTF();
                        if (timeBetweenTransactions > 0) {
                            try {
                                sleep(timeBetweenTransactions);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}

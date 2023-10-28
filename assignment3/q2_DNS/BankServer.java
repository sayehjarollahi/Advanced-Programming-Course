

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class BankServer {
    private final HashMap<Integer, Integer> accounts = new HashMap<>();
    private int count=0;


    public BankServer(String bankName, int dnsPort) throws IOException {
         ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        Socket connection = new Socket("localhost",dnsPort);
        DataInputStream input = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
        out.writeUTF(bankName+"&"+port);
        out.flush();
        input.readUTF();
        start(serverSocket,this);
    }

    public int getBalance(int userId) {
        if (accounts.containsKey(userId))
            return accounts.get(userId);
            return 0;
    }

    private void start(ServerSocket serverSocket,BankServer bankServer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Socket client = serverSocket.accept();
                        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                       clientHandler(client,dataInputStream,dataOutputStream,bankServer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private void clientHandler (Socket client,DataInputStream dataInputStream,DataOutputStream dataOutputStream,BankServer bankServer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String input = dataInputStream.readUTF();
                        if(input.startsWith("new ")) {
                            count++;
                            dataOutputStream.writeUTF("JUST IGNORE IT!");
                            dataOutputStream.flush();
                        } else{
                            String[] number = input.split(" ");
                            int userId = Integer.parseInt(number[0]);
                            int amount = Integer.parseInt(number[1]);
                            bankServer.transaction(userId,amount);
                            dataOutputStream.writeUTF("JUST IGNORE IT");
                            dataOutputStream.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();

    }

    private void transaction(int userId,int amount){
        if(accounts.containsKey(userId)){
            if(amount>=0){
                accounts.replace(userId,accounts.get(userId)+amount);
            }else{
                if(!(accounts.get(userId)+amount<0))
                    accounts.replace(userId,accounts.get(userId)+amount);
            }
        }else{
            if(amount>=0){
                accounts.put(userId,amount);
            }
        }
    }

    private  void runTransactionThread(Socket client,String input){
       // new Thread(new Runnable() {
        //    @Override
        //    public void run() {
                DataInputStream inputStream;
                DataOutputStream outputStream;
                    try {
                         outputStream = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                         inputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                         String[] number = input.split(" ");
                         int userId = Integer.parseInt(number[0]);
                         int amount = Integer.parseInt(number[1]);
                        for (Integer id : accounts.keySet()) {
                            if(id==userId){
                                if(amount>0) {
                                    accounts.replace(id, amount + accounts.get(id));
                                }
                                else{
                                    if(accounts.get(id)>=(-1)*amount)
                                        accounts.replace(id,  accounts.get(id)+amount);
                                }
                                outputStream.writeUTF("JUST IGNORE");
                                outputStream.flush();
                                return;
                            }
                        }
                        if(amount>=0)
                            accounts.put(userId, amount);
                        outputStream.writeUTF("JUST IGNORE");
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
       // }).start();

  //  }

    public int getNumberOfConnectedClients() {
        return count;
    }
}

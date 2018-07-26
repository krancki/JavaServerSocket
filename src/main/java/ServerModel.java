import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Krzysztof on 2018-07-24.
 */
public class ServerModel {


    // View Refrence
    ServerView serverView = null;
    // Sockets
    private ServerSocket serverSocket=null;
    private Socket connection = null;
    // Streams for Socket
    InputStreamReader inputStreamReader = null;
    OutputStreamWriter outputStreamWriter = null;


    // Data from and for
    StringBuilder inputBuilder = new StringBuilder();
    StringBuilder outputBuilder = new StringBuilder();

    // Connections Thread;
    Thread thread;
    ConnectionsRun connectionsRun;

    public ServerModel(ServerView serverView)
    {
        this.serverView=serverView;
    }

    public void setServerOn()                                   // Reset configuration
    {
        try {
            serverView.setServerStatus(true);
            serverSocket = new ServerSocket(55555,100);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println(e +" ServerSocket Error");
        }
        connectionsRun = new ConnectionsRun();
        thread = new Thread(connectionsRun);
        thread.start();
    }

    public void setServerOff()
    {
        if(connectionsRun!=null) {
            connectionsRun.CloseServer();
        }
    }





      class ConnectionsRun implements Runnable  {

        // For Stop Thread
        private AtomicBoolean isWorking = new AtomicBoolean(true);

        private ConnectionsRun(){}



        @Override
         public void run() {
            while(isWorking.get()) {
                try {

                    System.out.println("\n\nCzekam na połaczenie...");
                    connection = serverSocket.accept();

                    System.out.println(connection.getInetAddress().getHostAddress());
                    System.out.println(connection.getPort());

                    System.out.println("Połaczono");
                    OpenStreams();
                    System.out.println("Strumienia sa gotowe");
                    System.out.println("Wczytywanie...");
                    ReadRequest();
                    System.out.println("Wysyłanie...");
                    WriteResponse();
                    System.out.println("Zamykania Połaczenia");
                    CloseStreams();

                }catch (IOException e)
                {
                    System.out.println(e + "IOException in creating connection");
                }

            }
        }

        private void OpenStreams()
        {
            try {

                inputStreamReader = new InputStreamReader(connection.getInputStream());
                outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());

            } catch (Exception e) {
             System.out.println("Open Connection error");
            }

        }

        private void CloseStreams()
        {

                try {

                    inputStreamReader.close();
                    outputStreamWriter.close();



                } catch (Exception e) {
                    System.out.println("Close Connection error");
                }

        }

        private void CloseServer()
        {
            try {
                connectionsRun.isWorking.set(false);
                serverSocket.close();
                serverView.setServerStatus(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    private void ReadRequest()
    {

        char [] buffor = new char[4096];
        try {


                inputStreamReader.read(buffor);

                inputBuilder.append(buffor);

                serverView.setInputView(inputBuilder.toString());  // Send to View

        } catch (IOException e) {
            System.out.println(e +" IOException in reading stream");
        }


    }

    private void WriteResponse()
    {

        try {
            outputBuilder = new StringBuilder("");
            outputBuilder.append("HTTP/1.1 200 OK\nContent-Type: text/html\r\n\n");
            char [] buffor = new char[4096];
            new FileReader(new File("F:/WORK/ServerApplication/src/main/webapp/index.jsp")).read(buffor) ;
            outputBuilder.append(buffor);
            outputStreamWriter.write(outputBuilder.toString());
            outputStreamWriter.flush();

            serverView.setOutputView(outputBuilder.toString());   // Send to View

        } catch (IOException e) {
            System.out.println(e +" IOException in writing stream");
        }

    }










}

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ChatWorkerThread extends Thread
{
    private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput;

    public ChatWorkerThread(Socket theClientSocket)
    {
        try 
        {
            System.out.println("Connection Established...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());    
            //System.out.println("About to add a printstream");
            CORE.addClientThreadPrintStream(this.clientOutput);

            this.clientInput = new Scanner(this.theClientSocket.getInputStream());
        } 
        catch (Exception e) 
        {
            System.err.println("Bad things happened in thread!!!!!");
            e.printStackTrace();
        }
        
    }

    public void run()
    {
        try
        {
            //this is what the thread does
            this.clientOutput.println("What is your name?");
            String name = clientInput.nextLine();
            CORE.broadcastMessage(name + " has joined!");
            
            String message;
            while(true)
            {
                message = clientInput.nextLine();
                if(message.equals("/quit"))
                {
                    CORE.broadcastMessage(name + " has left the server!");
                    CORE.removeClientThreadPrintStream(this.clientOutput);
                    break;
                }
                else if(message.equals("/upload"))
                {
                    this.clientOutput.println("Enter filepath");
                    String fPath = clientInput.nextLine();
                    CORE.fileSend(fPath);
                    this.clientOutput.println("Uploaded");
                }
                else if(message.equals("/download"))
                {
                    this.clientOutput.println("Enter filepath to download");
                    String fPath = clientInput.nextLine();
                    File newFile = new File(fPath);
                    FileOutputStream userData = new FileOutputStream(newFile);
                    byte[] file = CORE.fileRecieve();
                    userData.write(file);
                    this.clientOutput.println("Downloaded");
                }

                CORE.broadcastMessage(message);
            }
        }
        catch (Exception e)
        {
            System.out.println("Not happening chief");
        }

    }
    
}
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;


public class CORE 
{
    private static ArrayList<PrintStream> theClientStreams = new ArrayList<PrintStream>();
    
    private static byte[] file;

    public static synchronized void addClientThreadPrintStream(PrintStream ps)
    {
        System.out.println("adding client thread");
        CORE.theClientStreams.add(ps);
    }

    public static synchronized void removeClientThreadPrintStream(PrintStream ps)
    {
        CORE.theClientStreams.remove(ps);
    }
    
    public static void broadcastMessage(String message)
    {
        System.out.println("About to broadcast....");
        for (PrintStream ps : CORE.theClientStreams)
        {
            ps.println(message);
        }
    }

    public static void fileSend (String fPath)
    {
        try
        {
            FileInputStream fInput = new FileInputStream(fPath);
            fInput.read(file);
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }
    }

    public static byte[] fileRecieve()
    {
        return file;
    }


}
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class chatClient 
{
    public static void main(String[] args) throws Exception
    {


        Socket s = new Socket("localhost", 2222); 
        Scanner clientInput = new Scanner(s.getInputStream());
        String question = clientInput.nextLine();
        System.out.println(question);
        Scanner localInput = new Scanner(System.in);
        PrintStream clientOutput = new PrintStream(s.getOutputStream());
        clientOutput.println(localInput.nextLine());

        do
        {
            if (clientInput.equals("empty"))
            {
                
                System.out.println("What is your name?");
                localInput = new Scanner(System.in);
                clientOutput = new PrintStream(s.getOutputStream());
                clientOutput.println(localInput.nextLine());
                //question = clientInput.nextLine();
                System.out.println(question);
                if (question.equals("exit"))
                {
                    break;
                }
            }
            else
            {
                String chat = ( "message: " );
                System.out.println(chat);
                localInput = new Scanner(System.in);
                clientOutput = new PrintStream(s.getOutputStream());
                clientOutput.println(localInput.nextLine());
                question = clientInput.nextLine();
                System.out.println(question);
                //System.out.println(chat + " " + question);
                if(question.equals("exit"))
                {
                    break;
                }
            }
            
        } while (!question.equals("exit"));

    }
}
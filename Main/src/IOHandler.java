import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOHandler
{

    // Constructor
    public IOHandler() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String name = reader.readLine();


        System.out.println(name);
    }






}

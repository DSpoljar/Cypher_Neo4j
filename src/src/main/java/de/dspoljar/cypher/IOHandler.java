package de.dspoljar.cypher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOHandler
{

    protected String query;

    // Constructor

    public IOHandler() throws IOException
    {

        /*
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String name = reader.readLine();

        System.out.print(name);

         */




    }

    public String inputReader() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String name = reader.readLine();

        System.out.print(name);

        return name;
    }






}

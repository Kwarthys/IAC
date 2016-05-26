package com.inc.pwal.iac;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Download
{
    public static String getFile(String host)
    {
        InputStream input = null;
        FileOutputStream writeFile = null;
        String fileName ="";
        try
        {
            URL url = new URL(host);
            URLConnection connection = url.openConnection();
            int fileLength = connection.getContentLength();

            if (fileLength == -1)
            {
                System.out.println("Invalid URL or file.");
                return fileName;
            }

            input = connection.getInputStream();
            fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            writeFile = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int read;

            while ((read = input.read(buffer)) > 0)
                writeFile.write(buffer, 0, read);
            writeFile.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error while trying to download the file.");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writeFile.close();
                input.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return fileName;
    }
/*
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("You must give the URL of the file to download.");
            return;
        }

        getFile(args[0]);
    }*/
}
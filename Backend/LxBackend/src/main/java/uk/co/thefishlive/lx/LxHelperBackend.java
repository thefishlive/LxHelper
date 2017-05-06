package uk.co.thefishlive.lx;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uk.co.thefishlive.lx.data.VectorworksFileReader;
import uk.co.thefishlive.lx.data.ShowData;

public class LxHelperBackend
{
    private static final Logger logger = LogManager.getLogger(LxHelperBackend.class);

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.out.printf("Requires argument specifying path to vectorworks file.%n");
            return;
        }


        VectorworksFileReader reader = new VectorworksFileReader();
        ShowData data = reader.readFile(new File(args[0]));

        int port = 8080;
        logger.info("Launching web server on port {}", port);
        HttpServer server = new HttpServer(port);
        server.start(data);

        System.out.println("Started web server");
    }
}

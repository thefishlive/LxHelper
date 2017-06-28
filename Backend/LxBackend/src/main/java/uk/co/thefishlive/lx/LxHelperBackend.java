package uk.co.thefishlive.lx;

import java.io.File;
import java.io.IOException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
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

    public static void main(String[] args) throws Exception
    {
        if (args.length != 3)
        {
            System.out.printf("Usage: <data file> <key chain> <private key>%n");
            return;
        }

        VectorworksFileReader reader = new VectorworksFileReader();
        ShowData data = reader.readFile(new File(args[0]));

        File keyChain = new File(args[1]);
        File privateKey = new File(args[2]);

        if (!keyChain.exists())
        {
            System.err.printf("Key chain (%s) does not exist %n", keyChain.getAbsolutePath());
            return;
        }

        if (!privateKey.exists())
        {
            System.err.printf("Private key (%s) does not exist %n", privateKey.getAbsolutePath());
            return;
        }

        final SslContext ssl = SslContextBuilder.forServer(keyChain, privateKey).build();

        int port = 8080;
        logger.info("Launching web server on port {}", port);
        HttpServer server = new HttpServer(port, ssl);
        server.start(data);

        System.out.println("Started web server");
    }
}

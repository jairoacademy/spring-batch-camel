package academy.jairo.springboot.camel.demo.file;

import academy.jairo.springboot.camel.demo.config.SftpProperties;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Log4j2
public class FileProcessor implements Processor {

    private final SftpProperties properties;

    public FileProcessor(SftpProperties sftpProperties) {
        this.properties = sftpProperties;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("-> .process | FileProcessor.process | in body:{}", exchange.getIn().getBody());
        File file = exchange.getIn().getBody(File.class);
        String filePath = properties.getRoutePath() + properties.getRouteOutput() + File.separator  + file.getName();
        sendToFTPUpload(file.getName(), filePath);
    }

    public void sendToFTPUpload(String fileName, String filePath) {
        FTPClient client = new FTPClient();
        FileInputStream fileInputStream = null;
        try {
            client.connect("10.32.4.84");
            client.login("jairo", "adm");

            fileInputStream = new FileInputStream(filePath);
            client.storeFile(fileName, fileInputStream);
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           try {
               if (fileInputStream != null) {
                   fileInputStream.close();
               }
               client.disconnect();
           } catch (IOException e) {
               e.printStackTrace();
           }
        }
    }

    /*
    Send file to sftp server
     */
    private void sendSFTPUpload(String filePath) {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(
                    properties.getUser(),
                    properties.getHost(),
                    Integer.valueOf((properties.getPort())));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(properties.getPass());
            log.info("-> Connecting");
            session.connect();
            log.info("-> Open channel");
            Channel sftp = session.openChannel("sftp");
            ChannelSftp channelSftp = (ChannelSftp) sftp;

            log.info("-> Connecting to channel");
            channelSftp.connect();
            channelSftp.put(filePath , properties.getPath());
            channelSftp.disconnect();

            session.disconnect();
            log.info("-> Disconnected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

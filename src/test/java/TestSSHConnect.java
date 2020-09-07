import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestSSHConnect {
    public static void main(String[] arg) {
        try {
            JSch jsch = new JSch();

            String user = "yakorotyshov";
            String host = "log-4talk.ru.mgo.su";
            int port = 22;
            String privateKey = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\korotyshov4talk_private.PPK";
            String command = "zgrep 401496806 /var/log/remote/4talk-prod-front-node*.ru.mgo.su/2020/08/31/*/node.log.gz | grep body | grep REQ";
            jsch.addIdentity(privateKey);
            System.out.println("identity added ");
            Session session = jsch.getSession(user, host, port);
            System.out.println("session created.\n");

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String str;

            while ((str = reader.readLine()) != null){
                System.out.println(str);
            }

            session.disconnect();
            channel.disconnect();
            System.out.println("\nFinish");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
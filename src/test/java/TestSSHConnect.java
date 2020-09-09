import com.jcraft.jsch.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestSSHConnect {
    public static void main(String[] arg) {
        String[] abonets = new String[]{"401497023", "402158256", "402158259", "402158265", "402158271", "402158280", "402158302", "401497049", "402158225", "401496711"};
        String abonentId = abonets[0];

        for(int i = 1; i < 10; i++){
            String command = String.format("zgrep %s /var/log/remote/4talk-prod-front-node*.ru.mgo.su/2020/08/0%s/*/node.log.gz | grep body | grep REQ", abonentId, i);
            String file_path = String.format("D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\%s_%s.txt", abonentId, i);
            assertTrue(new File(file_path).exists());
            writeToFile(file_path, serverRequest(command));
        }

        for(int i = 10; i < 32; i++){
            String command = String.format("zgrep %s /var/log/remote/4talk-prod-front-node*.ru.mgo.su/2020/08/%s/*/node.log.gz | grep body | grep REQ", abonentId, i);
            String file_path = String.format("D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\%s_%s.txt", abonentId, i);
            assertTrue(new File(file_path).exists());
            writeToFile(file_path, serverRequest(command));
        }

    }

    private static void writeToFile(String file_path, List<String> logs) {
        try {
            PrintWriter writer = new PrintWriter(file_path, "UTF-8");
            logs.forEach(writer::println);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<String> serverRequest(String command) {
        try {
            JSch jsch = new JSch();

            String user = "yakorotyshov";
            String host = "log-4talk.ru.mgo.su";
            int port = 22;
            String privateKey = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\korotyshov4talk_private.PPK";
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
            List<String> logs = new ArrayList<>();
            while ((str = reader.readLine()) != null){
                logs.add(str);
            }

            session.disconnect();
            channel.disconnect();
            System.out.println("\nFinish");
            return logs;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import ru.lanwen.verbalregex.VerbalExpression;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesGeter {

    public static String log_file_folder = "D:\\IdeaProjects\\Sshloger\\src\\main\\resources\\LogFile\\";
    public static String parse_file_folder = "D:\\IdeaProjects\\Sshloger\\src\\main\\resources\\ParseData\\";
    public static Map<String, String> abonentid_users_target = new HashMap<>();
    public static Map<String, String> abonentid_users_all;
    public static String result;

    public static void main(String[] args) throws Exception {

        abonentid_users_target.put("401496806", "Карпенко Елена Сергеевна");
        abonentid_users_target.put("402158248", "Карпенко Елена Сергеевна");
        abonentid_users_target.put("401496999", "Могильная Светлана Григорьевна");
        abonentid_users_target.put("402158276", "Могильная Светлана Григорьевна");
        abonentid_users_target.put("401497125", "Гусейнов Шамиль Саидович");
        abonentid_users_target.put("402158230", "Гусейнов Шамиль Саидович");
        abonentid_users_target.put("401824502", "Богачева Елена Владимировна");
        abonentid_users_target.put("402158213", "Богачева Елена Владимировна");
        abonentid_users_target.put("402158235", "Диденко Егор Анатольевич");
        abonentid_users_target.put("401496762", "Диденко Егор Анатольевич");
        abonentid_users_target.put("401497059", "Карпюк Анна Владимировна");
        abonentid_users_target.put("402158249", "Карпюк Анна Владимировна");
        abonentid_users_target.put("401497024", "Колемачкина Александра Сергеевна");
        abonentid_users_target.put("402158252", "Колемачкина Александра Сергеевна");
        abonentid_users_target.put("401497023", "Костюков Евгений Вячеславович");
        abonentid_users_target.put("402158255", "Костюков Евгений Вячеславович");
        abonentid_users_target.put("401497022", "Кромин Александр Сергеевич");
        abonentid_users_target.put("402158256", "Кромин Александр Сергеевич");
        abonentid_users_target.put("402158259", "Кузнецова Наталья Сергеевна");
        abonentid_users_target.put("401496712", "Кузнецова Наталья Сергеевна");
        abonentid_users_target.put("401497021", "Левшина Дарья Андреевна");
        abonentid_users_target.put("402158265", "Левшина Дарья Андреевна");
        abonentid_users_target.put("402158271", "Мацнев Сергей Витальевич");
        abonentid_users_target.put("401496903", "Мацнев Сергей Витальевич");
        abonentid_users_target.put("401497020", "Просецкая Алевтина Андреевна");
        abonentid_users_target.put("402158280", "Просецкая Алевтина Андреевна");
        abonentid_users_target.put("402158302", "Шалыгина Юлия Романовна");
        abonentid_users_target.put("401497018", "Шалыгина Юлия Романовна");
        abonentid_users_target.put("401497049", "Трошена Елена Ивановна");
        abonentid_users_target.put("402158292", "Трошена Елена Ивановна");
        abonentid_users_target.put("401496776", "Гоберман Светлана Евгеньевна");
        abonentid_users_target.put("402158225", "Гоберман Светлана Евгеньевна");
        abonentid_users_target.put("401496711", "Вуцан Светлана Георгиевна");
        abonentid_users_target.put("402158221", "Вуцан Светлана Георгиевна");
        abonentid_users_target.put("402158253", "Конденко Марина Владимировна");
        abonentid_users_target.put("401497028", "Конденко Марина Владимировна");
        abonentid_users_target.put("402158295", "Фонарев Василий Васильевич");
        abonentid_users_target.put("401496682", "Фонарев Василий Васильевич");
        abonentid_users_target.put("402158242", "Исаев Сергей Курманаевич");
        abonentid_users_target.put("401496672", "Исаев Сергей Курманаевич");

        abonentid_users_all = mapperCsv();

        for (Map.Entry<String, String> abonentid : abonentid_users_target.entrySet()) {
            for(int i = 1; i < 10; i++){
                String file_path = log_file_folder + String.format("%s_%s.txt", abonentid.getKey(), i);
                String command = String.format("zgrep %s /var/log/remote/4talk-prod-front-node*.ru.mgo.su/2020/08/0%s/*/node.log.gz | grep body | grep REQ", abonentid.getKey(), i);
                writeToFile(file_path, serverRequest(command));
                readerFile(file_path, String.format("%s_%s.txt", abonentid.getKey(), i));
            }
            for(int i = 10; i < 32; i++){
                String file_path = log_file_folder + String.format("%s_%s.txt", abonentid.getKey(), i);
                String command = String.format("zgrep %s /var/log/remote/4talk-prod-front-node*.ru.mgo.su/2020/08/%s/*/node.log.gz | grep body | grep REQ", abonentid.getKey(), i);
                writeToFile(file_path, serverRequest(command));
                readerFile(file_path, String.format("%s_%s.txt", abonentid.getKey(), i));
            }
        }


        String file_path = log_file_folder + "401496806_1.txt";
        String command = "zgrep 401496806 /var/log/remote/4talk-prod-front-node*.ru.mgo.su/2020/08/31/*/node.log.gz | grep body | grep REQ";
        writeToFile(file_path, serverRequest(command));
        readerFile(file_path, "401496806_1.txt");


    }

    private static void writeToFile(String file_path, List<String> logs) {
        try {
            PrintWriter writer = new PrintWriter(file_path, "UTF-8");
            logs.forEach(l -> {
                try {
                    writer.println(new String(l.getBytes(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> serverRequest(String command) {
        try {
            JSch jsch = new JSch();

            String user = "yakorotyshov";
            String host = "log-4talk.ru.mgo.su";
            int port = 22;
            String privateKey = "D:\\IdeaProjects\\Sshloger\\src\\main\\resources\\korotyshov4talk_private.PPK";
            jsch.addIdentity(privateKey);
            System.out.println("identity added ");
            Session session = jsch.getSession(user, host, port);
            System.out.println("session created");
            System.out.println("Grep in progress...");

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
            while ((str = reader.readLine()) != null) {
                logs.add(str);
            }

            session.disconnect();
            channel.disconnect();
            System.out.println("Finish");
            return logs;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readerFile(String fileName, String file_path) throws Exception {
        Path path = Paths.get(fileName);
        List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        list.forEach(l -> regexLog(l, file_path));
        result = "";
    }

    public static void regexLog(String log, String file_path) {
        try {
            VerbalExpression body = VerbalExpression.regex()
                    .capt().find("body").nonWordChar().oneOrMore().find("\"}").endCapt().build();

            String message = body.getText(log).split(":")[1]
                    .replaceAll("\"", "")
                    .replaceAll("}", "");

            //System.out.print(new String(String.format("Message '%s' ", message).getBytes(), "windows-1251"));
            System.out.print(String.format("Message '%s' ", message));
            result += String.format("Message '%s' ", message);

            VerbalExpression time = VerbalExpression.regex()
                    .capt().range("0", "9").count(2).maybe(":").range("0", "9").count(2).maybe(":").range("0", "9").count(2).space().endCapt().build();
            System.out.print("get in " + time.getText(log).replaceAll(" ", ""));
            result += "get in " + time.getText(log).replaceAll(" ", "");

            VerbalExpression users_reg = VerbalExpression.regex()
                    .capt().nonWordChar().nonSpace().digit().oneOrMore().find("@").endCapt().build();

            try {
                String[] users = users_reg.getText(log).replaceAll("@", "").split(":\"");
                System.out.println(String.format(" from employee '%s' to '%s'", abonentid_users_all.get(users[0].replaceAll(" ", "")), abonentid_users_all.get(users[1])));
                result += String.format(" from employee '%s' to '%s'", abonentid_users_all.get(users[0].replaceAll(" ", "")), abonentid_users_all.get(users[1])) + "\n";
            } catch (ArrayIndexOutOfBoundsException e) {
                //System.out.println(String.format("\nDont split reg with USER\n%s\n", users_reg.getText(log)));
                //System.out.println("SKIP - Group or channel message");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println(String.format("\nDont split reg with BODY\n%s\n", body.getText(log)));
            //System.out.println("SKIP - Body is empty");
        }

        try {
            PrintWriter writer = new PrintWriter(parse_file_folder + file_path, "UTF-8");
            if(result != null && !result.isEmpty()) {
                writer.println(result);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> mapperCsv() throws Exception {
        Path path = Paths.get("D:\\IdeaProjects\\Sshloger\\src\\main\\resources\\export2_csv.csv");
        Map<String, String> abonentId_user = new HashMap<>();
        List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        list.forEach(l -> abonentId_user.put(l.split(";")[0], l.split(";")[1]));
        return abonentId_user;
    }
}

import com.sun.javafx.binding.StringFormatter;
import org.junit.Test;
import ru.lanwen.verbalregex.VerbalExpression;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public String result = "";

    @Test
    public void testMain() throws Exception {
        // 401497023_1

            System.out.println("Log file 401497023_3:");
            result += "Log file 401497023_3:\n";
            String fileName = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\401497023_3.txt";
            readerFile(fileName);

    }

    public void testRegLog(String log, Map<String, String> mapperCsv) {
        VerbalExpression body = VerbalExpression.regex()
                .capt().find("body").nonWordChar().oneOrMore().find("\"}").endCapt().build();
        try {
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
                System.out.println(String.format(" from employee '%s' to '%s'", mapperCsv.get(users[0].replaceAll(" ", "")), mapperCsv.get(users[1])));
                result += String.format(" from employee '%s' to '%s'", mapperCsv.get(users[0].replaceAll(" ", "")), mapperCsv.get(users[1])) + "\n";
            } catch (ArrayIndexOutOfBoundsException e) {
                //System.out.println(String.format("\nDont split reg with USER\n%s\n", users_reg.getText(log)));
                //System.out.println("SKIP - Group or channel message");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println(String.format("\nDont split reg with BODY\n%s\n", body.getText(log)));
            //System.out.println("SKIP - Body is empty");
        }

        try {
            String file_path = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\testDecoder.txt";
            PrintWriter writer = new PrintWriter(file_path, "UTF-8");
            writer.println(result + "\n");
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void readerFile(String fileName) throws Exception {
        //String fileName = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\test.txt"; //resources/401496806_2020_08_31.txt
        Path path = Paths.get(fileName);
        //List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        Map<String, String> abonentId_user = mapperCsv();
        list.forEach(l -> testRegLog(l, abonentId_user));
    }

    public Map<String, String> mapperCsv() throws Exception {
        Path path = Paths.get("D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\export2_csv.csv");
        Map<String, String> abonentId_user = new HashMap<>();
        List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        list.forEach(l -> abonentId_user.put(l.split(";")[0], l.split(";")[1]));
        return abonentId_user;
    }
}

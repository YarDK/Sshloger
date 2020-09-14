import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestDecoder {

    public void testDecoder(String text) throws Exception {
        Charset cset = Charset.forName("windows-1251");
        ByteBuffer buf = cset.encode(text);
        byte[] b = buf.array();
        String str = new String(b);
        System.out.println(str);
    }

    @Test
    public void testWritDecode() {
        String file_path = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\testDecoder.txt";
        String text = "005952126-СЂР°СЃРєРёРґР°Р№ Р°РєС‚. СЃС‡РµС‚ СЏ РІС‹СЃС‚Р°РІРёР»Р° РѕРґРЅРѕР№ СЃС‚СЂРѕРєРѕР№";
        try {
            PrintWriter writer = new PrintWriter(file_path, "UTF-8");
            writer.println(text + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReaderFilePath() throws IOException {
        String file_path = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\testDecoder.txt";
        Path path = Paths.get(file_path);
        List<String> list = Files.readAllLines(path);
        String test = list.get(5);
        char[] test_as_char = test.toCharArray();
        for (char x : test_as_char) {
            System.out.println();
        }
    }

    @Test
    public void testReaderFileReader() throws IOException {
        String file_path = "D:\\IdeaProjects\\Sshloger\\src\\test\\resources\\data\\testDecoder2.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), "Windows-1251"));
        String result = br.readLine();
        System.out.println("Result:\n" + result);
    }

}

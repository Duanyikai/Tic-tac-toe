import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class test {
    public static void main(String[] args) throws IOException {
        Socket t = new Socket("localhost",5000);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(t.getOutputStream()));
        while(true){
            bw.write("sbsb");
        }

    }
}

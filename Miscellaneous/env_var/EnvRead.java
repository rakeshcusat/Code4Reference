import java.util.Map;

public class EnvRead {
    public static void main (String[] args) {
        Map<String, String> env = System.getenv();
         System.out.println(env.get("HOME"));

    }
}


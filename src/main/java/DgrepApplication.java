import java.util.Scanner;

public class DgrepApplication {

    private static final Scanner SCANNER = new Scanner(System.in);


    public static void main(String[] args) {
        while (true) {
            String input = getCommand();
            if (input.equals("exit")) {
                break;
            }
            String[] parts = input.split("\\s+");
            if (parts.length != 3 || !parts[0].equals("dgrep")) {
                System.out.println("invalid input");
                continue;
            }
            String keyword = parts[1];
            String relativePath = parts[2];
        }

    }

    private static String getCommand() {
        System.out.println("enter command like(dgrep {keyword} {relativePath}) or exit to quit: ");
        return SCANNER.nextLine().trim();
    }
}

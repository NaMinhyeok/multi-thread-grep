import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            Path path = Paths.get(relativePath);
            if(!Files.isDirectory(path)) {
                try(BufferedReader reader = Files.newBufferedReader(path)) {
                    List<String> lines = reader.lines().toList();
                    for(int i = 0; i<lines.size(); i++) {
                        if(lines.get(i).contains(keyword)) {
                            System.out.println("file: " + path.getFileName() + " line: " + (i+1));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static String getCommand() {
        System.out.println("enter command like(dgrep {keyword} {relativePath}) or exit to quit: ");
        return SCANNER.nextLine().trim();
    }
}

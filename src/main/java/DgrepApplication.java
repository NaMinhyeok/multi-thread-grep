import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
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
            try (Stream<Path> walk = Files.walk(path)) {
                walk.filter(Files::isRegularFile)
                    .parallel()
                    .map(file -> extractLineNumber(file, keyword))
                    .filter(result -> !result.isEmpty())
                    .forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private static String extractLineNumber(Path path, String keyword) {
        StringBuilder result = new StringBuilder();
        try (Stream<String> lines = Files.lines(path)) {
            AtomicInteger lineNumber = new AtomicInteger(1);
            lines.forEach(
                line -> {
                    if(line.contains(keyword)) {
                        result.append(String.format("file: %s line: %d%n", path.getFileName(), lineNumber.get()));
                    }
                    lineNumber.incrementAndGet();
                }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static String getCommand() {
        System.out.println("enter command like(dgrep {keyword} {relativePath}) or exit to quit: ");
        return SCANNER.nextLine().trim();
    }
}

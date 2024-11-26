package log;

import java.io.*;
import java.nio.file.*;

public class LogFile {
    private static final String TEMP_LOG_PATH = "C:\\myfiles\\temp_droid_log.txt";
    private BufferedWriter writer;

    public LogFile() {
        try {
            Files.createDirectories(Paths.get("C:\\myfiles"));
            if (Files.exists(Paths.get(TEMP_LOG_PATH))) {
                Files.write(Paths.get(TEMP_LOG_PATH), new byte[0]);
            }
            writer = new BufferedWriter(new FileWriter(TEMP_LOG_PATH, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askForSaving() {
        System.out.println("Бажаєте зберегти лог-файл? (y/n): ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String response = reader.readLine();
            if ("y".equalsIgnoreCase(response)) {
                saveLogToUserDefinedPath();
            } else { Files.delete(Paths.get(TEMP_LOG_PATH)); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLogToUserDefinedPath() {
        System.out.println("Введіть шлях для збереження лог-файлу: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String userPath = reader.readLine();
            Path destination = Paths.get(userPath);
            Files.copy(Paths.get(TEMP_LOG_PATH), destination, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(Paths.get(TEMP_LOG_PATH));
            System.out.println("Лог-файл успішно збережено за шляхом: " + userPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
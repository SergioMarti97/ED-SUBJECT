package race;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileUtils {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String path = "C:\\Users\\Sergio\\IdeaProjects\\ED-SUBJECT\\ED-TourDeFrance\\";

    private static final String fileName = "stats.txt";

    private static ArrayList<CyclingStage> loadStats(String fileName) {
        ArrayList<CyclingStage> data = new ArrayList<>();

        try ( FileReader fileReader = new FileReader(path + fileName) ) {

            BufferedReader bf = new BufferedReader(fileReader);
            String line = bf.readLine();

            do {
                String[] info = line.split(";");
                data.add(
                        new CyclingStage(
                                LocalDate.parse(info[0], dateFormat),
                                Float.parseFloat(info[1]),
                                info[2])
                );
                line = bf.readLine();
            } while ( line != null );

        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ArrayList<CyclingStage> loadStats() {
        return loadStats(fileName);
    }

    public static void saveStats(ArrayList<CyclingStage> data, String fileName) {
        try (PrintWriter printWriter = new PrintWriter(path + fileName)) {
            String line;
            for ( CyclingStage c : data ) {
                line = c.getDate().format(dateFormat) + ";" + c.getKm() + ";" + c.getWinner();
                printWriter.println(line);
            }
        } catch ( FileNotFoundException e ) {
            System.out.println("File " + fileName + " not found");
        }
    }

    public static void saveStats(ArrayList<CyclingStage> data) {
        saveStats(data, fileName);
    }

}

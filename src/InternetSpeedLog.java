
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InternetSpeedLog {
    private InternetSpeed[] items = new InternetSpeed[30];
    private int count = 0;
    private Path filePath = Paths.get("internet_speed.txt");

    public boolean addRecord(InternetSpeed item) {
        if (item == null || item.getDate() == null
                || item.getPlace() == null) {
            System.out.println("Жазба деректері дұрыс емес!");
            return false;
        }

        if (count >= items.length) {
            System.out.println("Журнал толып қалды! Ең көбі 30 жазба.");
            return false;
        }

        items[count] = item;
        count++;
        System.out.println("Жазба сәтті қосылды!");
        return true;
    }

    public int getCount() {
        return count;
    }

    public InternetSpeed getRecord(int index) {
        if (index < 0 || index >= count) {
            return null;
        }
        return items[index];
    }

    public void printAll() {
        if (count == 0) {
            System.out.println("Әзірге жазбалар жоқ.");
            return;
        }

        int i = 0;
        while (i < count) {
            System.out.println("\n--- Жазба " + (i + 1) + " ---");
            items[i].printInfo();
            i++;
        }
    }

    public double getAverageUploadMbps() {
        if (count == 0) {
            return 0;
        }

        double total = 0;

        for (int i = 0; i < count; i++) {
            total += items[i].getUploadMbps();
        }

        return total / count;
    }

    public double getAveragePing() {
        if (count == 0) {
            return 0;
        }

        double total = 0;

        for (int i = 0; i < count; i++) {
            total += items[i].getPing();
        }

        return total / count;
    }



    public void printByCategory(String place) {
        boolean found = false;
        int i = 0;

        while (i < count) {
            if (items[i].getPlace().equalsIgnoreCase(place.trim())) {
                items[i].printInfo();
                System.out.println("--------------------");
                found = true;
            }
            i++;
        }

        if (!found) {
            System.out.println("Бұл орын бойынша жазбалар табылмады.");
        }
    }

    public boolean updateRecord(int index, InternetSpeed newItem) {
        if (index < 0 || index >= count || newItem == null
                || newItem.getDate() == null
                || newItem.getPlace() == null) {
            System.out.println("Жазбаны өзгерту мүмкін емес.");
            return false;
        }

        items[index] = newItem;
        System.out.println("Жазба өзгертілді.");
        return true;
    }

    public boolean deleteRecord(int index) {
        if (index < 0 || index >= count) {
            System.out.println("Мұндай жазба нөмірі жоқ.");
            return false;
        }

        int i = index;
        while (i < count - 1) {
            items[i] = items[i + 1];
            i++;
        }

        count--;
        items[count] = null;
        System.out.println("Жазба өшірілді.");
        return true;
    }

    public int getTotalMbps() {
        int total = 0;
        int i = 0;

        while (i < count) {
            total += items[i].getMbps();
            i++;
        }

        return total;
    }

    public double getAverageMbps() {
        if (count == 0) {
            return 0;
        }
        return (double) getTotalMbps() / count;
    }

    public InternetSpeed findMax() {
        if (count == 0) {
            return null;
        }

        InternetSpeed max = items[0];
        int i = 1;

        while (i < count) {
            if (items[i].getMbps() > max.getMbps()) {
                max = items[i];
            }
            i++;
        }

        return max;
    }

    public InternetSpeed findMin() {
        if (count == 0) {
            return null;
        }

        InternetSpeed min = items[0];
        int i = 1;

        while (i < count) {
            if (items[i].getMbps() < min.getMbps()) {
                min = items[i];
            }
            i++;
        }

        return min;
    }

    public int countSlow() {
        int total = 0;
        int i = 0;

        while (i < count) {
            if (items[i].isSlow()) {
                total++;
            }
            i++;
        }

        return total;
    }


    public void printStatistics() {
        if (count == 0) {
            System.out.println("Статистика үшін жазбалар жоқ.");
            return;
        }

        System.out.println("Жазбалар саны: " + count);

        System.out.println("Жүктеу жылдамдығының қосындысы: "
                + getTotalMbps() + " Мбит/с");

        System.out.printf(
                "Орташа жүктеу жылдамдығы: %.2f Мбит/с%n",
                getAverageMbps()
        );

        System.out.printf(
                "Орташа жіберу жылдамдығы: %.2f Мбит/с%n",
                getAverageUploadMbps()
        );

        System.out.printf(
                "Орташа Ping: %.2f мс%n",
                getAveragePing()
        );

        InternetSpeed max = findMax();
        InternetSpeed min = findMin();

        System.out.println("\nЕң жоғары жылдамдық:");
        max.printInfo();

        System.out.println("\nЕң төмен жылдамдық:");
        min.printInfo();

        System.out.println("\nБаяу интернет жағдайлары: " + countSlow());
    }



    public void printChart() {
        if (count == 0) {
            System.out.println("Графикке арналған жазбалар жоқ.");
            return;
        }

        System.out.println("\n=== Жүктеу жылдамдығының графигі ===");

        int i = 0;
        while (i < count) {
            int stars = items[i].getMbps() / 2;

            if (items[i].getMbps() > 0 && stars == 0) {
                stars = 1;
            }

            System.out.printf("%-15s %4d Мбит/с | ",
                    items[i].getPlace(), items[i].getMbps());

            int j = 0;
            while (j < stars) {
                System.out.print("*");
                j++;
            }

            System.out.println();
            i++;
        }

        System.out.println("* шамамен 2 Мбит/с-қа тең.");
    }

    public boolean exportCSV() {
        Path csvPath = Paths.get("internet_speed_export.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(
                csvPath, StandardCharsets.UTF_8)) {

            writer.write("Date,Place,DownloadMbps,UploadMbps,PingMs");
            writer.newLine();

            int i = 0;
            while (i < count) {
                InternetSpeed s = items[i];

                writer.write(csv(s.getDate()) + ","
                        + csv(s.getPlace()) + ","
                        + s.getMbps() + ","
                        + s.getUploadMbps() + ","
                        + s.getPing());
                writer.newLine();
                i++;
            }

            System.out.println("CSV файлы жасалды: "
                    + csvPath.toAbsolutePath());
            return true;

        } catch (IOException e) {
            System.out.println("CSV экспорт қатесі: " + e.getMessage());
            return false;
        }
    }

    private String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    public boolean saveToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardCharsets.UTF_8)) {

            int i = 0;
            while (i < count) {
                InternetSpeed s = items[i];

                writer.write(s.getDate() + ";"
                        + s.getPlace() + ";"
                        + s.getMbps() + ";"
                        + s.getUploadMbps() + ";"
                        + s.getPing());
                writer.newLine();
                i++;
            }

            System.out.println("Деректер файлға сақталды.");
            return true;

        } catch (IOException e) {
            System.out.println("Файлға сақтау қатесі: " + e.getMessage());
            return false;
        }
    }

    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            System.out.println("Сақталған файл жоқ. Жаңа журнал басталды.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";", -1);

                if ((p.length != 4 && p.length != 5)
                        || count >= items.length) {
                    System.out.println("Жарамсыз жазба өткізіліп кетті.");
                    continue;
                }

                try {
                    int download = Integer.parseInt(
                            p.length == 4 ? p[2] : p[2]);
                    int upload = p.length == 5
                            ? Integer.parseInt(p[3]) : 0;
                    int ping = Integer.parseInt(
                            p.length == 4 ? p[3] : p[4]);

                    if (!p[0].matches("\\d{2}\\.\\d{2}")
                            || p[1].trim().isEmpty()
                            || download < 0 || download > 2000
                            || upload < 0 || upload > 2000
                            || ping < 1 || ping > 2000) {
                        System.out.println("Жарамсыз дерек өткізілді.");
                        continue;
                    }

                    InternetSpeed s = new InternetSpeed();
                    s.setDate(p[0]);
                    s.setPlace(p[1]);
                    s.setMbps(download);
                    s.setUploadMbps(upload);
                    s.setPing(ping);
                    addRecord(s);

                } catch (NumberFormatException e) {
                    System.out.println("Файлдағы сандық мән қате: " + line);
                }
            }

            System.out.println("Жүктелген жазбалар саны: " + count);

        } catch (IOException e) {
            System.out.println("Файлды оқу қатесі: " + e.getMessage());
        }
    }
}

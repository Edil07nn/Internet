
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
        if (item == null) {
            System.out.println("Жазба бос болмауы керек!");
            return false;
        }

        if (count >= items.length) {
            System.out.println("Жазбалар санының шегіне жеттіңіз!");
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
        System.out.println("Жалпы жылдамдық мәндерінің қосындысы: "
                + getTotalMbps() + " Мбит/с");
        System.out.printf("Орташа жылдамдық: %.2f Мбит/с%n",
                getAverageMbps());

        System.out.println("\nЕң жоғары жылдамдық:");
        findMax().printInfo();

        System.out.println("\nЕң төмен жылдамдық:");
        findMin().printInfo();

        System.out.println("\nБаяу интернет тіркелген жағдайлар: "
                + countSlow());
    }

    // Барлық жазбаны файлға сақтау
    public boolean saveToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardCharsets.UTF_8)) {

            int i = 0;

            while (i < count) {
                InternetSpeed speed = items[i];

                writer.write(
                        speed.getDate() + ";"
                                + speed.getPlace() + ";"
                                + speed.getMbps() + ";"
                                + speed.getPing()
                );

                writer.newLine();
                i++;
            }

            System.out.println("Деректер файлға сақталды.");
            return true;

        } catch (IOException e) {
            System.out.println("Файлға сақтау қатесі: "
                    + e.getMessage());
            return false;
        }
    }

    // Файлдағы жазбаларды журналға жүктеу
    public void loadFromFile() {
        if (!Files.exists(filePath)) {
            System.out.println(
                    "Сақталған файл әзірге жоқ. Жаңа журнал басталды."
            );
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);

                if (parts.length != 4 || count >= items.length) {
                    System.out.println(
                            "Жарамсыз жазба немесе журнал толып қалды."
                    );
                    continue;
                }

                try {
                    InternetSpeed speed = new InternetSpeed();

                    speed.setDate(parts[0]);
                    speed.setPlace(parts[1]);
                    speed.setMbps(Integer.parseInt(parts[2]));
                    speed.setPing(Integer.parseInt(parts[3]));

                    if (speed.getDate() != null
                            && speed.getPlace() != null
                            && speed.getMbps() >= 0
                            && speed.getMbps() <= 2000
                            && speed.getPing() >= 1
                            && speed.getPing() <= 2000) {

                        addRecord(speed);
                    } else {
                        System.out.println(
                                "Жарамсыз дерек файлдан өткізілді."
                        );
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Файлдағы сандық мән қате: " + line
                    );
                }
            }

            System.out.println(
                    "Файлдан жүктелген жазбалар саны: " + count
            );

        } catch (IOException e) {
            System.out.println("Файлды оқу қатесі: "
                    + e.getMessage());
        }
    }
}

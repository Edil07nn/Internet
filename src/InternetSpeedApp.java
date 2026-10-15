
import java.util.Scanner;

public class InternetSpeedApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InternetSpeedLog log = new InternetSpeedLog();

        log.loadFromFile();

        boolean running = true;

        while (running) {
            System.out.println("\n===== INTERNET SPEED =====");
            System.out.println("1. Барлық жазбаларды көрсету");
            System.out.println("2. Жаңа өлшем қосу");
            System.out.println("3. Статистиканы көрсету");
            System.out.println("4. Ең жоғары жылдамдықты көрсету");
            System.out.println("5. Орын бойынша іздеу");
            System.out.println("6. Ең төмен жылдамдықты көрсету");
            System.out.println("7. Баяу интернет жазбаларын санау");
            System.out.println("8. Жазбаны өзгерту");
            System.out.println("9. Жазбаны өшіру");
            System.out.println("10. CSV файлына экспорттау");
            System.out.println("11. Жылдамдық графигі");
            System.out.println("0. Шығу");
            System.out.print("Таңдауыңыз: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    log.printAll();
                    break;

                case "2": {
                    InternetSpeed speed = readSpeed(scanner);

                    if (speed != null && log.addRecord(speed)) {
                        log.saveToFile();
                    }
                    break;
                }

                case "3":
                    log.printStatistics();
                    break;

                case "4": {
                    InternetSpeed max = log.findMax();

                    if (max == null) {
                        System.out.println("Әзірге жазбалар жоқ.");
                    } else {
                        System.out.println("Ең жоғары жылдамдық:");
                        max.printInfo();
                    }
                    break;
                }

                case "5":
                    System.out.print("Орынды енгізіңіз: ");
                    log.printByCategory(scanner.nextLine().trim());
                    break;

                case "6": {
                    InternetSpeed min = log.findMin();

                    if (min == null) {
                        System.out.println("Әзірге жазбалар жоқ.");
                    } else {
                        System.out.println("Ең төмен жылдамдық:");
                        min.printInfo();
                    }
                    break;
                }

                case "7":
                    System.out.println("Баяу интернет жазбаларының саны: "
                            + log.countSlow());
                    break;

                case "8": {
                    log.printAll();

                    int number = readInt(scanner,
                            "Өзгертілетін жазбаның нөмірі: ");

                    if (number < 1 || log.getRecord(number - 1) == null) {
                        System.out.println("Жазба нөмірі дұрыс емес.");
                        break;
                    }

                    InternetSpeed updated = readSpeed(scanner);

                    if (updated != null
                            && log.updateRecord(number - 1, updated)) {
                        log.saveToFile();
                    }
                    break;
                }

                case "9": {
                    log.printAll();

                    int number = readInt(scanner,
                            "Өшірілетін жазбаның нөмірі: ");

                    if (log.deleteRecord(number - 1)) {
                        log.saveToFile();
                    }
                    break;
                }

                case "10":
                    log.exportCSV();
                    break;

                case "11":
                    log.printChart();
                    break;

                case "0":
                    running = false;
                    System.out.println("Бағдарлама аяқталды.");
                    break;

                default:
                    System.out.println("Қате таңдау! Қайта енгізіңіз.");
            }
        }

        scanner.close();
    }

    private static InternetSpeed readSpeed(Scanner scanner) {
        InternetSpeed speed = new InternetSpeed();

        System.out.print("Күнін енгізіңіз (КК.АА): ");
        speed.setDate(scanner.nextLine().trim());

        System.out.print("Өлшеу орнын енгізіңіз: ");
        speed.setPlace(scanner.nextLine().trim());

        if (speed.getDate() == null || speed.getPlace() == null) {
            System.out.println("Күн немесе орын дұрыс емес.");
            return null;
        }

        int download = readInt(scanner,
                "Жүктеу жылдамдығы (Мбит/с): ");
        if (download < 0 || download > 2000) {
            System.out.println("Жылдамдық 0-2000 аралығында болуы керек.");
            return null;
        }

        int upload = readInt(scanner,
                "Жіберу жылдамдығы (Мбит/с): ");
        if (upload < 0 || upload > 2000) {
            System.out.println("Жылдамдық 0-2000 аралығында болуы керек.");
            return null;
        }

        int ping = readInt(scanner, "Ping (мс): ");
        if (ping < 1 || ping > 2000) {
            System.out.println("Ping 1-2000 аралығында болуы керек.");
            return null;
        }

        speed.setMbps(download);
        speed.setUploadMbps(upload);
        speed.setPing(ping);

        return speed;
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Бүтін сан енгізу керек.");
            return -1;
        }
    }
}

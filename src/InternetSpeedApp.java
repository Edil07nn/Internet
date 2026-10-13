
import java.util.Scanner;

public class InternetSpeedApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InternetSpeedLog log = new InternetSpeedLog();

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
            System.out.println("0. Шығу");
            System.out.print("Таңдауыңыз: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    log.printAll();
                    break;

                case "2":
                    InternetSpeed speed = new InternetSpeed();

                    System.out.print("Күнін енгізіңіз (КК.АА): ");
                    speed.setDate(scanner.nextLine().trim());

                    System.out.print("Өлшеу орнын енгізіңіз: ");
                    speed.setPlace(scanner.nextLine().trim());

                    System.out.print("Жүктеу жылдамдығы (Мбит/с): ");
                    try {
                        speed.setMbps(Integer.parseInt(
                                scanner.nextLine().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Жылдамдық бүтін сан болуы керек!");
                        break;
                    }

                    System.out.print("Ping (мс): ");
                    try {
                        speed.setPing(Integer.parseInt(
                                scanner.nextLine().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Ping бүтін сан болуы керек!");
                        break;
                    }

                    if (speed.getDate() != null
                            && speed.getPlace() != null) {
                        log.addRecord(speed);
                    } else {
                        System.out.println("Жазба қосылмады. Деректерді тексеріңіз.");
                    }
                    break;

                case "3":
                    log.printStatistics();
                    break;

                case "4":
                    InternetSpeed max = log.findMax();
                    if (max == null) {
                        System.out.println("Әзірге жазбалар жоқ.");
                    } else {
                        System.out.println("Ең жоғары жылдамдық:");
                        max.printInfo();
                    }
                    break;

                case "5":
                    System.out.print("Орынды енгізіңіз (Үй, Университет, Кафе): ");
                    String place = scanner.nextLine().trim();
                    log.printByCategory(place);
                    break;

                case "6":
                    InternetSpeed min = log.findMin();
                    if (min == null) {
                        System.out.println("Әзірге жазбалар жоқ.");
                    } else {
                        System.out.println("Ең төмен жылдамдық:");
                        min.printInfo();
                    }
                    break;

                case "7":
                    System.out.println("Баяу интернет жазбаларының саны: "
                            + log.countSlow());
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
}

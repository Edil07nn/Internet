
public class InternetSpeedTestDrive {
    public static void main(String[] args) {
        InternetSpeed speed = new InternetSpeed();

        speed.setDate("12.10");
        speed.setPlace("Үй");
        speed.setMbps(100);
        speed.setPing(20);

        System.out.println("=== Интернет өлшемін тексеру ===");
        speed.printInfo();

        System.out.println("\nБаяу интернет пе? " + speed.isSlow());
    }
}

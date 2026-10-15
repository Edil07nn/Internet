
public class InternetSpeedTestDrive {
    public static void main(String[] args) {
        InternetSpeed speed = new InternetSpeed();

        speed.setDate("15.10");
        speed.setPlace("Универ 422");
        speed.setMbps(11);
        speed.setPing(591);

        System.out.println("=== Интернет өлшемін тексеру ===");
        speed.printInfo();

        System.out.println("\nБаяу интернет пе? " + speed.isSlow());
    }
}

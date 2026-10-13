public class InternetSpeed {
    private String date;
    private String place;
    private int mbps;
    private int ping;

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public int getMbps() {
        return mbps;
    }

    public int getPing() {
        return ping;
    }

    public void setDate(String date) {
        if (date != null && !date.trim().isEmpty()) {
            this.date = date;
        } else {
            System.out.println("Қате: күн бос болмауы керек!");
        }
    }

    // Өз бетіңше аяқта:
    // setPlace(String place)
    // setMbps(int mbps) — 0 мен 2000 аралығы
    // setPing(int ping) — 1 мен 2000 аралығы
    // isSlow() — mbps < 10 немесе ping > 50
    // printInfo() — барлық ақпаратты бір жолға шығару
}
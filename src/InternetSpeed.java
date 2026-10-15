
public class InternetSpeed {
    private String date;
    private String place;
    private int mbps;
    private int ping;
    private int uploadMbps;

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

    public int getUploadMbps() {
        return uploadMbps;
    }

    public void setDate(String date) {
        if (date != null && date.matches("\\d{2}\\.\\d{2}")) {
            this.date = date;
        } else {
            System.out.println("Күн форматы КК.АА болуы керек!");
        }
    }

    public void setPlace(String place) {
        if (place != null && !place.trim().isEmpty()) {
            this.place = place;
        } else {
            System.out.println("Өлшеу орны бос болмауы керек!");
        }
    }

    public void setMbps(int mbps) {
        if (mbps >= 0 && mbps <= 2000) {
            this.mbps = mbps;
        } else {
            System.out.println(
                    "Жылдамдық 0-2000 аралығында болуы керек!"
            );
        }
    }

    public void setPing(int ping) {
        if (ping >= 1 && ping <= 2000) {
            this.ping = ping;
        } else {
            System.out.println(
                    "Ping 1-2000 аралығында болуы керек!"
            );
        }
    }

    public void setUploadMbps(int uploadMbps) {
        if (uploadMbps >= 0 && uploadMbps <= 2000) {
            this.uploadMbps = uploadMbps;
        } else {
            System.out.println(
                    "Жіберу жылдамдығы 0-2000 аралығында болуы керек!"
            );
        }
    }

    public boolean isSlow() {
        return mbps < 10 || ping > 50;
    }

    public String getQuality() {
        if (mbps >= 100) {
            return "Өте жақсы";
        } else if (mbps >= 50) {
            return "Жақсы";
        } else if (mbps >= 10) {
            return "Орташа";
        } else {
            return "Нашар";
        }
    }

    public void printInfo() {
        System.out.println("Күні: " + date);
        System.out.println("Орын: " + place);
        System.out.println(
                "Жүктеу жылдамдығы: " + mbps + " Мбит/с"
        );
        System.out.println(
                "Жіберу жылдамдығы: " + uploadMbps + " Мбит/с"
        );
        System.out.println("Ping: " + ping + " мс");
        System.out.println(
                "Интернет күйі: " + (isSlow() ? "Баяу" : "Қалыпты")
        );
        System.out.println("Интернет сапасы: " + getQuality());
    }
}

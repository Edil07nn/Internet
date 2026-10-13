
public class InternetSpeedLog {
    private InternetSpeed[] items = new InternetSpeed[30];
    private int count = 0;

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

        InternetSpeed max = findMax();
        InternetSpeed min = findMin();

        System.out.println("\nЕң жоғары жылдамдық:");
        max.printInfo();

        System.out.println("\nЕң төмен жылдамдық:");
        min.printInfo();

        System.out.println("\nБаяу интернет тіркелген жағдайлар: "
                + countSlow());
    }
}

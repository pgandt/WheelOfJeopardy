public class enumTest {
    public enum Sectors {
        CAT1 ("cat1Label", 0),
        CAT2 ("cat2Label", 1);

        private final String label;
        private final int index;

        Sectors(String label, int index) {
            this.label=label;
            this.index=index;
        }

        String getCat(int ind) {
            for(Sectors cat: Sectors.values()) {
                if(cat.index==ind) return cat.values()[ind].toString();
            }
            return "Not a Match";
        }
    }

    public static void main(String[] args) {

        Sectors[] secs=Sectors.values();
        System.out.println(secs[0].getCat(0));
    }
}

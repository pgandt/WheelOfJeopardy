public class Sector {

    private String info;
    private int ID;

    public Sector(String info, int ID) {
        setCat(info, ID);
    }

    private void setCat(String infoIN, int IDIN) {
        this.info=infoIN;
        this.ID=IDIN;
    }

    public String dispSec() {
        return info;
    }

    public int getSecID() {
        return ID;
    }

    public static void main(String[] args) {
        Sector sec1=new Sector("sec1info", 2);
        System.out.println(sec1.dispSec());
        System.out.println(sec1.getSecID());
    }

}
import java.util.Random;

public class Countries {

    String countryName[]={"india","america","germany","canada","dubai"};


    public String getRandom() {
        int min=0;
        int max=3;
        Random random = new Random();
        double r= random.nextInt(max - min) + min;
        return countryName[(int)r];
    }

    int x=0;
    public String getRandom(int x) {

        String country=countryName[x];
        x=x+1;
        return country;
    }

}

import java.util.Random;

public class Persons {

    String personName[]={"gandhi","nehru","bharathiyar","valluvar","kambar"};


    public String getRandom() {
        int min=0;
        int max=3;
        Random random = new Random();
        double r= random.nextInt(max - min) + min;
        return personName[(int)r];
    }

    int x=0;
    public String getRandom(int x) {

        String person=personName[x];
        x=x+1;
        return person;
    }

}

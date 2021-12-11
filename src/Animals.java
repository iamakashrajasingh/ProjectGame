import java.util.Random;

public class Animals {

    String animalName[]={"lion","tiger","monkey","giraffe","zebra"};


    public String getRandom() {
        int min=0;
        int max=3;
        Random random = new Random();
        double r= random.nextInt(max - min) + min;
        return animalName[(int)r];
    }

    int x=0;
    public String getRandom(int x) {

        String animal=animalName[x];
        x=x+1;
        return animal;
    }


}

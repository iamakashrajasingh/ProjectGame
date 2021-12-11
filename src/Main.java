import javax.swing.*;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Users users;
    static Scanner scanner;
    static int mpOpt,gameOpt,birdScore=0,birdOpt,animalScore=0,animalOpt,countryScore=0,countryOpt,personScore=0,personOpt;
    static String username,password;
    static String key="difficultkey";
    static Connection c = null;

    static Statement stmt = null;

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt = c.createStatement();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        scanner=new Scanner(System.in);
        users=new Users();
        mainScreen();

    }

    private static void mainScreen() {
        System.out.println("1). Login\t2). Sign-up\t3). Guest");
        System.out.print("Please choose an Option: ");
        mpOpt=scanner.nextInt();
        switch (mpOpt)
        {
            case 1:
                login();
                break;
            case 2:
                signup();
                break;
            case 3:
                guestMode();
                break;
            default:
                System.out.println("Invalid Option! Please try again...");
                mainScreen();
        }
    }


    private static void guestMode() {
    }

    private static void signup() {
        System.out.print("Enter Username:");
        username=scanner.next();
        System.out.print("Enter Password:");
        password =scanner.next();
        String sql = "INSERT INTO users(username,password) VALUES ('"+username+"', '"+password+"' );";
        System.out.println(sql);
        try {
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sep();
        mainScreen();

    }

    private static void login() {

        System.out.print("Enter Username:");
        username=scanner.next();
        System.out.print("Enter Password:");
        password=scanner.next();
        try {
            ResultSet rs = stmt.executeQuery( "SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"';" );
            while ( rs.next() ) {
                users.setUsername(rs.getString(2));
                users.setPassword(rs.getString(3));
                users.setUserId(rs.getString(1));
//                System.out.println(rs.getString(2));
            }
            if (users.getUsername().equalsIgnoreCase(username)) {
                if (users.getPassword().equals(password)) {
                    System.out.println("Login Success!");
                    gameScreen();

                } else {
                    System.out.println("Login Failed due to invalid Password!");
                }
            } else {
                System.out.print("No User found!");
            }
        }
        catch (NullPointerException ex)
        {
            System.out.println("Please Signup first");
            mainScreen();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void sep()
    {
        System.out.print("\n*****************************************\n\n");

    }
    public static void gameScreen()
    {
        System.out.println("1). Play\t2). View Achievements\t3). Ranking\t4). Exit");
        System.out.print("Please choose an Option: ");
        gameOpt=scanner.nextInt();
        switch (gameOpt)
        {
            case 1:
                play();
            case 2:
                achievements();
                break;
            case 3:
                rank();
                break;
            case 4:
                mainScreen();
            default:
                System.out.println("Invalid Option! Please try again...");
                gameScreen();
        }
    }

    private static void achievements() {

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where uid=" + users.getUserId() + ";");
            while (rs.next()) {
                System.out.println("Hello " + users.getUsername() + "!");
                System.out.println("Bird Score: " + rs.getString(4));
                System.out.println("Animal Score: " + rs.getString(5));
                System.out.println("Country Score: " + rs.getString(6));
                System.out.println("Person Score: " + rs.getString(7));
                gameScreen();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void rank() {
        try {
            System.out.println("Hello " + users.getUsername() + "!");

            ResultSet rs;
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users ORDER BY birdScore DESC;");
            System.out.println("Bird Topper: " + rs.getString(2) + ":" + rs.getString(4));

            rs = stmt.executeQuery("SELECT * FROM users ORDER BY animalScore DESC;");
            System.out.println("Animal Topper: " + rs.getString(2) + ":" + rs.getString(5));

            rs = stmt.executeQuery("SELECT * FROM users ORDER BY countryScore DESC;");
            System.out.println("Country Topper: " + rs.getString(2) + ":" + rs.getString(6));

            rs = stmt.executeQuery("SELECT * FROM users ORDER BY personScore DESC;");
            System.out.println("Person Topper: " + rs.getString(2) + ":" + rs.getString(7));

            gameScreen();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void play() {
        System.out.println("1). Birds\t2). Animals\t3). Countries\t4). Famous Personalities\t5). Exit");
        System.out.print("Please choose an Option: ");
        gameOpt=scanner.nextInt();
        switch (gameOpt)
        {
            case 1:
                bird();
            case 2:
                animal();
                break;
            case 3:
                country();
                break;
            case 4:
                person();
                break;
            case 5:
                mainScreen();
                break;
            default:
                System.out.println("Invalid Option! Please try again...");
                play();
        }


    }

    private static void person() {

        int opt;
        String[] opts = new String[4];
        String[] opts1 = new String[4];
        String encText="";
        System.out.print("\n\n\n-----------------Starting Quiz-----------------\n\n\n");

        Persons persons=new Persons();
        String person=persons.getRandom(0);
        //System.out.print("1). "+person);
        opt=getRandomNo();
        encText=getCipher(person,opt);
        System.out.println("1). "+encText);

        opts[0]=person;
        opts[1]=persons.getRandom();
        opts[2]=persons.getRandom();
        opts[3]=persons.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        personOpt=scanner.nextInt();
        personOpt=personOpt-1;
        if(opts[personOpt].equals(person))
        {
            System.out.println("Correct Answer");
            personScore=personScore+1;
        }

        person=persons.getRandom(0);
        //System.out.print("1). "+person);
        opt=getRandomNo();
        encText=getCipher(person,opt);
        System.out.println("2). "+encText);

        opts[0]=person;
        opts[1]=persons.getRandom();
        opts[2]=persons.getRandom();
        opts[3]=persons.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        personOpt=scanner.nextInt();
        personOpt=personOpt-1;
        if(opts[personOpt].equals(person))
        {
            System.out.println("Correct Answer");
            personScore=personScore+1;
        }

        person=persons.getRandom(0);
        //System.out.print("1). "+person);
        opt=getRandomNo();
        encText=getCipher(person,opt);
        System.out.println("3). "+encText);

        opts[0]=person;
        opts[1]=persons.getRandom();
        opts[2]=persons.getRandom();
        opts[3]=persons.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        personOpt=scanner.nextInt();
        personOpt=personOpt-1;
        if(opts[personOpt].equals(person))
        {
            System.out.println("Correct Answer");
            personScore=personScore+1;
        }

        person=persons.getRandom(0);
        //System.out.print("1). "+person);
        opt=getRandomNo();
        encText=getCipher(person,opt);
        System.out.println("4). "+encText);

        opts[0]=person;
        opts[1]=persons.getRandom();
        opts[2]=persons.getRandom();
        opts[3]=persons.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        personOpt=scanner.nextInt();
        personOpt=personOpt-1;
        if(opts[personOpt].equals(person))
        {
            System.out.println("Correct Answer");
            personScore=personScore+1;
        }

        person=persons.getRandom(0);
        //System.out.print("1). "+person);
        opt=getRandomNo();
        encText=getCipher(person,opt);
        System.out.println("5). "+encText);

        opts[0]=person;
        opts[1]=persons.getRandom();
        opts[2]=persons.getRandom();
        opts[3]=persons.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        personOpt=scanner.nextInt();
        personOpt=personOpt-1;
        if(opts[personOpt].equals(person))
        {
            System.out.println("Correct Answer");
            personScore=personScore+1;
        }
        System.out.println("Your Score in this Round is "+personScore);

        String sql = "UPDATE users SET personScore="+personScore+" WHERE uid = "+users.getUserId()+";";
        try {

            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt=c.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        play();

    }

    private static void country() {

        int opt;
        String[] opts = new String[4];
        String[] opts1 = new String[4];
        String encText="";
        System.out.print("\n\n\n-----------------Starting Quiz-----------------\n\n\n");

        Countries countries=new Countries();
        String country=countries.getRandom(0);
        //System.out.print("1). "+country);
        opt=getRandomNo();
        encText=getCipher(country,opt);
        System.out.println("1). "+encText);

        opts[0]=country;
        opts[1]=countries.getRandom();
        opts[2]=countries.getRandom();
        opts[3]=countries.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        countryOpt=scanner.nextInt();
        countryOpt=countryOpt-1;
        if(opts[countryOpt].equals(country))
        {
            System.out.println("Correct Answer");
            countryScore=countryScore+1;
        }

        country=countries.getRandom(0);
        //System.out.print("1). "+country);
        opt=getRandomNo();
        encText=getCipher(country,opt);
        System.out.println("2). "+encText);

        opts[0]=country;
        opts[1]=countries.getRandom();
        opts[2]=countries.getRandom();
        opts[3]=countries.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        countryOpt=scanner.nextInt();
        countryOpt=countryOpt-1;
        if(opts[countryOpt].equals(country))
        {
            System.out.println("Correct Answer");
            countryScore=countryScore+1;
        }

        country=countries.getRandom(0);
        //System.out.print("1). "+country);
        opt=getRandomNo();
        encText=getCipher(country,opt);
        System.out.println("3). "+encText);

        opts[0]=country;
        opts[1]=countries.getRandom();
        opts[2]=countries.getRandom();
        opts[3]=countries.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        countryOpt=scanner.nextInt();
        countryOpt=countryOpt-1;
        if(opts[countryOpt].equals(country))
        {
            System.out.println("Correct Answer");
            countryScore=countryScore+1;
        }

        country=countries.getRandom(0);
        //System.out.print("1). "+country);
        opt=getRandomNo();
        encText=getCipher(country,opt);
        System.out.println("4). "+encText);

        opts[0]=country;
        opts[1]=countries.getRandom();
        opts[2]=countries.getRandom();
        opts[3]=countries.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        countryOpt=scanner.nextInt();
        countryOpt=countryOpt-1;
        if(opts[countryOpt].equals(country))
        {
            System.out.println("Correct Answer");
            countryScore=countryScore+1;
        }

        country=countries.getRandom(0);
        //System.out.print("1). "+country);
        opt=getRandomNo();
        encText=getCipher(country,opt);
        System.out.println("5). "+encText);

        opts[0]=country;
        opts[1]=countries.getRandom();
        opts[2]=countries.getRandom();
        opts[3]=countries.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        countryOpt=scanner.nextInt();
        countryOpt=countryOpt-1;
        if(opts[countryOpt].equals(country))
        {
            System.out.println("Correct Answer");
            countryScore=countryScore+1;
        }

        System.out.println("Your Score in this Round is "+countryScore);
        String sql = "UPDATE users SET countryScore="+countryScore+" WHERE uid = "+users.getUserId()+";";
        try {

            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt=c.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        play();


    }

    private static void animal() {
        int opt;
            String[] opts = new String[4];
            String[] opts1 = new String[4];
            String encText="";
            System.out.print("\n\n\n-----------------Starting Quiz-----------------\n\n\n");

            Animals animals=new Animals();
            String animal=animals.getRandom(0);
            //System.out.print("1). "+animal);
            opt=getRandomNo();
            encText=getCipher(animal,opt);
            System.out.println("1). "+encText);

            opts[0]=animal;
            opts[1]=animals.getRandom();
            opts[2]=animals.getRandom();
            opts[3]=animals.getRandom();

            opts1=shuffleArray(opts);

            System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
            System.out.print("Enter a Choice:");
            animalOpt=scanner.nextInt();
            animalOpt=animalOpt-1;
            if(opts[animalOpt].equals(animal))
            {
                System.out.println("Correct Answer");
                animalScore=animalScore+1;
            }

        animal=animals.getRandom(0);
        //System.out.print("1). "+animal);
        opt=getRandomNo();
        encText=getCipher(animal,opt);
        System.out.println("2). "+encText);

        opts[0]=animal;
        opts[1]=animals.getRandom();
        opts[2]=animals.getRandom();
        opts[3]=animals.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        animalOpt=scanner.nextInt();
        animalOpt=animalOpt-1;
        if(opts[animalOpt].equals(animal))
        {
            System.out.println("Correct Answer");
            animalScore=animalScore+1;
        }

        animal=animals.getRandom(0);
        //System.out.print("1). "+animal);
        opt=getRandomNo();
        encText=getCipher(animal,opt);
        System.out.println("3). "+encText);

        opts[0]=animal;
        opts[1]=animals.getRandom();
        opts[2]=animals.getRandom();
        opts[3]=animals.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        animalOpt=scanner.nextInt();
        animalOpt=animalOpt-1;
        if(opts[animalOpt].equals(animal))
        {
            System.out.println("Correct Answer");
            animalScore=animalScore+1;
        }

        animal=animals.getRandom(0);
        //System.out.print("1). "+animal);
        opt=getRandomNo();
        encText=getCipher(animal,opt);
        System.out.println("4). "+encText);

        opts[0]=animal;
        opts[1]=animals.getRandom();
        opts[2]=animals.getRandom();
        opts[3]=animals.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        animalOpt=scanner.nextInt();
        animalOpt=animalOpt-1;
        if(opts[animalOpt].equals(animal))
        {
            System.out.println("Correct Answer");
            animalScore=animalScore+1;
        }

        animal=animals.getRandom(0);
        //System.out.print("1). "+animal);
        opt=getRandomNo();
        encText=getCipher(animal,opt);
        System.out.println("5). "+encText);

        opts[0]=animal;
        opts[1]=animals.getRandom();
        opts[2]=animals.getRandom();
        opts[3]=animals.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        animalOpt=scanner.nextInt();
        animalOpt=animalOpt-1;
        if(opts[animalOpt].equals(animal))
        {
            System.out.println("Correct Answer");
            animalScore=animalScore+1;
        }

        System.out.println("Your Score in this Round is "+animalScore);
        String sql = "UPDATE users SET animalScore="+animalScore+" WHERE uid = "+users.getUserId()+";";
        try {

            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt=c.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        play();

        }

    private static void bird()
    {
        int opt;
        String[] opts = new String[4];
        String[] opts1 = new String[4];
        String encText="";
        System.out.print("\n\n\n-----------------Starting Quiz-----------------\n\n\n");

        Birds birds=new Birds();
        String bird=birds.getRandom(0);
        //System.out.print("1). "+bird);
        opt=getRandomNo();
        encText=getCipher(bird,opt);
        System.out.println("1). "+encText);

        opts[0]=bird;
        opts[1]=birds.getRandom();
        opts[2]=birds.getRandom();
        opts[3]=birds.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);
        System.out.print("Enter a Choice:");
        birdOpt=scanner.nextInt();
        birdOpt=birdOpt-1;
        if(opts[birdOpt].equals(bird))
        {
            System.out.println("Correct Answer");
            birdScore=birdScore+1;
        }




        bird=birds.getRandom(0);
        //System.out.print("1). "+bird);
        opt=getRandomNo();
        encText=getCipher(bird,opt);
        System.out.println("2). "+encText);

        opts[0]=bird;
        opts[1]=birds.getRandom();
        opts[2]=birds.getRandom();
        opts[3]=birds.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);

        System.out.print("Enter a Choice:");
        birdOpt=scanner.nextInt();
        birdOpt=birdOpt-1;
        if(opts[birdOpt].equals(bird))
        {
            System.out.println("Correct Answer");
            birdScore=birdScore+1;
        }

        bird=birds.getRandom(0);
        //System.out.print("1). "+bird);
        opt=getRandomNo();
        encText=getCipher(bird,opt);
        System.out.println("3). "+encText);

        opts[0]=bird;
        opts[1]=birds.getRandom();
        opts[2]=birds.getRandom();
        opts[3]=birds.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);

        System.out.print("Enter a Choice:");
        birdOpt=scanner.nextInt();
        birdOpt=birdOpt-1;
        if(opts[birdOpt].equals(bird))
        {
            System.out.println("Correct Answer");
            birdScore=birdScore+1;
        }

        bird=birds.getRandom(0);
        //System.out.print("1). "+bird);
        opt=getRandomNo();
        encText=getCipher(bird,opt);
        System.out.println("4). "+encText);

        opts[0]=bird;
        opts[1]=birds.getRandom();
        opts[2]=birds.getRandom();
        opts[3]=birds.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);

        System.out.print("Enter a Choice:");
        birdOpt=scanner.nextInt();
        birdOpt=birdOpt-1;
        if(opts[birdOpt].equals(bird))
        {
            System.out.println("Correct Answer");
            birdScore=birdScore+1;
        }

        bird=birds.getRandom(0);
        //System.out.print("1). "+bird);
        opt=getRandomNo();
        encText=getCipher(bird,opt);
        System.out.println("5). "+encText);

        opts[0]=bird;
        opts[1]=birds.getRandom();
        opts[2]=birds.getRandom();
        opts[3]=birds.getRandom();

        opts1=shuffleArray(opts);

        System.out.println("1). "+opts1[0]+" 2). "+opts1[1]+" 3). "+opts1[2]+" 4). "+opts1[3]);

        System.out.print("Enter a Choice:");
        birdOpt=scanner.nextInt();
        birdOpt=birdOpt-1;
        if(opts[birdOpt].equals(bird))
        {
            System.out.println("Correct Answer");
            birdScore=birdScore+1;
        }

        System.out.println("Your Score in this Round is "+birdScore);
        String sql = "UPDATE users SET birdScore="+birdScore+" WHERE uid = "+users.getUserId()+";";
        try {

            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt=c.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        play();


    }

    public static int getRandomNo() {
        int min=0;
        int max=4;
        Random random = new Random();
        return random.nextInt(max - min) + min;

    }

    public static String getCipher(String raw,int mode)
    {
        String cipher="";

        switch (mode)
        {
            case 0:
                Playfair pfc1 = new Playfair(key, raw);
                pfc1.cleanPlayFairKey();
                pfc1.generateCipherKey();

                cipher = pfc1.encryptMessage();
                break;
            case 1:
                CaesarCipher caesarCipher=new CaesarCipher();
                cipher=caesarCipher.encrypt(raw,5).toString();
                break;
            case 2:
                HillCipher hillCipher=new HillCipher();
                cipher=hillCipher.encrypt(raw,key);
                break;
            case 3:
                try {
                    RailFence rf = new RailFence();
                    cipher = rf.Encryption(raw, 5);

                }
                catch (Exception ex)
                {
                    System.out.println("Something went Wrong!");
                }
            case 4:
                SubstiteCipher substiteCipher=new SubstiteCipher();
                cipher=substiteCipher.encrypt(raw,4);
                break;




        }

        return cipher;
    }


    private static String[] shuffleArray(String[] array)
    {
        int index;
        String temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }
}

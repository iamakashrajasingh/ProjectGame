import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Users users=new Users();

        int mpOpt;
        String username,password;

        System.out.println("1). Login\t2). Sign-up\t3). Guest");
        System.out.print("Please choose an Option: ");
        mpOpt=scanner.nextInt();
        switch (mpOpt)
        {
            case 1:
                login();
            case 2:
                signup();
            case 3:
                guestMode();
        }
    }

    private static void guestMode() {
    }

    private static void signup() {
        System.out.print("Enter Username:");

        System.out.print("Enter Password:");
    }

    private static void login() {

        System.out.print("Enter Username:");
        System.out.print("Enter Password:");

    }

}

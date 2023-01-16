package loginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    List<String> usernames = new ArrayList<>();

    List<String> emails = new ArrayList<>();

    List<String> passwords = new ArrayList<>();

    public void register(){
        Scanner input = new Scanner(System.in);
        System.out.print("Ad - Soyad Giriniz : " );
        String name = input.nextLine();

        String username;
        boolean existsUsername;
        do {
            System.out.print("Kullanici Adi Giriniz :");
            username = input.nextLine();
            existsUsername = this.usernames.contains(username);//Listede username varsa true, yoksa false olacak

            if (existsUsername){
                System.out.println("Bu Username Zaten Kayitli. Lutfen Farkli Bir UserName Deneyiniz.");
            }
        }while (existsUsername);


        String email;
        boolean existEmail;
        boolean isValid;
        do {
            System.out.print("Lutfen E-Mail Giriniz :");
            email = input.nextLine();
            isValid = validateEmail(email);
            existEmail = this.emails.contains(email);



            if (existEmail){
                System.out.println("Bu E-Mail Zaten Kayitli. Lutfen Farkli Bir E-Mail Deneyiniz.");
                isValid=false;
            }

        }while (!isValid);

        String password;
        boolean isValidPassw;
        do {
            System.out.println("Lutfen Sifrenizi Giriniz");
            password =input.nextLine().trim();
            isValidPassw = validatePassword(password);

        }while (!isValidPassw);

        User user = new User(name,username,email,password);

        this.usernames.add(username);
        this.emails.add(email);
        this.passwords.add(password);
        System.out.println("****TEBRIKLER*****Isleminiz Basariyla Gerceklestirildi");
        System.out.println("Kullanici Adi veya Email Adresinizle Giris Yapabilirsiniz");


    }

    public void login (){
        Scanner input = new Scanner(System.in);
        System.out.println("Kullanici Adi veya Email Giriniz :");
        String usernameOremail = input.next();

        //Kullanicinin girdigi deger email mi username mi?

        boolean isUsername = this.usernames.contains(usernameOremail);
        boolean isMail =this.emails.contains(usernameOremail);

        if(isUsername || isMail){
            boolean isWrong = true;
            while (isWrong){
                System.out.println("Lutfen Sifrenizi Giriniz :");
                String  password = input.next();
                //username/email ile sifre ayni indexte olmali
                int index ;
                if (isUsername){
                    index = this.usernames.indexOf(usernameOremail);
                }else {
                    index=this.emails.indexOf(usernameOremail);
                }
                if (this.passwords.get(index).equals(password)){
                    System.out.println("Sisteme Giris Yaptiniz. Hosgeldiniz...");
                    isWrong=false;
                }else {
                    System.out.println("Sifreniz Yanlis veya Hatali. Lutfen tekrar deneyiniz!");
                }
            }

        }else {
            System.out.println("Sisteme Kayitli Boyle Bir Kullanici Bulunamadi");
            System.out.println("Uyeyseniz Bilgilerinizi Kontrol Ediniz, Uye Degilseniz Lutfen Uye Olun");
        }
    }

    public boolean validateEmail(String email ){
        boolean isValid;
        boolean space=email.contains(" ");
        boolean isContainAt = email.contains("@");

        if (space){
            System.out.println("Email Bosluk Iceremez!");
            isValid=false;
        }else if (!isContainAt){
            System.out.println("Email @ Sembolu Icermelidir!");
            isValid=false;
        }else {//ass@asdd

            String firstPart = email.split("@")[0];
            String secondPart = email.split("@")[1];

            int notValid = firstPart.replaceAll("[^a-zA-Z0-9-._]","").length();//a.S1-._*/->*/
            boolean checkStart=notValid==0;
            boolean checkEnd=secondPart.equals("gmail.com") ||
                    secondPart.equals("hotmail.com")||
                    secondPart.equals("yahoo.com");

            if (!checkStart){
                System.out.println("Email Kullanici Adi Buyuk Harf, Kucuk Harf, Rakam, -,.,_ Disinda Karakter Iceremez");
            }else if (!checkEnd){
                System.out.println("Email --gmail.com , hotmail.com veya yahoo ile Bitmelidir. ");

            }
            isValid=checkEnd && checkStart;

        }
        if (!isValid){
            System.out.println("Gecersiz E-Mail!!! Lutfen Tekrar Deneyiniz");
        }

        return isValid;
    }

    public boolean validatePassword (String password){
        boolean isValid;
        String upperLetter=password.replaceAll("[^A-Z]" ,"");
        String lowerLetter=password.replaceAll("[^a-z]" ,"");
        String digit=password.replaceAll("[\\D]" ,""); //   \\D = ^0-9
        String symbol=password.replaceAll("[\\P{Punct}]" ,"");

        boolean space = password.contains(" ");
        boolean lengthGt6= password.length()>=6;
        boolean existUpper=upperLetter.length()>0;
        boolean existLower=lowerLetter.length()>0;
        boolean existDigit=digit.length()>0;
        boolean existSymbol=symbol.length()>0;

        if (space){
            System.out.println("Sifre Bosluk Icermemelidir");
        }else if (!lengthGt6){
            System.out.println("Sifre Uzunlugu En Az 6 Karakter Icermelidir");
        }else if (!existUpper){
            System.out.println("Sifre En Az Bir Buyuk Harf Icermelidir");
        }else if (!existLower){
            System.out.println("Sifre En Az Bir Kucuk Harf Icermelidir");
        }else if (!existDigit){
            System.out.println("Sifre En Az Bir Rakam Icermelidir");
        }else if (!existSymbol){
            System.out.println("Sifre En Az Bir Sembol Icermelidir");
        }

        isValid =!space && lengthGt6 && existUpper && existLower && existDigit && existSymbol;
        if (!isValid){
            System.out.println("!!! Gecersiz Sifre !!!Tekrar Deneyiniz!");
        }

        return isValid;
    }

}

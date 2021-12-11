public class SubstiteCipher {

    public final String str="abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String plaint,int key)
    {
        plaint=plaint.toLowerCase();
        String ciphert="";
        for(int i=0;i<plaint.length();i++)
        {
            int charpos=str.indexOf(plaint.charAt(i));
            int keyval=(charpos+key)%26;
            char replaceval=str.charAt(keyval);
            ciphert=ciphert+replaceval;
        }
        return ciphert;
    }
}

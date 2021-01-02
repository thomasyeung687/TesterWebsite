package Random;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomString {
	public static void main(String[] args) {
    	//"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    	RandomString random = new RandomString(5,ThreadLocalRandom.current() );
    	System.out.println(random.randString());
    }
	public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;
    
    public RandomString(int length, Random random, String symbols) {
    	if(length<=0) throw new IllegalArgumentException();
    	if(symbols.length()<=1) throw new IllegalArgumentException();
    	this.random=Objects.requireNonNull(random);
    	this.symbols = symbols.toCharArray();
    	this.buf= new char[length];
    }
    
    public RandomString(int length, Random random) {
    	this(length, random, alphanum);
    }
    
    public String randString() {
    	for(int index = 0; index < buf.length; ++index) {
    		buf[index] = symbols[random.nextInt(symbols.length)];
    	}
    	return new String(buf);
    }
    
}



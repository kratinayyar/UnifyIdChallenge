import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RandomPlay {

	public static void main(String[] args) throws IOException {
		
		RandomPlay randomPlay=new RandomPlay();
		System.out.println("Choose the operation number:\n1. Get a random number\n2. Generate a RGB bitmap picture.");
		
		Scanner in = new Scanner(System.in);
		int op=in.nextInt();
	
		int num,min,max,col=2,base;
		String rnd="new",format="plain";
		if(op==1) {
			System.out.println("\nEnter the number of random integer required:");
			num=in.nextInt();
			
			System.out.println("\nEnter the minimum value of random integer:");
			min=in.nextInt();
			
			System.out.println("\nEnter the maximum value of random integer:");
			max=in.nextInt();
			
			System.out.println("\nEnter the base of random integer:");
			base=in.nextInt();			
			
			randomPlay.getRandomNumber(num, min, max, col, base, rnd, format);
		}
		else if(op==2) {
			randomPlay.getBitmapPicture();
		}
		
		
		
	}
	
	public void getRandomNumber(int num, int min, int max, int col, int base, String rnd, String format) throws IOException {
		String baseUrl = "https://www.random.org/integers";
		String url = String.format("%s/?num=%d&min=%d&max=%d&col=%d&base=%d&rnd=%s&format=%s", baseUrl, num, min, max, col, base, rnd, format);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setReadTimeout(60000);

		int responseCode = con.getResponseCode();
		
		if(responseCode != 200) {
			System.out.println("API returned error response. Please try again!");
		}
		ArrayList<Integer> response = readStream(con.getInputStream());
		
		
		System.out.println("\nSending 'GET' request to URL : " + url);
		
		System.out.println("Random Integers :\n");
		for(int i=0;i<response.size();i++) {
		System.out.println(response.get(i)+"\n");
		}
	}
	
	
	public void getBitmapPicture() {
		System.out.println("Bitmap picture");
	}
	
	
    private static ArrayList<Integer> readStream(InputStream in) {
        StringBuilder sbuilder = new StringBuilder();
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
            	sbuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return makeList(sbuilder.toString());
        
    }
    
    private static ArrayList<Integer> makeList(String str) {
    	
    	String[] randomNums = str.split("[ \t\n\r]");
    	ArrayList<Integer> nums = new ArrayList();
    	
    	for(int i=0;i<randomNums.length;i++) {
    		// System.out.println(randomNums[i]+"\n");
    		try {
    			if(randomNums[i]!="")
    			nums.add(Integer.parseInt(randomNums[i]));
    		} catch(java.lang.NumberFormatException e) {
    			continue;
    		}
    		
    	}
    	return nums;
    }
}

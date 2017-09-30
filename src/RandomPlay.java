import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

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
			
			ArrayList<Integer> response = randomPlay.getRandomNumber(num, min, max, col, base, rnd, format);
			
						
			System.out.println("Random Integers :\n");
			for(int i=0;i<response.size();i++) {
			System.out.println(response.get(i)+"\n");
			}
		}
		else if(op==2) {
			randomPlay.getBitmapPicture();
		}
		else {
			System.out.println("Exiting: Please enter correct code!");
		}
		
		
		
	}
	
	public ArrayList<Integer> getRandomNumber(int num, int min, int max, int col, int base, String rnd, String format) throws IOException {
		String baseUrl = "https://www.random.org/integers";
		String url = String.format("%s/?num=%d&min=%d&max=%d&col=%d&base=%d&rnd=%s&format=%s", baseUrl, num, min, max, col, base, rnd, format);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setReadTimeout(120000);

		int responseCode = con.getResponseCode();
		
		if(responseCode != 200) {
			System.out.println("API returned error response. Please try again!");
		}
		ArrayList<Integer> response = readStream(con.getInputStream());

		return response;
	}
	
	
	public void getBitmapPicture() throws IOException {
		ArrayList<Integer> red = new ArrayList();
		ArrayList<Integer> green = new ArrayList();
		ArrayList<Integer> blue = new ArrayList();
		
		for(int i=0;i<32;i++) {
			red.addAll(this.getRandomNumber(512,0,255, 1, 10, "new", "plain"));
			green.addAll(this.getRandomNumber(512,0,255, 1, 10, "new", "plain"));
			blue.addAll(this.getRandomNumber(512,0,255, 1, 10, "new", "plain"));
			
		}
	
		System.out.println("Bitmap picture");
		BufferedImage img = map( 128, 128, red, green, blue);
        savePNG( img, "./test.bmp" );
	}
	
	private static BufferedImage map( int sizeX, int sizeY,ArrayList<Integer> red ,ArrayList<Integer> green ,ArrayList<Integer> blue  ){
        final BufferedImage res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
        for (int x = 0; x < sizeX; x++){
            for (int y = 0; y < sizeY; y++){
            	
                res.setRGB(x, y, new Color(red.get(x*128 + y), green.get(x*128 + y), blue.get(x*128 + y)).getRGB() );
            }
        }
        return res;
    }

	
	private static void savePNG( final BufferedImage bi, final String path ){
        try {
            RenderedImage rendImage = bi;
            ImageIO.write(rendImage, "bmp", new File(path));
            //ImageIO.write(rendImage, "PNG", new File(path));
            //ImageIO.write(rendImage, "jpeg", new File(path));
        } catch ( IOException e) {
            e.printStackTrace();
        }
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

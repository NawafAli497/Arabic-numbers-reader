import java.util.Scanner;

public class Main {
	static String[] soundFiles = new String[15];
	static int count = 0;
	
    public static void main(String[] args){
    	String realNumber = "";
    	String number[];
        int numbvalue;
    	
        Scanner input = new Scanner(System.in);
        
        realNumber = input.next();   
        
    	number = realNumber.split("\\.");
    	
    	if(number.length > 2) {
    		System.out.println("Input is not a number!");
    		return;
    	}
    	
        for(int i = 0; i < number[0].length(); i++) {
        	if(!Character.isDigit(number[0].charAt(i))) { // Integer part is a number. Example (100.25) -> this checks if "100" contains no characters.
        		System.out.println("Input is not a number!");
        		return;
        	}
        }
        
    	if(Integer.parseInt(number[0]) > 1000000) { // Anything bigger than a million is not accepted.
    		System.out.println("Number too big!");
    		return;
    	}
        
    	if(number.length > 1) { // If there is a fraction.
	        for(int i = 0; i < number[1].length(); i++) {
	        	
	        	if(!Character.isDigit(number[1].charAt(i))) { // Fraction part is a number. Example (100.25) -> this checks if "25" contains no characters.
	        		System.out.println("Input is not a number!");
	        		return;
	        	}
	        }
	        
    	}else {
    		if(number[0].length() == 1) {
				ones(Character.getNumericValue(number[0].charAt(0)));
		    	SoundPlayer.loadSound(soundFiles[0]);
			    SoundPlayer.play(soundFiles[0]);

				return;
    		}else if(Integer.parseInt(number[0]) == 1000000) { // If its a million, play abomunif.
        		abomunif();
        		return;
        	}
    		
    		
    		for(int i = number[0].length() - 1; i >= 0 ; i--) {
    			if(i == 0) {
    				numbvalue = Integer.parseInt(number[0].substring(number[0].length() - 2)); 
    				
    				numbvalue = Character.getNumericValue(number[0].charAt(number[0].length() - 2)) * 10;
    				if(numbvalue != 0)
    					tens(numbvalue);
    			}else if(i == 1) {
    				numbvalue = Integer.parseInt(number[0].substring(number[0].length() - 2));
    				
    				if(numbvalue != 0) {
    					ones(numbvalue);
    				}
    			}else if(i == 2) {
    				numbvalue = Integer.parseInt(number[0].substring(number[0].length() - 3));

    				hundreds(Character.getNumericValue(number[0].charAt(number[0].length() - 3)) * 100);
    			}else if(i == 3) {
    				
    			}else if(i == 4) {
    				
    			}else if(i == 5) {
    			
    			}else if(i == 6) {
    				
    			}
    			
    		}
    	}
    	
    	
    	
    	SoundPlayer.loadAllSounds(soundFiles);

		for (int i = 0; i < count; i++) {
		    SoundPlayer.play(soundFiles[i]);
		}
		
    }
    
    public static void ones(int number) {
    	boolean flag = false;
    	if(number > 19) {
			number = number % 10;
			flag = true;
    	}
    	
    	if(number == 0) {
    		return;
    	}else if(number == 1) {
    		soundFiles[count++] = "واحد.wav";
    	}else if(number == 2) {
    		soundFiles[count++] = "اثنان.wav";
    	}else if(number == 3) {
    		soundFiles[count++] = "ثلاثه.wav";
    	}else if(number == 4) {
    		soundFiles[count++] = "اربعه.wav";
    	}else if(number == 5) {
    		soundFiles[count++] = "خمسه.wav";
    	}else if(number == 6) {
    		soundFiles[count++] = "سته.wav";
    	}else if(number == 7) {
    		soundFiles[count++] = "سبعه.wav";
    	}else if(number == 8) {
    		soundFiles[count++] = "ثمانيه.wav";
    	}else if(number == 9) {
    		soundFiles[count++] = "تسعه.wav";
    	}else if(number == 10) {
    		soundFiles[count++] = "عشره.wav";
    	}else if(number == 11) {
    		soundFiles[count++] = "أحدعشر.wav";
    	}else if(number == 12) {
    		soundFiles[count++] = "اثناعشر.wav";
    	}else if(number == 13) {
    		soundFiles[count++] = "ثلاثةعشر.wav";
    	}else if(number == 14) {
    		soundFiles[count++] = "اربعةعشر.wav";
    	}else if(number == 15) {
    		soundFiles[count++] = "خمسةعشر.wav";
    	}else if(number == 16) {
    		soundFiles[count++] = "ستةعشر.wav";
    	}else if(number == 17) {
    		soundFiles[count++] = "سبعةعشر.wav";
    	}else if(number == 18) {
    		soundFiles[count++] = "ثمانيةعشر.wav";
    	}else if(number == 19) {
    		soundFiles[count++] = "تسعةعشر.wav";
    	}
    	
    	if(flag) {
    		soundFiles[count++] = "و.wav";;
    	}
    }
    
    public static void tens(int number) {
    	if(number == 20) {
    		soundFiles[count++] = "عشرون.wav";
    	}else if(number == 30) {
    		
    	}else if(number == 40) {
    		
    	}else if(number == 50) {
    		
    	}else if(number == 60) {
    		
    	}else if(number == 70) {
    		
    	}else if(number == 80) {
    		
    	}else if(number == 90) {
    		
    	}
    }
    
    public static void hundreds(int number) {
    	
    	System.out.println(number);
    	
    	
    	
    	
    }
    
    
    public static void thousands(int number) {
    	
    	System.out.println(number);
    	
    	
    	
    	
    }
    
    public static void tensOfThousands(int number) {
    	
    	System.out.println(number);
    	
    	
    	
    	
    }
    
    public static void hundredsOfThousands(int number) {
    	
    	System.out.println(number);
    	
    	
    	
    	
    }
    
   public static void abomunif() {
	   SoundPlayer.play("مليون.wav");
    }
    
}

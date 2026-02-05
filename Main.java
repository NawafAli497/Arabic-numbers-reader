import java.util.Scanner;

public class Main {
	
    public static void main(String[] args){
    	String realNumber = "";
    	String number[];
        
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
				return;
    		}else if(Integer.parseInt(number[0]) == 1000000) { // If its a million, play abomunif.
        		abomunif();
        		return;
        	}
    		
    		
    		for(int i = number[0].length() - 1; i >= 0 ; i--) {
    			if(i == 0) {
    				tens(Character.getNumericValue(number[0].charAt(number[0].length() - 2)) * 10);
    			}else if(i == 1) {
    				ones(Character.getNumericValue(number[0].charAt(number[0].length() - 1)));
    	    		SoundPlayer.play("و.wav");
    			}else if(i == 2) {
    				
    			}else if(i == 3) {
    				
    			}else if(i == 4) {
    				
    			}else if(i == 5) {
    			
    			}else if(i == 6) {
    				
    			}
    			
    		}
    	}
    	
    	
    	

    }
    
    public static void ones(int number) {
    	if(number == 1) {
    		SoundPlayer.play("واحد.wav");
    	}else if(number == 2) {
    		SoundPlayer.play("اثنان.wav");
    	}else if(number == 3) {
    		SoundPlayer.play("ثلاثه.wav");
    	}else if(number == 4) {
    		SoundPlayer.play("");
    	}else if(number == 5) {
    		SoundPlayer.play("");
    	}else if(number == 6) {
    		SoundPlayer.play("");
    	}else if(number == 7) {
    		SoundPlayer.play("");
    	}else if(number == 8) {
    		SoundPlayer.play("");
    	}else if(number == 9) {
    		SoundPlayer.play("");
    	}else if(number == 10) {
    		SoundPlayer.play("");
    	}else if(number == 11) {
    		SoundPlayer.play("");
    	}else if(number == 12) {
    		SoundPlayer.play("");
    	}else if(number == 13) {
    		SoundPlayer.play("");
    	}else if(number == 14) {
    		SoundPlayer.play("");
    	}else if(number == 15) {
    		SoundPlayer.play("");
    	}else if(number == 16) {
    		SoundPlayer.play("");
    	}else if(number == 17) {
    		SoundPlayer.play("");
    	}else if(number == 18) {
    		SoundPlayer.play("");
    	}else if(number == 19) {
    		SoundPlayer.play("");
    	}
    	

    }
    
    public static void tens(int number) {
    	
    	if(number == 20) {
    		SoundPlayer.play("عشرون.wav");
    	}else if(number == 30) {
    		
    	}else if(number == 40) {
    		
    	}else if(number == 50) {
    		
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

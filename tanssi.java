package Peli;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class tanssi extends Thread {
	
	/**Luodaan muuttujat*/
	private EV3LargeRegulatedMotor largeMotor1;
	private EV3LargeRegulatedMotor largeMotor2;
	private EV3MediumRegulatedMotor mediumMotor1;
	private EV3MediumRegulatedMotor mediumMotor2;
	
	/**Metodi joka antaa moottoreille nimet*/
    public tanssi(final EV3LargeRegulatedMotor motor1, final EV3LargeRegulatedMotor motor2, final EV3MediumRegulatedMotor motor3, final EV3MediumRegulatedMotor motor4){
        this.largeMotor1 = motor1;	//a
        this.largeMotor2 = motor2;	//b
        this.mediumMotor1 = motor3;	//c
        this.mediumMotor2 = motor4;	//d
    }
    
	/**Luodaan tarvittava muuttuja tanssin aloittamiseksi ja lopettamiseksi*/
	private boolean tanssi = matka.getTanssi();
	
	@Override
    public void run() {
		while(Button.ESCAPE.isUp()) {
			
			tanssi = matka.getTanssi();
			
			/**Tanssimiseen tarvittava while loop*/
            while (tanssi == true) {
            	mediumMotor1.rotateTo(75);
                largeMotor1.rotateTo(100);
                mediumMotor2.rotate(2000);
                mediumMotor1.rotateTo(-75);
                largeMotor2.rotateTo(-100);
                tanssi = matka.getTanssi();
                largeMotor2.rotateTo(100);
                mediumMotor1.rotateTo(75);
                mediumMotor2.rotate(-2000);
                mediumMotor1.rotateTo(-75);
                largeMotor1.rotateTo(-100);
                tanssi = matka.getTanssi();
                
                /**Varmistetaan että tanssittaessa käytetyt moottorit ovat oikeassa kohtaa tanssin loputtua*/
                if (tanssi == false) {
                	mediumMotor2.rotateTo(0);
                	mediumMotor2.stop();
                }
            }
            
            
		}
	}
}
package Peli;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.utility.Delay;

public class liike extends Thread{
	
	/**Luodaan muuttujat*/
	private static EV3LargeRegulatedMotor largeMotor1;
	private static EV3LargeRegulatedMotor largeMotor2;
	private static EV3MediumRegulatedMotor mediumMotor1;
	private static EV3MediumRegulatedMotor mediumMotor2;
	
	/**Metodi joka antaa moottoreille nimet*/
    public liike(final EV3LargeRegulatedMotor motor1, final EV3LargeRegulatedMotor motor2, final EV3MediumRegulatedMotor motor3, final EV3MediumRegulatedMotor motor4){
        this.largeMotor1 = motor1;	//a
        this.largeMotor2 = motor2;	//b
        this.mediumMotor1 = motor3;	//c
        this.mediumMotor2 = motor4;	//d
    }
    
    /**Konfiguroidaan moottoreiden oletusnopeudet- ja kiihtyvyydet*/
	public static void moottoriSaadot() {
		largeMotor1.setSpeed(1020);
		largeMotor2.setSpeed(1020);
		mediumMotor1.setSpeed(1450);
		mediumMotor2.setSpeed(1450);
        largeMotor1.setAcceleration(6000);
        largeMotor2.setAcceleration(6000);
        mediumMotor1.setAcceleration(1500);
        mediumMotor2.setAcceleration(2500);
	}
	
	/**Kun mitään nappia ei paineta, moottorit pysähdyksissä*/
	public static void seis() {
		largeMotor1.startSynchronization();
		largeMotor1.stop();
		largeMotor2.stop();
		mediumMotor1.stop();
		mediumMotor2.stop();
        largeMotor1.endSynchronization();
	}

	/**Eteen ja vasemmalle*/
	public static void eteenVasen() {
		largeMotor1.forward();
		largeMotor2.forward();
		mediumMotor1.rotateTo(75, true);
	}

	/**Taakse ja vasemmalle*/
	public static void taakseVasen() {
		largeMotor1.backward();
		largeMotor2.backward();
		mediumMotor1.rotateTo(75, true);
	}
	
	/**Eteen ja oikealle*/
	public static void eteenOikea() {
		largeMotor1.forward();
		largeMotor2.forward();
		mediumMotor1.rotateTo(-75, true);
	}

	/**Taakse ja oikealle*/
	public static void taakseOikea() {
		largeMotor1.backward();
		largeMotor2.backward();
		mediumMotor1.rotateTo(-75);
	}

	/**Eteen*/
	public static void eteen() {
		mediumMotor1.rotateTo(0);
		largeMotor1.startSynchronization();
		largeMotor1.forward();
		largeMotor2.forward();
        largeMotor1.endSynchronization();
	}

	/**Taakse*/
	public static void taakse() {
		mediumMotor1.rotateTo(0);
		largeMotor1.startSynchronization();
		largeMotor1.backward();
		largeMotor2.backward();
        largeMotor1.endSynchronization();
	}
	
	/**Suljetaan kaikki moottorit*/
	public static void sulku() {
		largeMotor1.close();
		largeMotor2.close();
		mediumMotor1.close();
		mediumMotor2.close();
	}
	
	/**Jos robotti törmää*/
	public static void tormays() {
		/**Pysäytetään moottorit*/
		largeMotor1.startSynchronization();
		largeMotor1.stop();
		largeMotor2.stop();
        largeMotor1.endSynchronization();
        /**Sytytetään merkkivalo, näytölle teksti ja soitetaan varoitusääni*/
        Button.LEDPattern(2);
        LCD.drawString("TORMAYS!", 0, 3);
        Sound.systemSound(true, 4);
        Delay.msDelay(1000);
        /**Sammutetaan merkkivalo ja pyyhitään teksti*/
        Button.LEDPattern(0);
        LCD.clear(3);
	}
}
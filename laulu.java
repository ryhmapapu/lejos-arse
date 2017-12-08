package Peli;

import java.io.File;
import Peli.matka;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class laulu extends Thread {
	
	/**Luodaan tarvittava muuttuja laulun aloittamiseksi ja lopettamiseksi*/
	private boolean dance;
	
	@Override
    public void run() {
		while(Button.ESCAPE.isUp()) {
			
			/**Määritetään mistä muuttuja "tanssi" saa arvonsa*/
			dance = matka.getTanssi();
			
			/**Tanssiessa aloittaa musiikin ja tanssin loputtua soittaa merkkiäänen*/
			if(dance == true) {
				File music = new File("anaconda3.wav");
				Sound.playSample(music, 100);
				//Sound.setVolume(100);
				while(dance == true) {
					dance = matka.getTanssi();
					if(dance == false) {
						Sound.beep();
					}
				}
			}
		}
	}
}
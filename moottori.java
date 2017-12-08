package Peli;

import Peli.matka;
import Peli.laulu;
import Peli.liike;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
 
public class moottori  {
    public static void main(String[] args) {
       
        /**Luodaan robotin neljä moottoria*/
    	//ÄLÄ VÄLITÄ ERROR CODEISTA, MOOTTORIT KYLLÄ SULJETAAN!
        EV3LargeRegulatedMotor a = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor b = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3MediumRegulatedMotor c = new EV3MediumRegulatedMotor(MotorPort.C);
        EV3MediumRegulatedMotor d = new EV3MediumRegulatedMotor(MotorPort.D);
       
        /**Luodaan robotin sensorit*/
        EV3IRSensor infraredSensor = new EV3IRSensor(SensorPort.S1);
        EV3TouchSensor touchSensor = new  EV3TouchSensor(SensorPort.S2);
       
        /**Printataan näytölle teksti toiminnan varmistamiseksi*/
        LCD.clear();
        LCD.drawString("EV3 IR Beacon", 0, 1);
       
        /**Luodaan threadit ja käynnistetään ne*/
        matka matkaThread = new matka(infraredSensor, touchSensor);
        matkaThread.start();
        laulu lauluThread = new laulu();
        lauluThread.start();
        tanssi tanssiThread = new tanssi(a, b, c, d);
        tanssiThread.start();
        liike liikeThread = new liike(a, b, c, d);
        liikeThread.start();
        
        /**Synkronoidaan päämoottori A päämoottori B:n kanssa*/
        a.synchronizeWith(new RegulatedMotor[]{b});
        
        /**Konfiguroidaan moottoreiden oletusnopeudet- ja kiihtyvyydet*/
        liike.moottoriSaadot();
        
        while (Button.ESCAPE.isUp()) {
        	
            /**Luodaan muuttuja, joka saa arvonsa matka-luokan metodilta*/
            int etaisyys = matka.getEtaisyys();
           
            /**Jos robotti törmää*/
            if (etaisyys < 10) {
                liike.tormays();
            }
           
            /**Määritelään käytettävä kaukosäädin kanava ja luodaan "switch", jonka sisällä kaukosäätimen toiminnot*/
            int kaukosaadin0 = matka.getKauko2();
            switch (kaukosaadin0){
           
                /**Kun mitään nappia ei paineta, moottorit pysähdyksissä*/
                case 0:
                liike.seis();
                break;
               
                /**Eteen ja vasemmalle*/
                case 1:
                liike.eteenVasen();
                break;
               
                /**Taakse ja vasemmalle*/
                case 2:
                liike.taakseVasen();
                break;
               
                /**Eteen ja oikealle*/
                case 3:
                liike.eteenOikea();
                break;
               
                /**Taakse ja oikealle*/
                case 4:
                liike.taakseOikea();
                break;
               
                /**Eteen*/
                case 5:
                liike.eteen();
                break;
               
                /**Taakse*/
                case 8:
                liike.taakse();
                break;
            }
        }
        /**Suljetaan kaikki moottorit lopuksi*/
        liike.sulku();
    }
}
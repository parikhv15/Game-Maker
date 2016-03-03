package model;

import java.io.File;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import static model.GameConstants.SOUND_RES_PATH;
import model.GameConstants.SoundMode;

//class to play sound
public class PlaySound {
	
	   private AudioFormat audioFormat;
	   private AudioInputStream audioInputStream;
	   private SourceDataLine sourceDataLine;
	   private File file;
	   private String ballpath;
	   //Sets file path here
	   public PlaySound() {
	
	}

//play sound
	public void playSoundMethod(SoundMode soundMode) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		switch (soundMode) {
		case COLLISION:
			ballpath = SOUND_RES_PATH + "Ding.wav";
			break;
		case ERROR_MESSAGE:
			ballpath = SOUND_RES_PATH + "WrongBuzzer.wav";
			break;
		default:
			break;

		}
		   
		try {
			file = new File(getClass().getClassLoader().getResource(ballpath).toURI());
			audioInputStream = AudioSystem.getAudioInputStream(file);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		   	     
		      audioFormat = audioInputStream.getFormat();
		      DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
			                                    audioFormat);

			      sourceDataLine =   (SourceDataLine)AudioSystem.getLine(dataLineInfo);

			      new PlayThread().start();	   
	   }
	   
	
	//inner class
	   class PlayThread extends Thread{
		   byte tempBuffer[] = new byte[10000];

		   public void run(){
		     try{
		       sourceDataLine.open(audioFormat);
		       sourceDataLine.start();

		       int cnt;
		       while((cnt = audioInputStream.read(
		            tempBuffer,0,tempBuffer.length)) != -1){
		         if(cnt > 0){
		           sourceDataLine.write(
		                              tempBuffer, 0, cnt);
		         }
		       }
		       sourceDataLine.drain();
		       sourceDataLine.close();
		     }catch (Exception e) {
		       e.printStackTrace();
		       System.exit(0);
		     }
		   }
		 }
}

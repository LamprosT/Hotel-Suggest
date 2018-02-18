package hotelmanagement_beanswork;

import javax.swing.JFrame;


/**
 *
 * @LamprosTzanetos LambrosTzanetos
 */
public class Singleton {

   private static Singleton instance = null;
   public MainMenu mainData;
   public String displayOrder;
   public JFrame hotelDisplaySing;
   public String hotelID;
   public BasicPersonInfo browsingUser;
   
   private Singleton() {
      // Exists only to defeat instantiation.
   }

   public static Singleton getInstance() {
      if(instance == null) {
         instance = new Singleton();
      }
      return instance;
   }
}
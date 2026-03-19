/**
 *
 *  @author Król Bartosz s31572
 *
 */

package zad2;

import zad2.gui.GUIApplicationStarter;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Italy");
    String weatherJson = s.getWeather("Rome");
    Double rate1 = s.getRateFor("THB");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    GUIApplicationStarter.startGuiApplication();
  }
}

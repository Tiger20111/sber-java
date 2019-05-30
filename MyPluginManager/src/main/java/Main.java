import ru.tiger20111.pluginmanager.Plugin;
import ru.tiger20111.pluginmanager.PluginManager;

public class Main {

  public static void main(String[] args) throws Exception {

    PluginManager pluginManager = new PluginManager("target");
    Plugin plugin = pluginManager.load("test-classes", "PluginManager");
    plugin.doUseful();

  }
}

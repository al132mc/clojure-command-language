package al132.clojurecommandlanguage;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = ClojureCommandLanguage.MODID, name = ClojureCommandLanguage.NAME, version = ClojureCommandLanguage.VERSION)
public class ClojureCommandLanguage {
    public static final String MODID = "clojurecommandlanguage";
    public static final String NAME = "Clojure Command Language";
    public static final String VERSION = "1.0";

    public static Logger logger;
    public static File configDir;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();

        IFn readString = Clojure.var("clojure.core", "read-string");
        IFn eval = Clojure.var("clojure.core", "eval");
        eval.invoke(readString.invoke("(defn dub [n] (* 2 n))"));
        logger.info(eval.invoke(readString.invoke("(dub 20)")));
        configDir = new File(e.getModConfigurationDirectory().getParent(), "ccl_scripts");
        if (!configDir.exists()) configDir.mkdir();
        /*File exampleFile = new File(configDir, "test.clj");
        if (!exampleFile.exists()) {
            try {
                //new FileWriter(exampleFile).write("(println \"test\")");
            } catch (Exception exception) {
                //oh lawdy, we silently failing out here
            }
        }*/
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CCLEvalCommand());
        event.registerServerCommand(new CCLFileEvalCommand());
    }
}

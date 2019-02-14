package al132.clojurecommandlanguage;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CCLFileEvalCommand extends CommandBase {

    IFn eval;
    IFn readString;
    IFn loadFile;

    public CCLFileEvalCommand() {
        readString = Clojure.var("clojure.core", "read-string");
        eval = Clojure.var("clojure.core", "eval");
        loadFile = Clojure.var("clojure.core", "load-file");
        eval.invoke(readString.invoke("(import al132.clojurecommandlanguage.lib)"));
    }

    @Override
    public String getName() {
        return "cclfile";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "cclfile <filename(without extension)>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        lib.context = new CommandContext(server, sender, args);
        if (args.length != 1) {
            notifyCommandListener(sender, this, "Invalid arguments, requires 1 argument");
        } else {
            String path = ClojureCommandLanguage.configDir.getPath() + "/" + args[0] + ".clj";
            loadFile.invoke(path);
        }
        lib.context = null;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
package al132.clojurecommandlanguage;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CCLEvalCommand extends CommandBase {

    IFn eval;
    IFn readString;

    public CCLEvalCommand() {
        readString = Clojure.var("clojure.core", "read-string");
        eval = Clojure.var("clojure.core", "eval");
        eval.invoke(readString.invoke("(import al132.clojurecommandlanguage.lib)"));
    }

    @Override
    public String getName() {
        return "ccl";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "ccl <input>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        lib.context = new CommandContext(server, sender, args);
        String concatenatedStr = String.join(" ", args).replace("|", "/");

        try {
            String result = eval.invoke(readString.invoke(concatenatedStr)).toString();
            if (result != null) sender.sendMessage(new TextComponentString(result));
            else sender.sendMessage(new TextComponentString("null"));
        } catch (NullPointerException e) {
            sender.sendMessage(new TextComponentString("nil"));
        } catch (Exception e) {
            if (e.getCause() != null) {
                String message = e.getCause().getMessage();
                if (message != null) {
                    System.out.println("caught: " + message);
                    sender.sendMessage(new TextComponentString(message));
                }
            } else sender.sendMessage(new TextComponentString(e.toString()));
        }
        lib.context = null;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
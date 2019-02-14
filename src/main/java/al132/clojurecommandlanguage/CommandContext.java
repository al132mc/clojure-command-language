package al132.clojurecommandlanguage;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandContext {
    public MinecraftServer server;
    public ICommandSender sender;
    public String[] args;

    public CommandContext(MinecraftServer server, ICommandSender sender, String[] args){
        this.server = server;
        this.sender = sender;
        this.args = args;
    }
}
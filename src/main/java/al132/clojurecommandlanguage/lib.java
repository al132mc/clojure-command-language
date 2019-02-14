package al132.clojurecommandlanguage;

import clojure.lang.PersistentVector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.stream.Collectors;

public class lib {

    public static CommandContext context;

    public static void clearblock(int x, int y, int z) {
        if (context != null) {
            World world = context.sender.getEntityWorld();
            System.out.println("setting " + x + " " + y + " " + z + " to air");
            world.setBlockToAir(new BlockPos(x, y, z));
            System.out.println(world.getBlockState(new BlockPos(x, y, z)).getBlock().toString());
        } else System.out.println("Context is null?!");
    }

    public static void exec(PersistentVector args) {
        if (context != null) {
            String parsedArgs = Arrays.stream(args.toArray()).map(Object::toString).collect(Collectors.joining(""));
            int i = context.server.getCommandManager().executeCommand(context.sender, parsedArgs);
        } else System.out.println("Context is null?!");
    }
}
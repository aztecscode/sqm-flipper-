package de.torui.coflsky;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import de.torui.coflsky.websocket.WSClient;
import de.torui.coflsky.websocket.WSClientWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = CoflSky.MODID, version = CoflSky.VERSION)
public class CoflSky
{
    public static final String MODID = "CoflSky";
    public static final String VERSION = "1.0";
    public static WSClientWrapper Wrapper;
    
    public static String PlayerUUID = "";
    
    @EventHandler
    public void init(FMLInitializationEvent event) throws URISyntaxException
    {
    	
    	//Minecraft.getSessionInfo().forEach((a,b) -> System.out.println("Key=" + a + " value=" + b));
    	
    	//System.out.println("Loggerfactory: " + LoggerFactory.getILoggerFactory());
    //	Logger log = LoggerFactory.getLogger(CoflSky.class);
   // 	log.debug("Testing");
    	
		// some example code
        System.out.println("Initializing");
        
        //new Thread(new WSClient(new URI("ws://localhost:8080"))).start();        
        System.out.println(">>>Started");
        
       
        CoflSky.Wrapper = new WSClientWrapper("wss://sky-commands.coflnet.com/modsocket?uuid=");
        
        if(event.getSide() == Side.CLIENT)
        	ClientCommandHandler.instance.registerCommand(new CoflSkyCommand());
        MinecraftForge.EVENT_BUS.register(new EventRegistry());	   
    }   
    

   /* @EventHandler
    public void init(FMLServerStartingEvent event)
    {
    	
    	if(event.getSide() == Side.CLIENT)    	return;
    		//event.registerServerCommand(new CoflSkyCommand());
    }*/
    
}
	
package com.spaceboyross.gundam.net.client;

import com.spaceboyross.gundam.capabilities.human.Human;
import com.spaceboyross.gundam.capabilities.interfaces.IHumanCapability;
import com.spaceboyross.gundam.enums.EHumantypes;
import com.spaceboyross.gundam.ms.MobileSuit;
import com.spaceboyross.gundam.net.server.PacketHumanServer;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHumanClient implements IMessage {
	
	private boolean shownHumantypeMenu = false;
	private int humantype = 0;
	private int msID = -1;
	
	public PacketHumanClient() {
	}
	
	public PacketHumanClient(IHumanCapability nt) {
		this.shownHumantypeMenu = nt.hasShownHumantypeMenu();
		this.humantype = nt.getHumantype().ordinal();
		if(nt.getMS() != null) this.msID = nt.getMS().getEntityId();
	}

    @Override
    public void fromBytes(ByteBuf buf) {
    	this.shownHumantypeMenu = buf.readBoolean();
    	this.humantype = buf.readInt();
    	this.msID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeBoolean(this.shownHumantypeMenu);
    	buf.writeInt(this.humantype);
    	buf.writeInt(this.msID);
    }

    public static class Handler implements IMessageHandler<PacketHumanClient,IMessage> {
        @Override
        public IMessage onMessage(PacketHumanClient message,MessageContext ctx) {
        	Minecraft.getMinecraft().addScheduledTask(() -> handle(message,ctx));
            return null;
        }

        private void handle(PacketHumanClient message,MessageContext ctx) {
            EntityPlayer playerEntity = Minecraft.getMinecraft().player;
            World world = playerEntity.getEntityWorld();
            IHumanCapability nt = Human.getHuman(playerEntity);
            nt.setHasShownHumantypeMenu(message.shownHumantypeMenu);
            nt.setHumantype(EHumantypes.values()[message.humantype]);
            if(message.msID != -1) nt.setMS((MobileSuit.MSMob)world.getEntityByID(message.msID));
        }
    }

}
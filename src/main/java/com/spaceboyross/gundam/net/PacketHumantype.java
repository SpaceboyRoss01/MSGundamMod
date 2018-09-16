package com.spaceboyross.gundam.net;

import com.spaceboyross.gundam.gui.HumantypeGUI;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHumantype implements IMessage {
	
	public PacketHumantype() {}
	
	@Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}
    
    public static class Handler implements IMessageHandler<PacketHumantype,IMessage> {
        @Override
        public IMessage onMessage(PacketHumantype message,MessageContext ctx) {
        	Minecraft.getMinecraft().addScheduledTask(() -> handle(message,ctx));
        	return null;
        }
        
        private void handle(PacketHumantype message,MessageContext ctx) {
        	Minecraft.getMinecraft().displayGuiScreen(new HumantypeGUI(Minecraft.getMinecraft().player));
        }
    }
}
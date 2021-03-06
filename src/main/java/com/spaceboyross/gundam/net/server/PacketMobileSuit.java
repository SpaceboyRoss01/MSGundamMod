package com.spaceboyross.gundam.net.server;

import java.lang.reflect.InvocationTargetException;

import com.spaceboyross.gundam.ms.MSRegistry;
import com.spaceboyross.gundam.ms.MobileSuit;
import com.spaceboyross.gundam.utils.InventoryUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMobileSuit implements IMessage {

	public int index = 0;
	public Vec3d pos = new Vec3d(0.0,0.0,0.0);
	public IInventory inventory = null;
	public EnumMobileSuitCommand action = EnumMobileSuitCommand.SPAWN;

    public PacketMobileSuit() {
    }
    
    public PacketMobileSuit(int index,Vec3d pos) {
    	this.index = index;
    	this.pos = pos;
    	this.action = EnumMobileSuitCommand.SPAWN;
    }
    
    public PacketMobileSuit(MobileSuit.MSEntity entity) {
    	this.index = entity.getEntityId();
    	this.pos = new Vec3d(entity.posX,entity.posY,entity.posZ);
    	this.inventory = entity.inventory;
    	this.action = EnumMobileSuitCommand.UPDATE;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	this.action = EnumMobileSuitCommand.values()[buf.readInt()];
    	this.index = buf.readInt();
    	this.pos = new Vec3d(buf.readDouble(),buf.readDouble(),buf.readDouble());
    	if(this.action == EnumMobileSuitCommand.UPDATE) {
    		try {
				this.inventory = InventoryUtils.fromByteBuf(buf);
			} catch(NBTException e) {
				e.printStackTrace();
			}
    	}
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(this.action.ordinal());
    	buf.writeInt(this.index);
    	buf.writeDouble(this.pos.x);
    	buf.writeDouble(this.pos.y);
    	buf.writeDouble(this.pos.z);
    	if(this.action == EnumMobileSuitCommand.UPDATE) {
    		buf = InventoryUtils.toByteBuf(buf,this.inventory);
    	}
    }
    
    public static enum EnumMobileSuitCommand {
    	SPAWN,
    	UPDATE
    }

    public static class Handler implements IMessageHandler<PacketMobileSuit,IMessage> {
        @Override
        public IMessage onMessage(PacketMobileSuit message,MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> handle(message,ctx));
            return null;
        }

        private void handle(PacketMobileSuit message,MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            switch(message.action) {
            	case SPAWN:
                    MobileSuit ms = MSRegistry.getMobileSuitFromIndex(message.index);
            		world.spawnEntity(ms.createEntity(world,new Vec3d(message.pos.x+1.0,message.pos.y+1.0,message.pos.z+1.0)));
            		break;
            	case UPDATE:
            		Entity entityTemp = world.getEntityByID(message.index);
            		if(entityTemp instanceof MobileSuit.MSEntity) {
            			MobileSuit.MSEntity entity = (MobileSuit.MSEntity)entityTemp;
            		}
            		break;
            	default: break;
            }
        }
    }
}

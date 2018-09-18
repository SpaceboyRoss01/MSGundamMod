package com.spaceboyross.gundam.capabilities.human;

import org.lwjgl.opengl.GL11;

import com.spaceboyross.gundam.GundamMod;
import com.spaceboyross.gundam.capabilities.interfaces.IHumanCapability;
import com.spaceboyross.gundam.capabilities.providers.SimpleProvider;
import com.spaceboyross.gundam.enums.EHumantypes;
import com.spaceboyross.gundam.ms.MobileSuit;
import com.spaceboyross.gundam.net.client.PacketGUI;
import com.spaceboyross.gundam.net.client.PacketHumanClient;
import com.spaceboyross.gundam.utils.CapabilityUtils;
import com.spaceboyross.gundam.utils.NetworkUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class Human {
	@CapabilityInject(IHumanCapability.class)
	public static final Capability<IHumanCapability> MAX_HUMAN_CAPABILITY = null;
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	public static final ResourceLocation ID = new ResourceLocation(GundamMod.MODID,"Newtype");
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IHumanCapability.class,new Capability.IStorage<IHumanCapability>() {
			@Override
			public NBTBase writeNBT(Capability<IHumanCapability> capability,IHumanCapability instance,EnumFacing side) {
				NBTTagCompound root = new NBTTagCompound();
				root.setBoolean("shownHumantypeMenu",instance.hasShownHumantypeMenu());
				root.setInteger("humantype",instance.getHumantype().ordinal());
				return root;
			}

			@Override
			public void readNBT(Capability<IHumanCapability> capability,IHumanCapability instance,EnumFacing side,NBTBase nbt) {
				NBTTagCompound root = (NBTTagCompound)nbt;
				instance.setHasShownHumantypeMenu(root.getBoolean("shownHumantypeMenu"));
				instance.setHumantype(EHumantypes.values()[root.getInteger("humantype")]);
			}
		},() -> new HumanCapability(null));
	}
	
	public static ICapabilityProvider createProvider(IHumanCapability nc) {
		return new SimpleProvider<IHumanCapability>(MAX_HUMAN_CAPABILITY,DEFAULT_FACING,nc);
	}
	
	public static IHumanCapability getHuman(EntityPlayer entity) {
		return (IHumanCapability)CapabilityUtils.getCapability(entity,MAX_HUMAN_CAPABILITY,DEFAULT_FACING);
	}
	
	@Mod.EventBusSubscriber
	public static class EventHandler {
		@SubscribeEvent
		public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if(event.getObject() instanceof EntityPlayer) {
				final HumanCapability nc = new HumanCapability((EntityPlayer)event.getObject());
				event.addCapability(ID,createProvider(nc));
			}
		}
		
		@SubscribeEvent
		public static void hasConnected(PlayerEvent.PlayerLoggedInEvent event) {
			IHumanCapability nt = CapabilityUtils.getCapability(event.player,MAX_HUMAN_CAPABILITY,DEFAULT_FACING);
			if(!nt.hasShownHumantypeMenu()) NetworkUtils.sendToClient(new PacketGUI(0),event.player);
			else NetworkUtils.sendToClient(new PacketHumanClient(nt),event.player);
		}
		
		@SubscribeEvent
		public static void playerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
			IHumanCapability original = getHuman(event.getOriginal());
			IHumanCapability nt = getHuman(event.getEntityPlayer());
			
			if(original != null && nt != null) {
				nt.setHasShownHumantypeMenu(original.hasShownHumantypeMenu());
				nt.setHumantype(original.getHumantype());
			}
		}
		
		@SubscribeEvent
		public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
			EntityPlayer player = event.getEntityPlayer();
			IHumanCapability nt = Human.getHuman(player);
			MobileSuit.MSMob msmob = nt.getMS();
			if(msmob != null) {
				MobileSuit ms = msmob.getMSRegistryEntry();
				event.getRenderer().bindTexture(new ResourceLocation(ms.getBaseResourceLocation()+".png"));
				event.setCanceled(true);
				GlStateManager.pushMatrix();
				event.getRenderer().getMainModel().bipedHead.render(0.0625F*msmob.scale);
				event.getRenderer().getMainModel().bipedBody.render(0.0625F*msmob.scale);
				event.getRenderer().getMainModel().bipedLeftLeg.render(0.0625F*msmob.scale);
				event.getRenderer().getMainModel().bipedRightLeg.render(0.0625F*msmob.scale);
				event.getRenderer().getMainModel().bipedLeftArm.render(0.0625F*msmob.scale);
				event.getRenderer().getMainModel().bipedRightArm.render(0.0625F*msmob.scale);
				GlStateManager.popMatrix();
			}
		}
	}
}
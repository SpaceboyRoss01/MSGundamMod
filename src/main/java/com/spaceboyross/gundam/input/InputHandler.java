package com.spaceboyross.gundam.input;

import com.spaceboyross.gundam.GundamMod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class InputHandler {
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(KeyBindings.useVernier.isPressed() && Minecraft.getMinecraft().player.inventory.armorItemInSlot(2).getItem().getUnlocalizedName().equals("item."+GundamMod.MODID+".portable_vernier") && Minecraft.getMinecraft().player.motionY <= 0.0) {
			Minecraft.getMinecraft().player.addVelocity(Minecraft.getMinecraft().player.motionX,0.7+Minecraft.getMinecraft().player.motionY,Minecraft.getMinecraft().player.motionY);
		}
	}
}
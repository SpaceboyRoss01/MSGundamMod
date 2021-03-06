package com.spaceboyross.gundam.gui.hud;

import java.util.List;

import com.spaceboyross.gundam.capabilities.interfaces.IHumanCapability;
import com.spaceboyross.gundam.ms.MobileSuit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;

public class NewtypeHUD extends Gui {
	
	public NewtypeHUD(Minecraft mc,IHumanCapability human) {
		ScaledResolution scaled = new ScaledResolution(mc);
		
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		
		this.drawString(mc.fontRenderer,I18n.format("hud.gundam.newtype.name"),0,0,0xFFFFFF);
		
		List<Entity> within = human.getEntitiesWithinRange();
		int i = 0;
		for(Entity e : within) {
			if(e instanceof EntityLiving) {
				if(human.getPlayer().getRidingEntity() != null && human.getPlayer().getRidingEntity().getEntityId() == e.getEntityId()) continue;
				Vec3d diff = new Vec3d(e.posX,e.posY,e.posZ).subtract(human.getPlayer().posX,human.getPlayer().posY,human.getPlayer().posZ);
				this.drawString(mc.fontRenderer,I18n.format("hud.gundam.newtype.mob",e.getName(),((EntityLiving)e).getHealth(),diff.x,diff.y,diff.z),0,mc.fontRenderer.FONT_HEIGHT+i++*mc.fontRenderer.FONT_HEIGHT,0xFFFFFF);
				if(i == 5) break;
			}
		}
	}
}

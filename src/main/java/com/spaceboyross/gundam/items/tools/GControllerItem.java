package com.spaceboyross.gundam.items.tools;

import java.util.List;

import com.spaceboyross.gundam.GundamMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GControllerItem extends Item {

	public GControllerItem() {
		this.setRegistryName("g_controller");
		this.setUnlocalizedName(GundamMod.MODID+".g_controller");
		this.setCreativeTab(GundamMod.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack,World world,List<String> tooltip,ITooltipFlag flag) {
		tooltip.add(I18n.format("item.gundam.g_controller.desc"));
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] {
			GundamMod.tab,CreativeTabs.TOOLS
		};
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(this.getRegistryName(),"inventory"));
	}

}

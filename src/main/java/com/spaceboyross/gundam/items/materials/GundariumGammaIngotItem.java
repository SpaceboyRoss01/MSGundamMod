package com.spaceboyross.gundam.items.materials;

import com.spaceboyross.gundam.GundamMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GundariumGammaIngotItem extends Item {

	public GundariumGammaIngotItem() {
		this.setRegistryName("gundarium_gamma_ingot");
		this.setUnlocalizedName(GundamMod.MODID+".gundarium_gamma_ingot");
		this.setCreativeTab(GundamMod.tab);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] {
			GundamMod.tab,CreativeTabs.MATERIALS
		};
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
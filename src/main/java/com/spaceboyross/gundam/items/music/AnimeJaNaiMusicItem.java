package com.spaceboyross.gundam.items.music;

import com.spaceboyross.gundam.GundamMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimeJaNaiMusicItem extends ItemRecord {

	public AnimeJaNaiMusicItem() {
		super("anime_ja_nai",new SoundEvent(new ResourceLocation(GundamMod.MODID,"anime_ja_nai")));
		this.setRegistryName("music_anime_ja_nai");
		this.setUnlocalizedName(GundamMod.MODID+".music_anime_ja_nai");
		this.setCreativeTab(GundamMod.tab);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] {
			GundamMod.tab,CreativeTabs.MISC
		};
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(getRegistryName(),"inventory"));
	}

}

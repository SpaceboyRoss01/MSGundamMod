package com.spaceboyross.gundam.items.weapons;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonParser;
import com.spaceboyross.gundam.GundamMod;
import com.spaceboyross.gundam.utils.NBTUtilities;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MobileSuitArmamentItem extends Item {
	
	public MobileSuitArmamentItem(String id) {
		this.setRegistryName("ms_armament_"+id);
		this.setUnlocalizedName(GundamMod.MODID+".ms_armament_"+id);
	}
	
	@Override
	public ItemStack getDefaultInstance() {
		ItemStack itemStack = super.getDefaultInstance();
		this.initNBT(itemStack);
		return itemStack;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		itemStack = super.getContainerItem(itemStack);
		this.initNBT(itemStack);
		return itemStack;
	}
	
	private void initNBT(ItemStack stack) {
		NBTTagCompound nbt = new NBTTagCompound();
		if(stack.hasTagCompound()) nbt = stack.getTagCompound();
		stack.setTagCompound(this.initNBT(nbt));
		this.setMaxStackSize(stack.getCount());
	}
	
	private NBTTagCompound initNBT(NBTTagCompound nbt) {
		return nbt;
	}
	
	@Override
	public void addInformation(ItemStack stack,World world,List<String> tooltip,ITooltipFlag flag) {
		this.initNBT(stack);
		if(I18n.hasKey("item.gundam."+this.getRegistryName().getResourcePath()+".desc")) {
			tooltip.add(I18n.format("item.gundam."+this.getRegistryName().getResourcePath()+".desc",NBTUtilities.nbtToObjectArray(stack.getTagCompound())));
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
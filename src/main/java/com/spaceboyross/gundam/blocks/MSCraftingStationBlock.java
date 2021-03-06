package com.spaceboyross.gundam.blocks;

import java.util.List;

import com.spaceboyross.gundam.GundamMod;
import com.spaceboyross.gundam.blocks.tile.MSCraftingStationTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MSCraftingStationBlock extends Block {
	
	public static final int GUI_ID = 1;

	public MSCraftingStationBlock() {
		super(Material.IRON);
		this.setUnlocalizedName(GundamMod.MODID+".ms_crafting_station");
		this.setRegistryName("ms_crafting_station");
		this.setCreativeTab(GundamMod.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack,World world,List<String> tooltip,ITooltipFlag flag) {
		tooltip.add(I18n.format("tile.gundam.ms_crafting_station.desc"));
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0,new ModelResourceLocation(this.getRegistryName(),"inventory"));
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World worldIn,IBlockState state) {
		return new MSCraftingStationTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing side,float hitX,float hitY,float hitZ) {
		if(worldIn.isRemote) return true;
		TileEntity te = worldIn.getTileEntity(pos);
		if(!(te instanceof MSCraftingStationTileEntity)) return false;
		playerIn.openGui(GundamMod.instance,MSCraftingStationBlock.GUI_ID,worldIn,pos.getX(),pos.getY(),pos.getZ());
		return true;
	}
}

package com.spaceboyross.gundam.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class MSCraftingStationTileEntity extends TileEntity {
	
	@Override
    public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return compound;
	}
	
	public boolean canInteractWith(EntityPlayer playerIn) {
        return !this.isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
		return super.hasCapability(capability,facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
		 return super.getCapability(capability,facing);
	}
}
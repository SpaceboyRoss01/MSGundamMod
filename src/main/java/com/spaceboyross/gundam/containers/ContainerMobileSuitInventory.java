package com.spaceboyross.gundam.containers;

import com.spaceboyross.gundam.ms.MobileSuit;
import com.spaceboyross.gundam.net.PacketHandler;
import com.spaceboyross.gundam.net.server.PacketMobileSuit;
import com.spaceboyross.gundam.utils.PlayerUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ContainerMobileSuitInventory extends Container {
	
	private MobileSuit.MSEntity msEntity;
    private final int numRows;
	
	public ContainerMobileSuitInventory(MobileSuit.MSEntity msEntity) {
		this.msEntity = msEntity;
		this.numRows = this.msEntity.inventory.getSizeInventory()/9;
		int i = (this.numRows-4)*18;
		
		int idx = 0;
		for(int j = 0;j < this.numRows;j++) {
            for(int k = 0;k < 9;k++) {
                this.addSlotToContainer(new Slot(this.msEntity.inventory,idx++,8+k*18,18+j*18));
            }
        }
		
		for(i = 0;i < this.msEntity.inventory.getSizeInventory();i++) {
			this.inventoryItemStacks.add(i,this.msEntity.inventory.getStackInSlot(i));
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		System.out.println(PlayerUtils.isMultiplayer(playerIn));
	}
	
	@Override
	public boolean canDragIntoSlot(Slot slotIn) {
		return slotIn.slotNumber > this.msEntity.getMSRegistryEntry().getArmamentCount();
	}
	
	@Override
	public boolean canMergeSlot(ItemStack stack,Slot slotIn) {
		return slotIn.slotNumber > this.msEntity.getMSRegistryEntry().getArmamentCount();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.isRiding() ? playerIn.getRidingEntity() instanceof MobileSuit.MSEntity : false;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn,int slotIndex) {
		Slot slot = (Slot)inventorySlots.get(slotIndex);
		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			if(slotIndex < this.numRows*9) {
				if(!this.mergeItemStack(stack,this.numRows*9,this.inventorySlots.size(),true)) return ItemStack.EMPTY;
			} else if(!this.mergeItemStack(stack,0,this.numRows*9,false)) return ItemStack.EMPTY;
			if(stack.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
		}
		return ItemStack.EMPTY;
	}

}

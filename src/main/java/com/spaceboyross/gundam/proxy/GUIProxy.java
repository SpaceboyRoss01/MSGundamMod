package com.spaceboyross.gundam.proxy;

import com.spaceboyross.gundam.blocks.MSCraftingStationBlock;
import com.spaceboyross.gundam.blocks.container.MSCraftingStationContainer;
import com.spaceboyross.gundam.blocks.gui.MSCraftingStationGUIContainer;
import com.spaceboyross.gundam.blocks.tile.MSCraftingStationTileEntity;
import com.spaceboyross.gundam.gui.GUIMobileSuitInventory;
import com.spaceboyross.gundam.gui.HumantypeGUI;
import com.spaceboyross.gundam.ms.MobileSuit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID,EntityPlayer player,World world,int x,int y,int z) {
		switch(ID) {
			case MSCraftingStationBlock.GUI_ID:
				TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
				if(te instanceof MSCraftingStationTileEntity) return new MSCraftingStationContainer(player.inventory,(MSCraftingStationTileEntity)te);
				break;
			case GUIMobileSuitInventory.GUI_ID:
				if(player.isRiding() && player.getRidingEntity() instanceof MobileSuit.MSEntity) return ((MobileSuit.MSEntity)player.getRidingEntity()).inventoryContainer;
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID,EntityPlayer player,World world,int x,int y,int z) {
		switch(ID) {
			case MSCraftingStationBlock.GUI_ID:
				TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
		       	if(te instanceof MSCraftingStationTileEntity) return new MSCraftingStationGUIContainer((MSCraftingStationTileEntity)te,new MSCraftingStationContainer(player.inventory,(MSCraftingStationTileEntity)te));
		       	break;
			case HumantypeGUI.GUI_ID: return new HumantypeGUI(player);
			case GUIMobileSuitInventory.GUI_ID:
				if(player.isRiding() && player.getRidingEntity() instanceof MobileSuit.MSEntity) return new GUIMobileSuitInventory((MobileSuit.MSEntity)player.getRidingEntity());
				break;
		}
        return null;
	}

}

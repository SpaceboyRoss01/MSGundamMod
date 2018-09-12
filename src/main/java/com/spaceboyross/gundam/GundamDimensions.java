package com.spaceboyross.gundam;

import javax.annotation.Nullable;

import com.spaceboyross.gundam.dimension.side1.DimensionSide1ShangriLa;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class GundamDimensions {
	
	public static DimensionType side1ShangriLa;
	private static int side1ShangriLaID = GundamDimensions.findFreeDimensionID();
	
	public static void init() {
		GundamDimensions.registerDimensionTypes();
		GundamDimensions.registerDimensions();
	}
	
	@Nullable
    private static Integer findFreeDimensionID() {
        for(int i = 2;i < Integer.MAX_VALUE;i++) {
            if(!DimensionManager.isDimensionRegistered(i)) return i;
        }
        return null;
    }
	
	private static void registerDimensionTypes() {
		GundamDimensions.side1ShangriLa = DimensionType.register(GundamMod.MODID,"_side1_shangrila",GundamDimensions.side1ShangriLaID,DimensionSide1ShangriLa.class,false);
	}
	
	private static void registerDimensions() {
		DimensionManager.registerDimension(GundamDimensions.side1ShangriLaID,GundamDimensions.side1ShangriLa);
	}
}
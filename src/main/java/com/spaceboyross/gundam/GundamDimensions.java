package com.spaceboyross.gundam;

import javax.annotation.Nullable;

import com.spaceboyross.gundam.dimension.DimensionMoon;
import com.spaceboyross.gundam.dimension.DimensionSpace;
import com.spaceboyross.gundam.dimension.side1.DimensionSide1ShangriLa;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class GundamDimensions {
	
	public static DimensionType side1ShangriLa;
	public static int side1ShangriLaID = GundamDimensions.findFreeDimensionID();
	
	public static DimensionType moon;
	public static int moonID = GundamDimensions.findFreeDimensionID()+1;
	
	public static DimensionType space;
	public static int spaceID = GundamDimensions.findFreeDimensionID()+2;
	
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
		GundamDimensions.moon = DimensionType.register(GundamMod.MODID,"_moon",GundamDimensions.moonID,DimensionMoon.class,false);
		GundamDimensions.space = DimensionType.register(GundamMod.MODID,"_space",GundamDimensions.spaceID,DimensionSpace.class,false);
	}
	
	private static void registerDimensions() {
		DimensionManager.registerDimension(GundamDimensions.side1ShangriLaID,GundamDimensions.side1ShangriLa);
		DimensionManager.registerDimension(GundamDimensions.moonID,GundamDimensions.moon);
		DimensionManager.registerDimension(GundamDimensions.spaceID,GundamDimensions.space);
	}
}
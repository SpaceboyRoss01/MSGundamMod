package com.spaceboyross.gundam.ms.gundams;

import com.spaceboyross.gundam.ms.MSRegistry;
import com.spaceboyross.gundam.ms.MobileSuit;
import com.spaceboyross.gundam.ms.armaments.BeamSaberMSArmament;
import com.spaceboyross.gundam.ms.armaments.VulcanGunMSArmament;
import com.spaceboyross.gundam.ms.gundams.RX782GundamMobileSuit.MSMob;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RX78NT1GundamAlexMobileSuit extends MobileSuit {
	
	public RX78NT1GundamAlexMobileSuit() {
		this.setHeight(59.055f);
		this.setModel("RX-78NT-1");
		this.setName("Gundam Alex");
		
		this.addArmament(new VulcanGunMSArmament(2,60));
		this.addArmament(new BeamSaberMSArmament(2));
		
		this.addRecipeItem("gundam:luna_titanium_ingot",20);
	}
	
	@Override
	public int getCameraCount() {
		return 12;
	}
	
	@Override
	public MSMob createEntity(World worldIn,Vec3d pos) {
		MSMob mob = new RX78NT1GundamAlexMobileSuit.MSMob(worldIn);
		mob.setPosition(pos.x,pos.y,pos.z);
		return mob;
	}
	
	public static class MSMob extends MobileSuit.MSMob {
		public MSMob(World worldIn) {
			super(worldIn);
			this.scale = 6.65333333333f;
			this.setSize(1.0f*this.scale,2.0f*this.scale);
		}
		
		@Override
		public MobileSuit getMSRegistryEntry() {
			return MSRegistry.getMobileSuit("RX-78NT-1 Gundam Alex");
		}
	}
}

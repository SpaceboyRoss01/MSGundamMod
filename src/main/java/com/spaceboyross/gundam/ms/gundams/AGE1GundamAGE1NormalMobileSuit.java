package com.spaceboyross.gundam.ms.gundams;

import com.spaceboyross.gundam.ms.MSRegistry;
import com.spaceboyross.gundam.ms.MobileSuit;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AGE1GundamAGE1NormalMobileSuit extends MobileSuit {
	
	public AGE1GundamAGE1NormalMobileSuit() {
		this.setHeight(59.055f);
		this.setModel("AGE-1");
		this.setName("Gundam AGE-1 Normal");
		
		this.addRecipeItem("gundam:luna_titanium_ingot",20);
	}
	
	@Override
	public int getCameraCount() {
		return 12;
	}
	
	@Override
	public MSEntity createEntity(World worldIn,Vec3d pos) {
		MSEntity mob = new AGE1GundamAGE1NormalMobileSuit.MSEntity(worldIn);
		mob.setPosition(pos.x,pos.y,pos.z);
		return mob;
	}
	
	public static class MSEntity extends MobileSuit.MSEntity {
		public MSEntity(World worldIn) {
			super(worldIn);
			this.scale = 6.65333333333f;
			this.setSize(1.0f*this.scale,2.0f*this.scale);
		}
		
		@Override
		public MobileSuit getMSRegistryEntry() {
			return MSRegistry.getMobileSuit("AGE-1 Gundam AGE-1 Normal");
		}
	}
}

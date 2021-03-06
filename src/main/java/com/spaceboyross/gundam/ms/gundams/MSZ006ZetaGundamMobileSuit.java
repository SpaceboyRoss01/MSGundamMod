package com.spaceboyross.gundam.ms.gundams;

import com.spaceboyross.gundam.ms.MSRegistry;
import com.spaceboyross.gundam.ms.MobileSuit;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MSZ006ZetaGundamMobileSuit extends MobileSuit {
	
	public MSZ006ZetaGundamMobileSuit() {
		this.setHeight(65.125f);
		this.setModel("MSZ-006");
		this.setName("Zeta Gundam");
		
		this.addRecipeItem("gundam:gundarium_gamma_ingot",20);
	}
	
	@Override
	public MSEntity createEntity(World worldIn,Vec3d pos) {
		MSEntity mob = new MSZ006ZetaGundamMobileSuit.MSEntity(worldIn);
		mob.setPosition(pos.x,pos.y,pos.z);
		return mob;
	}
	
	public static class MSEntity extends MobileSuit.MSEntity {
		public MSEntity(World worldIn) {
			super(worldIn);
			this.scale = 6.8f;
			this.setSize(1.0f*this.scale,2.0f*this.scale);
		}
		
		@Override
		public MobileSuit getMSRegistryEntry() {
			return MSRegistry.getMobileSuit("MSZ-006 Zeta Gundam");
		}
	}

}

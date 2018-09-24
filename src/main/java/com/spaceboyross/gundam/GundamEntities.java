package com.spaceboyross.gundam;

import com.spaceboyross.gundam.entities.EntityAmuroRay;
import com.spaceboyross.gundam.entities.EntityBrightNoa;
import com.spaceboyross.gundam.entities.EntityCharAznable;
import com.spaceboyross.gundam.entities.EntityDomonKasshu;
import com.spaceboyross.gundam.entities.EntityFullFrontal;
import com.spaceboyross.gundam.entities.EntityHeeroYuy;
import com.spaceboyross.gundam.entities.EntityKamilleBidan;
import com.spaceboyross.gundam.entities.EntityMasterAsia;
import com.spaceboyross.gundam.entities.EntityQuattroBajeena;
import com.spaceboyross.gundam.entities.EntitySetsunaFSeiei;
import com.spaceboyross.gundam.entities.EntityTreizeKhushrenada;
import com.spaceboyross.gundam.entities.EntityUsoEwin;
import com.spaceboyross.gundam.entities.EntityZechsMarquise;
import com.spaceboyross.gundam.entities.render.SkinRender;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GundamEntities {
	
	public static int ID = 1;
	
	private static void registerCharacter(Class<? extends Entity> entity,String name,String id) {
		EntityRegistry.registerModEntity(new ResourceLocation(GundamMod.MODID,id),entity,GundamMod.MODID+"."+name,GundamEntities.ID++,GundamMod.instance,64,3,true,0x996600+(GundamEntities.ID-1),0x00ff00+(GundamEntities.ID-1));
	}
	
	private static void registerCharacterModel(Class<? extends EntityMob> entity,String name,float scale) {
		RenderingRegistry.registerEntityRenderingHandler(entity,new SkinRender.Factory(new ResourceLocation(GundamMod.MODID,"textures/entity/"+name+".png"),scale));
	}
	
	private static void registerCharacterModel(Class<? extends EntityMob> entity,String name) {
		RenderingRegistry.registerEntityRenderingHandler(entity,new SkinRender.Factory(new ResourceLocation(GundamMod.MODID,"textures/entity/"+name+".png")));
	}
	
	public static void init() {
		registerCharacter(EntityAmuroRay.class,"AmuroRay","amuro_ray");
		registerCharacter(EntityBrightNoa.class,"BrightNoa","bright_noa");
		registerCharacter(EntityCharAznable.class,"CharAznable","char_aznable");
		registerCharacter(EntityDomonKasshu.class,"DomonKasshu","domon_kasshu");
		registerCharacter(EntityFullFrontal.class,"FullFrontal","full_frontal");
		registerCharacter(EntityHeeroYuy.class,"HeeroYuy","heero_yuy");
		registerCharacter(EntityKamilleBidan.class,"KamilleBidan","kamille_bidan");
		registerCharacter(EntityMasterAsia.class,"MasterAsia","master_asia");
		registerCharacter(EntityQuattroBajeena.class,"QuattroBajeena","quattro_bajeena");
		registerCharacter(EntitySetsunaFSeiei.class,"SetsunaFSeiei","setsuna_f_seiei");
		registerCharacter(EntityTreizeKhushrenada.class,"TreizeKhushrenada","treize_khushrenada");
		registerCharacter(EntityUsoEwin.class,"UsoEwin","uso_ewin");
		registerCharacter(EntityZechsMarquise.class,"ZechsMarquise","zechs_marquise");
		
		EntityRegistry.addSpawn(EntityHeeroYuy.class,100,3,5,EnumCreatureType.CREATURE,Biomes.PLAINS,Biomes.ICE_PLAINS,Biomes.COLD_BEACH,Biomes.OCEAN);
		EntityRegistry.addSpawn(EntityZechsMarquise.class,100,3,5,EnumCreatureType.CREATURE,Biomes.PLAINS,Biomes.ICE_PLAINS,Biomes.COLD_BEACH);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		registerCharacterModel(EntityAmuroRay.class,"amuro_ray");
		registerCharacterModel(EntityBrightNoa.class,"bright_noa");
		registerCharacterModel(EntityCharAznable.class,"char_aznable");
		registerCharacterModel(EntityDomonKasshu.class,"domon_kasshu");
		registerCharacterModel(EntityFullFrontal.class,"full_frontal");
		registerCharacterModel(EntityHeeroYuy.class,"heero_yuy");
		registerCharacterModel(EntityKamilleBidan.class,"kamille_bidan");
		registerCharacterModel(EntityMasterAsia.class,"master_asia");
		registerCharacterModel(EntityQuattroBajeena.class,"quattro_bajeena");
		registerCharacterModel(EntitySetsunaFSeiei.class,"setsuna_f_seiei");
		registerCharacterModel(EntityTreizeKhushrenada.class,"treize_khushrenada");
		registerCharacterModel(EntityUsoEwin.class,"uso_ewin",0.66F);
		registerCharacterModel(EntityZechsMarquise.class,"zechs_marquise");
	}
}

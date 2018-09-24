package com.spaceboyross.gundam.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.spaceboyross.gundam.GundamDimensions;
import com.spaceboyross.gundam.world.terrain.MoonTerrainGenerator;
import com.spaceboyross.gundam.world.terrain.NormalTerrainGenerator;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class DimensionMoon extends WorldProvider {
	
	@Override
	public void init() {
		super.init();
		this.doesWaterVaporize = true;
		this.hasSkyLight = false;
	}
	
	@Override
	public void updateWeather() {
		this.world.getWorldInfo().setRainTime(0);
        this.world.getWorldInfo().setRaining(false);
        this.world.getWorldInfo().setThunderTime(0);
        this.world.getWorldInfo().setThundering(false);
        this.world.rainingStrength = 0.0F;
        this.world.thunderingStrength = 0.0F;
	}
	
	@Override
	public Vec3d getCloudColor(float partialTicks) {
		return Vec3d.ZERO;
	}

	@Override
	public DimensionType getDimensionType() {
		return GundamDimensions.moon;
	}
	
	@Override
	public boolean canSnowAt(BlockPos pos,boolean checkLight) {
		return false;
	}
	
	@Override
	public boolean isDaytime() {
		return false;
	}
	
	@Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }
	
	@Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
	}
	
	@Override
	public String getSaveFolder() {
		return "GUNDAM_MOON";
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new MoonChunkGenerator(this.world);
	}
	
	public static class MoonChunkGenerator implements IChunkGenerator {
		
		private World world;
		private Random random;
		private NormalTerrainGenerator terraingen;
		private Biome[] biomesForGeneration;
		
		public MoonChunkGenerator(World world) {
			this.world = world;
			long seed = this.world.getSeed();
			this.random = new Random((seed+516)*314);
			this.terraingen = new MoonTerrainGenerator();
			this.terraingen.setup(this.world,this.random);
		}

		@Override
		public Chunk generateChunk(int x,int z) {
			ChunkPrimer chunkprimer = new ChunkPrimer();
			
			this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration,x*4-2,z*4-2,10,10);
			this.terraingen.setBiomesForGeneration(this.biomesForGeneration);
			this.terraingen.generate(x,z,chunkprimer);
			
			this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration,x*16,z*16,16,16);
			this.terraingen.replaceBiomeBlocks(x,z,chunkprimer,this,this.biomesForGeneration);
			
			Chunk chunk = new Chunk(this.world,chunkprimer,x,z);
			
			byte[] biomeArray = chunk.getBiomeArray();
			for(int i = 0;i < biomeArray.length;i++) biomeArray[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
			chunk.generateSkylightMap();
			return chunk;
		}

		@Override
		public void populate(int x,int z) {
			int i = x*16;
	        int j = z*16;
	        BlockPos blockpos = new BlockPos(i,0,j);
	        Biome biome = this.world.getBiome(blockpos.add(16,0,16));
	        biome.decorate(this.world,this.random,blockpos);
	        WorldEntitySpawner.performWorldGenSpawning(this.world,biome,i+8,j+8,16,16,this.random);
		}

		@Override
		public boolean generateStructures(Chunk chunkIn,int x,int z) {
			return false;
		}

		@Override
		public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType,BlockPos pos) {
			return new ArrayList<>();
		}

		@Override
		public BlockPos getNearestStructurePos(World worldIn,String structureName,BlockPos position,boolean findUnexplored) {
			return null;
		}

		@Override
		public void recreateStructures(Chunk chunkIn,int x,int z) {
		}

		@Override
		public boolean isInsideStructure(World worldIn,String structureName,BlockPos pos) {
			return false;
		}
		
	}

}

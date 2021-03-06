package com.spaceboyross.gundam.ms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.spaceboyross.gundam.GundamItems;
import com.spaceboyross.gundam.GundamMod;
import com.spaceboyross.gundam.capabilities.human.Human;
import com.spaceboyross.gundam.capabilities.interfaces.IHumanCapability;
import com.spaceboyross.gundam.containers.ContainerMobileSuitInventory;
import com.spaceboyross.gundam.gui.hud.MobileSuitHUD;
import com.spaceboyross.gundam.utils.InventoryUtils;
import com.spaceboyross.gundam.utils.PlayerUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class MobileSuit {

	private List<ItemStack> armaments;
	private String name;
	private String model;
	private Map<String,Integer> recipeIDs;
	private float height;
	
	public MobileSuit.MSRender.Factory FACTORY = new MobileSuit.MSRender.Factory(this);
	
	public MobileSuit() {
		this.armaments = new ArrayList<>();
		this.recipeIDs = new HashMap<>();
		this.height = 0.0f;
	}
	
	public void init() {}
	
	public int getArmamentCount() {
		return this.armaments.size();
	}
	
	public void addArmament(ItemStack armament) {
		this.armaments.add(armament);
	}
	
	public ItemStack getArmament(int i) {
		return this.armaments.get(i);
	}
	
	public int getCameraCount() {
		return 2;
	}
	
	public void drawHUD(MobileSuitHUD hud) {
		MobileSuit.MSEntity mob = (MSEntity)hud.human.getPlayer().getRidingEntity();
		Minecraft.getMinecraft().setRenderViewEntity(mob);
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public ResourceLocation getBaseResourceLocation() {
		return new ResourceLocation(GundamMod.MODID,"textures/entity/"+(this.model+"_"+this.name).replaceAll(" ","_"));
	}
	
	public MSEntity createEntity(World worldIn,Vec3d pos) {
		MSEntity mob = new MSEntity(worldIn);
		mob.setPosition(pos.x,pos.y,pos.z);
		return mob;
	}
	
	public void addRecipeItem(String id,int count) {
		this.recipeIDs.put(id,new Integer(count));
	}
	
	public int getRecipeItemCount() {
		return this.recipeIDs.size();
	}
	
	public String getRecipeItemID(int i) {
		String ids[] = new String[this.recipeIDs.keySet().size()];
		ids = this.recipeIDs.keySet().toArray(ids);
		return ids[i];
	}
	
	public Integer getRecipeItemCount(String id) {
		return this.recipeIDs.get(id);
	}
	
	public Integer getRecipeItemCount(int i) {
		return this.getRecipeItemCount(this.getRecipeItemID(i));
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ModelBase createModel() {
		return null;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public ItemStack getRequiredItemToPilot() {
		return ItemStack.EMPTY;
	}
	
	public static class MSEntity extends Entity {
		
		@CapabilityInject(IItemHandler.class)
		public static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
		
		public float scale = 1.0f;
		private EntityPlayer pilot;
		
		private static final DataParameter<Float> ACCELERATION_SPEED = EntityDataManager.createKey(MSEntity.class,DataSerializers.FLOAT);
		
		private double driveMotionX = 0.0;
		private double driveMotionY = 0.0;
		private double driveMotionZ = 0.0;
		
		public float headYaw = 0.0f;
		public float headPitch = 0.0f;
		
		public InventoryBasic inventory;
		public Container inventoryContainer;
		
		public MSEntity(World worldIn) {
			super(worldIn);
			int slots = 9;
			if((this.getMSRegistryEntry().getArmamentCount() % 9) == 0) {
				slots += this.getMSRegistryEntry().getArmamentCount();
				if(this.getMSRegistryEntry().getArmamentCount()-9 > -1) slots += this.getMSRegistryEntry().getArmamentCount()-9;
				else slots += 9-this.getMSRegistryEntry().getArmamentCount();
			} else slots += this.getMSRegistryEntry().getArmamentCount();
			this.inventory = new InventoryBasic(this.getCachedUniqueIdString(),true,slots);
			this.inventory.setCustomName(I18n.format("gui.gundam.ms.inventory.name",this.getName()));
			for(int i = 0;i < this.getMSRegistryEntry().getArmamentCount();i++) this.inventory.addItem(this.getMSRegistryEntry().getArmament(i));
			this.inventoryContainer = new ContainerMobileSuitInventory(this);
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
			if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
			return super.hasCapability(capability,facing);
		}
		
		@Override
		public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
			if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.inventory;
			return super.getCapability(capability,facing);
		}
		
		@Override
		public void entityInit() {
			this.dataManager.register(ACCELERATION_SPEED,0.5f);
		}
		
		@Override
		public void writeEntityToNBT(NBTTagCompound root) {
			NBTTagCompound ms = new NBTTagCompound();
			root.setTag("mobileSuit",ms);
			root.setFloat("headPitch",this.headPitch);
			root.setFloat("headYaw",this.headYaw);
			root.setTag("inventory",InventoryUtils.save(this.inventory));
		}
		
		@Override
		public void readEntityFromNBT(NBTTagCompound root) {
			if(root.hasKey("mobileSuit")) {
				NBTTagCompound ms = root.getCompoundTag("mobileSuit");
			}
			this.headPitch = root.getFloat("headPitch");
			this.headYaw = root.getFloat("headYaw");
			this.inventory = (InventoryBasic)InventoryUtils.load(root.getTagList("inventory",10),this.inventory);
		}
		
		@Override
		public void onUpdate() {
			super.onUpdate();
			if(this.isBeingRidden() && this.canBeSteered() && this.getControllingPassenger() instanceof EntityLivingBase) {
				EntityLivingBase driver = (EntityLivingBase)this.getControllingPassenger();
				this.rotationYaw = driver.rotationYaw;
				this.rotationPitch = driver.rotationPitch;
				this.setRotation(this.rotationYaw,this.rotationPitch);
				this.headYaw = driver.rotationYawHead;
				this.headPitch = driver.cameraPitch;
				this.driveMotionX = driver.motionX;
				this.driveMotionY = driver.motionY;
				this.driveMotionZ = driver.motionZ;
				this.move(MoverType.SELF,this.motionX+this.driveMotionX,this.motionY+this.driveMotionY,this.motionZ+this.driveMotionZ);
			}
		}
		
	    @Nullable
	    public Entity getControllingPassenger() {
	        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
	    }
		
		@Override
		public boolean processInitialInteract(EntityPlayer player,EnumHand hand) {
			if(player.inventory.getStackInSlot(player.inventory.currentItem).getItem() == GundamItems.wrench) {
				// TODO: show customization interface
			} else {
				if(this.getMSRegistryEntry().getRequiredItemToPilot() != ItemStack.EMPTY) {
					if(!player.inventory.hasItemStack(this.getMSRegistryEntry().getRequiredItemToPilot())) return false;
				}
				if(this.pilot != null) return false;
				this.pilot = player;
				this.pilot.startRiding(this);
				IHumanCapability human = Human.getHuman(this.pilot);
				human.setMS(this);
				if(PlayerUtils.isSinglePlayer(player)) Minecraft.getMinecraft().setRenderViewEntity(this);
			}
			return true;
		}
		
		public EntityPlayer getPilot() {
			return this.pilot;
		}
		
		public MobileSuit getMSRegistryEntry() {
			return MSRegistry.getMobileSuit(this.getName());
		}
		
		@Override
		public boolean canBeAttackedWithItem() {
			return false;
		}
		
		@Override
		public boolean canPassengerSteer() {
			return true;
		}
		
		@Override
		public boolean canRiderInteract() {
			return true;
		}
		
		@Override
		public boolean canBeCollidedWith() {
			return true;
		}
		
		public boolean canBeSteered() {
			return this.getControllingPassenger() instanceof EntityLivingBase;
		}
		
		private void updateRiderPosition(Entity entity) {
			if(entity != null) {
				entity.setPosition(this.posX,this.posY+(this.getMountedYOffset()+entity.getYOffset())/2,this.posZ);
			}
		}
		
		@Override
		public void updatePassenger(Entity passenger) {
			this.updateRiderPosition(passenger);
			passenger.setInvisible(true);
		}
		
		@Override
		public void removePassenger(Entity passenger) {
			if(passenger != null) passenger.setPosition(this.posX,this.posY,this.posZ);
			super.removePassenger(passenger);
			passenger.setInvisible(false);
			if(passenger instanceof EntityPlayer) {
				IHumanCapability human = Human.getHuman((EntityPlayer)passenger);
				human.setMS(null);
				this.pilot = null;
				human.syncToServer();
				Minecraft.getMinecraft().setRenderViewEntity(passenger);
			}
		}
	}
	
	public static class MSRender extends Render<MSEntity> {
		
		private MobileSuit ms;
		private ModelBase model;
		
		public MSRender(RenderManager rendermanagerIn,MobileSuit ms) {
			super(rendermanagerIn);
			this.model = ms.createModel() == null ? new ModelPlayer(1.0f,false) : ms.createModel();
			this.model.isChild = false;
			this.ms = ms;
		}
		
		protected float handleRotationFloat(MSEntity entity,float partialTicks) {
	        return (float)entity.ticksExisted + partialTicks;
	    }
		
		protected float interpolateRotation(float prevYawOffset,float yawOffset,float partialTicks) {
	        float f;
	        for(f = yawOffset-prevYawOffset;f < -180.0F;f += 360.0F);
	        while(f >= 180.0F) f -= 360.0F;
	        return prevYawOffset+partialTicks*f;
	    }
		
		@Override
		public void doRender(MSEntity entity,double x,double y,double z,float entityYaw,float partialTicks) {
			GlStateManager.pushMatrix();
			this.scale(entity,partialTicks);
			GlStateManager.translate(x,y,z);
			this.bindTexture(this.getEntityTexture(entity));
			this.model.render(entity,0,0,entity.ticksExisted+partialTicks,entity.headYaw,entity.headPitch,1.0f);
			super.doRender(entity,x,y,z,entityYaw,partialTicks);
			GlStateManager.popMatrix();
		}
		
		protected void scale(MSEntity mob,float par2) {
			this.shadowSize = mob.scale/2.0f;
		}

		@Override
		protected ResourceLocation getEntityTexture(MSEntity entity) {
			return new ResourceLocation(this.ms.getBaseResourceLocation()+".png");
		}
		
		public static class Factory implements IRenderFactory<MSEntity> {
			
			private MobileSuit ms;
			
			public Factory(MobileSuit ms) {
				this.ms = ms;
			}
			
			@Override
			public Render<? super MSEntity> createRenderFor(RenderManager manager) {
				return new MSRender(manager,this.ms);
			}
		}
	}
}
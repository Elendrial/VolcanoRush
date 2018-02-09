package me.hii488.volcanoRush.objects.entities;

//Currently no reason for this class other than to group entities with AI

abstract public class EntityLiving extends GravityEntity{
	
	public EntityLiving(){super();}
	public EntityLiving(EntityLiving e){super(e);}
	
	public void updateOnTick(){
		super.updateOnTick();
	}
}

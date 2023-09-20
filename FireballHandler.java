package net.fabricmc.example.mod;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class FireballHandler extends SwordItem {

    public static HashMap<UUID, CustomTimer> timers = new HashMap<>();

    public FireballHandler(ToolMaterial material) {
        super(material, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
        }

        if (player.isSneaking()) {
            return super.use(world, player, hand);
        } else {
            CustomTimer timer = timers.get(player.getUuid());
            if (timer == null) {
                timer = new CustomTimer();
                timers.put(player.getUuid(), timer);
                timer.startTimer();
            } else {
                if (timer.getElapsedTime() >= 3000) { // 3 seconds
                    spawnFireball(player, world);
                    timers.remove(player.getUuid()); // Remove the timer after firing
                }
            }
            return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
        }
    }

    private void spawnFireball(PlayerEntity player, World world) {
        Vec3d lookVec = player.getRotationVector();
        int explosionPower = 1; // 你需要为这个设置一个合适的值

        FireballEntity fireball = new FireballEntity(world, player, lookVec.x * 0.5, lookVec.y * 0.5, lookVec.z * 0.5, explosionPower);
        fireball.setPos(player.getX(), player.getY() + player.getStandingEyeHeight(), player.getZ());
        world.spawnEntity(fireball);
    }





    public static class CustomTimer {
        private long startTime;

        public void startTimer() {
            this.startTime = System.currentTimeMillis();
        }

        public long getElapsedTime() {
            return System.currentTimeMillis() - this.startTime;
        }
    }
}

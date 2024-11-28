package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class HammerEventListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val itemInHand: ItemStack = player.inventory.itemInMainHand

        // Check if the player is holding the custom hammer by comparing ItemMeta with CustomItems
        if (!isValidHammer(itemInHand)) return

        val block = event.block

        // Determine the new block type based on the block broken
        val newBlockType: Material? = when (block.type) {
            Material.COBBLESTONE -> Material.GRAVEL
            Material.GRAVEL -> Material.SAND
            Material.SAND -> Material.DIRT
            else -> null
        }

        // If the block type can be transformed, replace it and drop the new item
        if (newBlockType != null) {
            event.isCancelled = true // Prevent the default block break behavior
            block.type = Material.AIR // Remove the block
            block.world.dropItemNaturally(block.location, ItemStack(newBlockType)) // Drop the new item

            // Optionally, decrease durability or handle other logic here
            decreaseHammerDurability(player, itemInHand)
            player.world.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
        }
    }

    private fun isValidHammer(itemInHand: ItemStack): Boolean {
        val meta: ItemMeta = itemInHand.itemMeta ?: return false

        // Check if the display name matches any hammer variant in the HAMMER_MAP
        return CustomItems.HAMMER_MAP.values.any { hammer ->
            val hammerMeta = hammer.itemMeta
            hammerMeta != null && hammerMeta.displayName == meta.displayName
        }
    }

    // Optionally, you can handle durability or other features
    private fun decreaseHammerDurability(player: Player, itemInHand: ItemStack) {
        // You can choose to ignore durability checks or apply custom durability logic here
        // Just for example, here's how you can decrease the durability if needed
        val durability = itemInHand.durability

        if (durability < itemInHand.type.maxDurability) {
            itemInHand.durability = (durability + 1).toShort()
        }
    }
}
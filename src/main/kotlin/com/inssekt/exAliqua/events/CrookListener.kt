package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class CrookListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent){
        val block = event.block
        val player = event.player
        val itemInHand = player.inventory.itemInMainHand

        if (block.type.name.contains("LEAVES") && isCrook(itemInHand)) {
            // Generate a random number between 1 and 100
            val chance = Random.nextInt(100)
            player.sendMessage(">$chance")

            // 5% chance to drop the silkworm item (Random number between 0 and 99)
            if (chance < 5) {
                dropSilkworm(player, block)
            }
        }
    }

    private fun dropSilkworm(player: Player, block: Block) {
        // Create the custom silkworm item
        val silkworm = CustomItems.SILKWORM // This assumes you've defined the custom silkworm item in your CustomItems class

        // Drop the silkworm item at the block's location
        block.world.dropItemNaturally(block.location, silkworm)

        // Optionally, send a message to the player
        player.sendMessage(ChatColor.GREEN.toString() + "You found a Silkworm!")
        player.playSound(player.location, Sound.ENTITY_SILVERFISH_AMBIENT, 1f, 1f)
    }

    fun isCrook(item: ItemStack): Boolean {
        // Check if the item is the custom crook (ignoring durability)
        return item.type == CustomItems.CROOK.type && item.itemMeta?.displayName == CustomItems.CROOK.itemMeta?.displayName
    }
}
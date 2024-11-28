package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.*
import org.bukkit.block.Block

import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class SilkwormListener(private val plugin: JavaPlugin) : Listener {

    private val directions = arrayOf(
        BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH,
        BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST
    )

    @EventHandler
    fun onLeafRightClick(event: PlayerInteractEvent) {
        if (event.hand != org.bukkit.inventory.EquipmentSlot.HAND) return

        val block = event.clickedBlock ?: return
        val player = event.player
        val item = event.item ?: return

        // Check if the block is a leaf and the item is a silkworm
        if (block.type.name.contains("LEAVES") && item.isSimilar(CustomItems.SILKWORM)) {
            item.amount -= 1
            if(item.amount <= 0) {
                player.inventory.setItemInMainHand(null)
            }

            infestLeaves(player, block)
            event.isCancelled = true // Prevent default behavior
        }
    }

    private fun infestLeaves(player: Player, startBlock: Block) {
        val visited = mutableSetOf<Location>()
        val toInfect = mutableListOf(startBlock)



        player.sendMessage(ChatColor.GREEN.toString() + "Silkworms infested the leaves!")

        // Schedule the infestation process
        object : BukkitRunnable() {
            var duration = 100 // ticks (5 seconds)
            override fun run() {
                if (duration-- <= 0 || toInfect.isEmpty()) {
                    cancel()
                    return
                }

                // Pick a random block to infest
                val randomInfections = mutableListOf<Block>()
                for (block in toInfect) {
                    if (block.location in visited) continue
                    visited.add(block.location)

                    // Change the block to infested (e.g., a cobweb or a custom infested block)
                    block.type = Material.COBWEB // Or a custom "infested leaves" block
                    block.world.playSound(block.location, Sound.ENTITY_SILVERFISH_DEATH, 1.0f, 1.0f) // Sound can be adjusted here

                    // Collect neighboring leaf blocks
                    val neighbors = mutableListOf<Block>()
                    for (face in directions) {
                        val neighbor = block.getRelative(face)
                        if (neighbor.type.name.contains("LEAVES") && neighbor.location !in visited) {
                            neighbors.add(neighbor)
                        }
                    }

                    // If there are any valid neighbors, pick one at random and add it to the spread list
                    if (neighbors.isNotEmpty()) {
                        val randomNeighbor = neighbors[Random.nextInt(neighbors.size)]
                        randomInfections.add(randomNeighbor)
                    }
                }

                // Prepare the next set of blocks to infect
                toInfect.clear()
                toInfect.addAll(randomInfections)
            }
        }.runTaskTimer(plugin, 0, 200) // Runs every 10 ticks (0.5 seconds)
    }
}

package com.inssekt.exAliqua.blocks

import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.scheduler.BukkitRunnable

class CrucibleManager(private val plugin: JavaPlugin) : Listener {
    private val activeCrucibles = mutableMapOf<Location, Int>()

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val block = event.block
        if (block.type != Material.COBBLESTONE) return

        val crucibleCenter = findCrucibleCenter(block.location) ?: return

        event.player.sendMessage("§eCobblestone added to the crucible. Lava is being generated!")

        // Start lava generation
        if (!activeCrucibles.containsKey(crucibleCenter)) {
            activeCrucibles[crucibleCenter] = 0
            generateLavaOverTime(crucibleCenter)
        }
    }

    private fun findCrucibleCenter(location: Location): Location? {
        val world = location.world ?: return null

        val center = location.clone().add(0.0, -1.0, 0.0) // Crucible center is one block below placed cobblestone

        // Check the 3x3 base of bricks
        for (x in -1..1) {
            for (z in -1..1) {
                if (center.clone().add(x.toDouble(), 0.0, z.toDouble()).block.type != Material.BRICKS) {
                    return null
                }
            }
        }

        // Check the circle of 8 bricks on top
        val offsets = listOf(
            Location(world, -1.0, 1.0, 0.0),
            Location(world, 1.0, 1.0, 0.0),
            Location(world, 0.0, 1.0, -1.0),
            Location(world, 0.0, 1.0, 1.0),
            Location(world, -1.0, 1.0, -1.0),
            Location(world, 1.0, 1.0, 1.0),
            Location(world, -1.0, 1.0, 1.0),
            Location(world, 1.0, 1.0, -1.0)
        )
        if (offsets.any { offset -> center.clone().add(offset).block.type != Material.BRICKS }) {
            return null
        }

        // Check for the torch below the 3x3 center
        if (center.clone().add(0.0, -1.0, 0.0).block.type != Material.TORCH) {
            return null
        }

        return center
    }

    private fun generateLavaOverTime(center: Location) {
        val task = object : BukkitRunnable() {
            override fun run() {

                // Check if the cobblestone is still there
                if (center.clone().add(0.0, 1.0, 0.0).block.type != Material.COBBLESTONE) {
                    activeCrucibles.remove(center)
                    cancel()
                    return
                }

                val currentLava = activeCrucibles[center] ?: run {
                    cancel()
                    return
                }

                if (currentLava < 1000) {
                    activeCrucibles[center] = currentLava + 10

                    center.world?.spawnParticle(Particle.WHITE_SMOKE, center.clone().add(0.0, 1.0, 0.0), 5)
                    center.world?.spawnParticle(Particle.DRIPPING_LAVA, center.clone().add(0.0, 1.0, 0.0), 5)
                    center.world?.playSound(center, Sound.ITEM_BUCKET_FILL_LAVA, 1f, 1f)
                } else {
                    center.clone().add(0.0, 1.0, 0.0).block.type = Material.LAVA
                    activeCrucibles.remove(center)
                    cancel()
                }
            }
        }

        // 20T/1 sec
        task.runTaskTimer(plugin, 20L, 20L)
    }
}
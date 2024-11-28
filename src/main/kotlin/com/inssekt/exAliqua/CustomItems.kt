package com.inssekt.exAliqua

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CustomItems {

    val SILKWORM: ItemStack by lazy {
        val item = ItemStack(Material.RABBIT_FOOT)
        val meta = item.itemMeta

        if (meta != null)
        {
            meta.setDisplayName("§7Silk Worm")
            meta.lore = listOf(
                "§7A worm that produces silk.",
                "§7Right click leaves to infest them."
            )
            item.itemMeta = meta
        }

        item
    }


    val ROCK: ItemStack by lazy {
        val item = ItemStack(Material.FIREWORK_STAR)
        val meta = item.itemMeta

        if (meta != null)
        {
            meta.setDisplayName("§7Rock")
            meta.lore = listOf(
                "§7Rocks from the ground.",
                "§7Craft 4 into cobblestone."
            )
            item.itemMeta = meta
        }

        item
    }

    val CROOK: ItemStack by lazy {
        val item = ItemStack(Material.WOODEN_HOE)
        val meta = item.itemMeta

        if (meta != null)
        {
            meta.setDisplayName("§7Crook")
            meta.lore = listOf(
                "§7A hooked tool.",
                "§7Used to gather silkworms from leaves."
            )
            item.itemMeta = meta
        }

        item
    }


    val HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.WOODEN_PICKAXE)
        val meta = item.itemMeta

        if (meta != null)
        {
            meta.setDisplayName("§6Hammer")
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.",
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    // Stone Hammer
    val STONE_HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.STONE_PICKAXE)
        val meta = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§7Stone Hammer")
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.",
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    // Iron Hammer
    val IRON_HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.IRON_PICKAXE)
        val meta = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§fIron Hammer")
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.",
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    // Diamond Hammer
    val DIAMOND_HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.DIAMOND_PICKAXE)
        val meta = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§bDiamond Hammer")
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.",
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    val ITEM_MAP: Map<String, ItemStack> by lazy {
        mapOf(
            "hammer" to HAMMER,
            "stone_hammer" to STONE_HAMMER,
            "iron_hammer" to IRON_HAMMER,
            "diamond_hammer" to DIAMOND_HAMMER,
            "rock" to ROCK,
            "silkworm" to SILKWORM,
            "crook" to CROOK
        )
    }

    val HAMMER_MAP: Map<String, ItemStack> = mapOf(
        "stone" to STONE_HAMMER,
        "iron" to IRON_HAMMER,
        "diamond" to DIAMOND_HAMMER,
        "wooden" to HAMMER
    )

}
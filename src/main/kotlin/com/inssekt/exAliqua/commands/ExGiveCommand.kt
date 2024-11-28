package com.inssekt.exAliqua.commands

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ExGiveCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /exgive <item> [player]")
            sender.sendMessage("§7Available items: ${CustomItems.ITEM_MAP.keys.joinToString(", ")}")
            return true
        }

        val itemName = args[0].lowercase()

        // Look up the item in the ITEM_MAP
        val item = CustomItems.ITEM_MAP[itemName]
        if (item == null) {
            sender.sendMessage("§cUnknown item: $itemName")
            sender.sendMessage("§7Available items: ${CustomItems.ITEM_MAP.keys.joinToString(", ")}")
            return true
        }

        val targetPlayer: Player? = if (args.size > 1) {
            Bukkit.getPlayerExact(args[1])
        } else if (sender is Player) {
            sender
        } else {
            null
        }

        if (targetPlayer == null) {
            sender.sendMessage("§cPlayer not found or not specified!")
            return true
        }

        // Give the item to the target player
        targetPlayer.inventory.addItem(item.clone())
        targetPlayer.sendMessage("§aYou have been given a ${item.itemMeta?.displayName}!")
        if (sender != targetPlayer) {
            sender.sendMessage("§aYou have given ${item.itemMeta?.displayName} to ${targetPlayer.name}!")
        }

        return true
    }
}

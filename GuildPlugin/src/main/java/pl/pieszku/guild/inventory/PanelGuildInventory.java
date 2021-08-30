package pl.pieszku.guild.inventory;

import lombok.Getter;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.helper.ItemHelper;
import pl.pieszku.guild.service.GuildService;
import pl.pieszku.guild.utilities.DataUtilities;
import qu.code.api.builders.ItemBuilder;
import qu.code.api.objects.ApiConfig;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PanelGuildInventory implements Listener {

    private final ApiConfig config;
    private final GuildService guildService;
    private final ChatHelper chatHelper = new ChatHelper();
    private final VisualGuildIInventory visualGuildIInventory;

    public PanelGuildInventory(GuildService guildService, ApiConfig apiConfig) {
        this.guildService = guildService;
        this.config = apiConfig;
        this.visualGuildIInventory = new VisualGuildIInventory(guildService, apiConfig);
    }

    public void show(Player player) {
        int rows = this.config.getOrCreate("guild.panel.inventory.main_rows", 6 * 9);
        Inventory inventory = Bukkit.createInventory(player, rows,
                this.chatHelper.colored(this.config.getOrCreate("guild.panel.inventory.main_title", "&dPanel gildyjny")));

        var guild = this.guildService.getGuild(player);
        if (guild == null) return;

        for (int i = 0; i < rows; i++) {
            inventory.setItem(this.config.getOrCreate("guild.panel.inventory.main_glass_slot_" + i, i),
                    new ItemHelper(Material.valueOf(this.config.getOrCreate("guild.panel.inventory.main_glass_material_" + i, "BLACK_STAINED_GLASS_PANE")))
                            .setName("&8&m--!--")
                            .addLore("&8>> &cPuste pole")
                            .addLore("&8>> &cWybierz inna pozycje!").toItemStack());
        }

        List<String> lore = this.config.getOrCreate("guild.panel.inventory.head_lore",
                Arrays.asList(
                        "&8>> &fPelna nazwa: &d{FULLNAME}",
                        "&8>> &fOpis: &5{DESCRIPTION}",
                        "&8>> &fData zalozenia: &d{DATA_CREATE}",
                        "&8>> &fZalozyciel: &5{OWNER}",
                        "&8>> &fZastepca: &d{LEADER}",
                        "&8>> &fMistrz: &5{MASTER}",
                        "",
                        "&8>> &fOchrona: &d{PROTECTION}",
                        "&8>> &fWygasa za: &5{EXPIRES}",
                        "",
                        "&8Sa to informacje dotyczace twojej gildii",
                        "&8dzieki temu panelowi mozesz zarzadzac gildia"));


        ItemBuilder mainItem = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(config.getOrCreate("guild.panel.inventory.head_title", "&d{TAG}")
                        .replace("{TAG}", guild.getName().toUpperCase()));
        mainItem.setLore(lore.stream().map(regex -> {
            regex = regex.replace("{FULLNAME}", guild.getFullName());
            regex = regex.replace("{DESCRIPTION}", (guild.getDescription().equalsIgnoreCase("null") ? "brak" : guild.getDescription()));
            regex = regex.replace("{LEADER}", (guild.getLeader().equalsIgnoreCase("null") ? "brak" : guild.getLeader()));
            regex = regex.replace("{MASTER}", (guild.getMaster().equalsIgnoreCase("null") ? "brak" : guild.getMaster()));
            regex = regex.replace("{PROTECTION}", DataUtilities.secondsToString(guild.getProtectionTime()));
            regex = regex.replace("{EXPIRES}", DataUtilities.secondsToString(guild.getExpiresTime()));
            regex = regex.replace("{OWNER}", guild.getOwner());
            regex = regex.replace("{DATA_CREATE}", guild.getDateCreate());
            return regex;
        }).collect(Collectors.toList()));
        mainItem.setOwnerURL(guild.getGuildHead());

        inventory.setItem(this.config.getOrCreate("guild.panel.inventory.head_slot", 4), mainItem.toItemStack());

        lore = this.config.getOrCreate("guild.inventory.visual_lore", Arrays.asList(
                "",
                "&8>> &7Troszke wizualizacji nie zaszkodzi? ",
                "",
                "&8>> &7Tutaj mozesz zmienic wyglad serca",
                "&8>> &7swojej gildii jesli to zrobisz wyglad serca gildyjnego",
                "&8>> &7zostanie zmieniony na ten ktory wybrales",
                "",
                "&dLewy &f- &5Aby przejsc dalej",
                "&dSHIFT + Lewy &f- &5Aby przywrocic domyslne"));

        ItemBuilder visualItem = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(this.config.getOrCreate("guild.panel.inventory.visual_title", "&fWizualizacja serca"))
                .setLore(lore)
                .setOwnerURL(guild.getGuildHead());

        inventory.setItem(this.config.getOrCreate("guild.panel.inventory.visual_slot", 10), visualItem.toItemStack());


        lore = this.config.getOrCreate("guild.inventory.visual_particle_lore", Arrays.asList(
                "",
                "&8>> &7Troszke wizualizacji nie zaszkodzi? ",
                "",
                "&8>> &7Tutaj mozesz zmienic partiklesy",
                "&8>> &7swojej gildii jesli to zrobisz partiklesy gildyjnego",
                "&8>> &7zostana zmienione na te ktore wybrales",
                "",
                "&dLewy &f- &5Aby przejsc dalej",
                "&dSHIFT + Lewy &f- &5Aby przywrocic domyslne"));

        ItemBuilder visualParticleItem = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(this.config.getOrCreate("guild.panel.inventory.visual_particle_title", "&fPartiklesy"))
                .setLore(lore)
                .setOwnerURL(this.config.getOrCreate("guild.panel.inventory.visual_particle_material", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFlNjZmMzZhODEzYzc2Y2NlNTc2ZGE5YjhlMzUxZGQ1YjVmYWI4ZWEyYzliYzZhMzI4MDM4ZjVhMDQwOWMyNSJ9fX0="));

        inventory.setItem(this.config.getOrCreate("guild.panel.inventory.visual_particle_slot", 11), visualParticleItem.toItemStack());


        lore = this.config.getOrCreate("guild.panel.inventory.role_lore", Arrays.asList(
                "",
                "&8>> &fDzieki tej opcji mozesz konfigurowac",
                "&8>> &fuprawnienia gildyjne",
                "",
                "&dLewy &8- &5Aby przejsc dalej"));

        ItemBuilder roleItem = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(this.config.getOrCreate("guild.panel.inventory.role_title", "&dKonfiguruj schematy uprawnien"))
                .setLore(lore)
                .setOwnerURL(this.config.getOrCreate("guild.panel.inventory.role_material", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMzkyMGJkYWIwZmFhMTI4OTk5MjE5NWRjNjRhMjE0OTA5OGIxNTNjMzVjNmJhZTc2NWJhM2IwODQyZDFhOSJ9fX0="));

        inventory.setItem(this.config.getOrCreate("guild.panel.inventory.role_slot", 12), roleItem.toItemStack());


        lore = this.config.getOrCreate("guild.panel.inventory.camera_lore", Arrays.asList(
                "",
                "&8>> &fDziekii tej opcji bedziesz mogl obserwowac",
                "&8>> &fswoj teren gildii",
                "",
                "&dLewy &8- &5Aby przejsc dalej"));

        ItemBuilder cameraItem = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(this.config.getOrCreate("guild.panel.inventory.camera_title", "&dKamery gildyjne"))
                .setLore(lore)
                .setOwnerURL(this.config.getOrCreate("guild.panel.inventory.camera_material", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIyOGY0ZWVmZjg5MWI3OGQ1MWY3NWQ4NzIyYzYyODQ4NGJhNDlkZjljOWYyMzcxODk4YzI2OTY3Mzg2In19fQ=="));


        inventory.setItem(this.config.getOrCreate("guild.panel.inventory.camera_slot", 13), cameraItem.toItemStack());

        player.openInventory(inventory);
    }
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(this.chatHelper.colored(this.config.getOrCreate("guild.panel.inventory.main_title", "&dPanel gildyjny")))){
            event.setCancelled(true);

            if(event.getInventory().getType() == InventoryType.PLAYER){
                event.setCancelled(true);
                return;
            }
            ItemStack itemStack = event.getCurrentItem();
            if(itemStack == null)return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta == null)return;
            Player player = (Player) event.getWhoClicked();
            var guild = this.getGuildService().getGuild(player.getName());
            if(guild == null)return;
            if(itemMeta.getDisplayName().equalsIgnoreCase(this.chatHelper.colored(this.config.getOrCreate("guild.panel.inventory.visual_title", "&fWizualizacja serca")))){
                this.visualGuildIInventory.show(player);
                return;
            }
        }
    }
}

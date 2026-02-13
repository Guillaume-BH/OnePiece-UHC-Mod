package fr.lastril.onepiecemod.launcher;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.player.ApolloPlayerManager;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class LunarAPI {

    private final ApolloPlayerManager playerManager;

    public LunarAPI(){
        this.playerManager = Apollo.getPlayerManager();
    }

    public void sendModerationMods(UUID uuid){
        ApolloPlayer apolloPlayer = getApolloPlayer(uuid);
        if(apolloPlayer == null) return;
        StaffModModule staffModModule = Apollo.getModuleManager().getModule(StaffModModule.class);
        staffModModule.enableStaffMods(apolloPlayer, Collections.singletonList(StaffMod.XRAY));
    }

    public void removeModerationMods(UUID uuid){
        ApolloPlayer apolloPlayer = getApolloPlayer(uuid);
        if(apolloPlayer == null) return;
        StaffModModule staffModModule = Apollo.getModuleManager().getModule(StaffModModule.class);
        staffModModule.disableStaffMods(apolloPlayer, Collections.singletonList(StaffMod.XRAY));
    }

    public boolean isPlayerRunningLunarClient(UUID uuid) {
        return this.playerManager.hasSupport(uuid);
    }

    private ApolloPlayer getApolloPlayer(UUID uuid) {
        Optional<ApolloPlayer> apolloPlayer = Apollo.getPlayerManager().getPlayer(uuid);
        return apolloPlayer.orElse(null);
    }

}

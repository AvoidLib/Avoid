package pl.olafcio.avoid.net.server;

import net.minecraft.server.dedicated.DedicatedServerProperties;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.net.player.gamemode.GameMode;
import pl.olafcio.avoid.net.player.gamemode.GameModeNative;

// TODO Implement setters for final properties
@ApiStatus.Experimental
public final class Properties {
    Object object;

    Properties(Object object) {
        this.object = object;
    }

    public boolean getOnlineMode() {
        return ((DedicatedServerProperties) object).onlineMode;
    }

    public boolean getPreventProxyConnections() {
        return ((DedicatedServerProperties) object).preventProxyConnections;
    }

    public String getServerIP() {
        return ((DedicatedServerProperties) object).serverIp;
    }

    public boolean getAllowFlight() {
        return ((DedicatedServerProperties) object).allowFlight;
    }

    public void setAllowFlight(boolean value) {
        throw new RuntimeException("[Properties#setAllowFlight] Not possible on MC 1.21.10");
    }

    public String getMOTD() {
        return ((DedicatedServerProperties) object).motd;
    }

    public void setMOTD(String value) {
        throw new RuntimeException("[Properties#setMOTD] Not possible on MC 1.21.10");
    }

    public boolean getCodeOfConduct() {
        return false;
    }

    public String getBugReportLink() {
        return ((DedicatedServerProperties) object).bugReportLink;
    }

    public boolean getForceGameMode() {
        return ((DedicatedServerProperties) object).forceGameMode;
    }

    public void setForceGameMode(boolean value) {
        throw new RuntimeException("[Properties#setForceGameMode] Not possible on MC 1.21.10");
    }

    public boolean getEnforceWhitelist() {
        return ((DedicatedServerProperties) object).enforceWhitelist;
    }

    public void setEnforceWhitelist(boolean value) {
        throw new RuntimeException("[Properties#setEnforceWhitelist] Not possible on MC 1.21.10");
    }

    public GameMode getGameMode() {
        return GameModeNative.convertFrom(((DedicatedServerProperties) object).gamemode);
    }

    public void setGameMode(GameMode value) {
        throw new RuntimeException("[Properties#setGameMode] Not possible on MC 1.21.10");
    }

    public String getLevelName() {
        return ((DedicatedServerProperties) object).levelName;
    }

    public int getServerPort() {
        return ((DedicatedServerProperties) object).serverPort;
    }

    public boolean getManagementServerEnabled() {
        return false;
    }

    public String getManagementServerHost() {
        return "localhost";
    }

    public int getManagementServerPort() {
        return 0;
    }

    public String getManagementServerSecret() {
        return "";
    }

    public boolean getManagementServerTLSEnabled() {
        return false;
    }

    public String getManagementServerTLSKeystore() {
        return null;
    }

    public String getManagementServerTLSKeystorePassword() {
        return null;
    }

    public String getManagementServerAllowedOrigins() {
        return null;
    }

    @Nullable
    public Boolean getAnnouncePlayerAchievements() {
        return ((DedicatedServerProperties) object).announcePlayerAchievements;
    }

    public boolean getEnableQuery() {
        return ((DedicatedServerProperties) object).enableQuery;
    }

    public int getQueryPort() {
        return ((DedicatedServerProperties) object).queryPort;
    }

    public boolean getEnableRCON() {
        return ((DedicatedServerProperties) object).enableRcon;
    }

    public int getRCONPort() {
        return ((DedicatedServerProperties) object).rconPort;
    }

    public String getRCONPassword() {
        return ((DedicatedServerProperties) object).rconPassword;
    }

    public boolean getHardcore() {
        return ((DedicatedServerProperties) object).hardcore;
    }

    public boolean getUseNativeTransport() {
        return ((DedicatedServerProperties) object).useNativeTransport;
    }

    public int getSpawnProtection() {
        return ((DedicatedServerProperties) object).spawnProtection;
    }

    public void setSpawnProtection(int value) {
        throw new RuntimeException("[Properties#setSpawnProtection] Not possible on MC 1.21.10");
    }

    // TODO opPermissions
    // TODO functionPermissions

    public long getMaxTickTime() {
        return ((DedicatedServerProperties) object).maxTickTime;
    }

    public int getMaxChainedNeighborUpdates() {
        return ((DedicatedServerProperties) object).maxChainedNeighborUpdates;
    }

    public int getRateLimitPacketsPerSecond() {
        return ((DedicatedServerProperties) object).rateLimitPacketsPerSecond;
    }

    public int getViewDistance() {
        return ((DedicatedServerProperties) object).viewDistance;
    }

    public void setViewDistance(int value) {
        throw new RuntimeException("[Properties#setViewDistance] Not possible on MC 1.21.10");
    }

    public int getSimulationDistance() {
        return ((DedicatedServerProperties) object).simulationDistance;
    }

    public void setSimulationDistance(int value) {
        throw new RuntimeException("[Properties#setSimulationDistance] Not possible on MC 1.21.10");
    }

    public int getMaxPlayers() {
        return ((DedicatedServerProperties) object).maxPlayers;
    }

    public void setMaxPlayers(int value) {
        throw new RuntimeException("[Properties#setMaxPlayers] Not possible on MC 1.21.10");
    }

    public int getNetworkCompressionThreshold() {
        return ((DedicatedServerProperties) object).networkCompressionThreshold;
    }

    public boolean getBroadcastRCONtoOPs() {
        return ((DedicatedServerProperties) object).broadcastRconToOps;
    }

    public boolean getBroadcastConsoleToOPs() {
        return ((DedicatedServerProperties) object).broadcastConsoleToOps;
    }

    public int getMaxWorldSize() {
        return ((DedicatedServerProperties) object).maxWorldSize;
    }

    public boolean getSyncChunkWrites() {
        return ((DedicatedServerProperties) object).syncChunkWrites;
    }

    public String getRegionFileCompression() {
        return ((DedicatedServerProperties) object).regionFileComression;
    }

    public boolean getEnableJMXMonitoring() {
        return ((DedicatedServerProperties) object).enableJmxMonitoring;
    }

    public boolean getEnableStatus() {
        return ((DedicatedServerProperties) object).enableStatus;
    }

    public void setEnableStatus(boolean value) {
        throw new RuntimeException("[Properties#setEnableStatus] Not possible on MC 1.21.10");
    }

    public boolean getHideOnlinePlayers() {
        return ((DedicatedServerProperties) object).hideOnlinePlayers;
    }

    public void setHideOnlinePlayers(boolean value) {
        throw new RuntimeException("[Properties#setHideOnlinePlayers] Not possible on MC 1.21.10");
    }

    public int getEntityBroadcastRangePercentage() {
        return ((DedicatedServerProperties) object).entityBroadcastRangePercentage;
    }

    public void setEntityBroadcastRangePercentage(int value) {
        throw new RuntimeException("[Properties#setEntityBroadcastRangePercentage] Not possible on MC 1.21.10");
    }

    public String getTextFilteringConfig() {
        return ((DedicatedServerProperties) object).textFilteringConfig;
    }

    public int getTextFilteringVersion() {
        return ((DedicatedServerProperties) object).textFilteringVersion;
    }

    // TODO serverResourcePackInfo
    // TODO initialDataPackConfiguration

    public int getPlayerIdleTimeout() {
        return ((DedicatedServerProperties) object).playerIdleTimeout.get();
    }

    public void setPlayerIdleTimeout(int value) {
        ((DedicatedServerProperties) object).playerIdleTimeout.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public int getStatusHeartbeatInterval() {
        return 5;
    }

    public void setStatusHeartbeatInterval(int value) {
        throw new RuntimeException("[Properties#setStatusHeartbeatInterval] Not possible on MC 1.21.10");
    }

    public boolean getWhitelist() {
        return ((DedicatedServerProperties) object).whiteList.get();
    }

    public void setWhitelist(boolean value) {
        ((DedicatedServerProperties) object).whiteList.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public boolean getEnforceSecureProfile() {
        return ((DedicatedServerProperties) object).enforceSecureProfile;
    }

    public boolean getLogIPs() {
        return ((DedicatedServerProperties) object).logIPs;
    }

    public int getPauseWhenEmptySeconds() {
        return ((DedicatedServerProperties) object).pauseWhenEmptySeconds;
    }

    public void setPauseWhenEmptySeconds(int value) {
        throw new RuntimeException("[Properties#setPauseWhenEmptySeconds] Not possible on MC 1.21.10");
    }

    // TODO worldDimensionData
    // TODO worldOptions

    public boolean getAcceptTransfers() {
        return ((DedicatedServerProperties) object).acceptsTransfers;
    }

    public void setAcceptTransfers(boolean value) {
        ((DedicatedServerProperties) object).acceptsTransfers = value;
    }
}

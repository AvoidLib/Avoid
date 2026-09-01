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
        return ((DedicatedServerProperties) object).allowFlight.get();
    }

    public void setAllowFlight(boolean value) {
        ((DedicatedServerProperties) object).allowFlight.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public String getMOTD() {
        return ((DedicatedServerProperties) object).motd.get();
    }

    public void setMOTD(String value) {
        ((DedicatedServerProperties) object).motd.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public boolean getCodeOfConduct() {
        return ((DedicatedServerProperties) object).codeOfConduct;
    }

    public String getBugReportLink() {
        return ((DedicatedServerProperties) object).bugReportLink;
    }

    public boolean getForceGameMode() {
        return ((DedicatedServerProperties) object).forceGameMode.get();
    }

    public void setForceGameMode(boolean value) {
        ((DedicatedServerProperties) object).forceGameMode.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public boolean getEnforceWhitelist() {
        return ((DedicatedServerProperties) object).enforceWhitelist.get();
    }

    public void setEnforceWhitelist(boolean value) {
        ((DedicatedServerProperties) object).enforceWhitelist.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public GameMode getGameMode() {
        return GameModeNative.convertFrom(((DedicatedServerProperties) object).gameMode.get());
    }

    public void setGameMode(GameMode value) {
        ((DedicatedServerProperties) object).gameMode.update(AvoidInternal.getServer().registryAccess(), GameModeNative.convert(value));
    }

    public String getLevelName() {
        return ((DedicatedServerProperties) object).levelName;
    }

    public int getServerPort() {
        return ((DedicatedServerProperties) object).serverPort;
    }

    public boolean getManagementServerEnabled() {
        return ((DedicatedServerProperties) object).managementServerEnabled;
    }

    public String getManagementServerHost() {
        return ((DedicatedServerProperties) object).managementServerHost;
    }

    public int getManagementServerPort() {
        return ((DedicatedServerProperties) object).managementServerPort;
    }

    public String getManagementServerSecret() {
        return ((DedicatedServerProperties) object).managementServerSecret;
    }

    public boolean getManagementServerTLSEnabled() {
        return ((DedicatedServerProperties) object).managementServerTlsEnabled;
    }

    public String getManagementServerTLSKeystore() {
        return ((DedicatedServerProperties) object).managementServerTlsKeystore;
    }

    public String getManagementServerTLSKeystorePassword() {
        return ((DedicatedServerProperties) object).managementServerTlsKeystorePassword;
    }

    public String getManagementServerAllowedOrigins() {
        return ((DedicatedServerProperties) object).managementServerAllowedOrigins;
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
        return ((DedicatedServerProperties) object).spawnProtection.get();
    }

    public void setSpawnProtection(int value) {
        ((DedicatedServerProperties) object).spawnProtection.update(AvoidInternal.getServer().registryAccess(), value);
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
        return ((DedicatedServerProperties) object).viewDistance.get();
    }

    public void setViewDistance(int value) {
        ((DedicatedServerProperties) object).viewDistance.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public int getSimulationDistance() {
        return ((DedicatedServerProperties) object).simulationDistance.get();
    }

    public void setSimulationDistance(int value) {
        ((DedicatedServerProperties) object).simulationDistance.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public int getMaxPlayers() {
        return ((DedicatedServerProperties) object).maxPlayers.get();
    }

    public void setMaxPlayers(int value) {
        ((DedicatedServerProperties) object).maxPlayers.update(AvoidInternal.getServer().registryAccess(), value);
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
        return ((DedicatedServerProperties) object).enableStatus.get();
    }

    public void setEnableStatus(boolean value) {
        ((DedicatedServerProperties) object).enableStatus.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public boolean getHideOnlinePlayers() {
        return ((DedicatedServerProperties) object).hideOnlinePlayers.get();
    }

    public void setHideOnlinePlayers(boolean value) {
        ((DedicatedServerProperties) object).hideOnlinePlayers.update(AvoidInternal.getServer().registryAccess(), value);
    }

    public int getEntityBroadcastRangePercentage() {
        return ((DedicatedServerProperties) object).entityBroadcastRangePercentage.get();
    }

    public void setEntityBroadcastRangePercentage(int value) {
        ((DedicatedServerProperties) object).entityBroadcastRangePercentage.update(AvoidInternal.getServer().registryAccess(), value);
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
        return ((DedicatedServerProperties) object).statusHeartbeatInterval.get();
    }

    public void setStatusHeartbeatInterval(int value) {
        ((DedicatedServerProperties) object).statusHeartbeatInterval.update(AvoidInternal.getServer().registryAccess(), value);
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
        return ((DedicatedServerProperties) object).pauseWhenEmptySeconds.get();
    }

    public void setPauseWhenEmptySeconds(int value) {
        ((DedicatedServerProperties) object).pauseWhenEmptySeconds.update(AvoidInternal.getServer().registryAccess(), value);
    }

    // TODO worldDimensionData
    // TODO worldOptions

    public boolean getAcceptTransfers() {
        return ((DedicatedServerProperties) object).acceptsTransfers.get();
    }

    public void setAcceptTransfers(boolean value) {
        ((DedicatedServerProperties) object).acceptsTransfers.update(AvoidInternal.getServer().registryAccess(), value);
    }
}

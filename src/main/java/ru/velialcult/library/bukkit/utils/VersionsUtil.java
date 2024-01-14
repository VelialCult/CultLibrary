package ru.velialcult.library.bukkit.utils;

import org.bukkit.Bukkit;

public class VersionsUtil {

    public static final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];


    public static ServerVersion getServerVersion() {

        String version = Bukkit.getBukkitVersion().trim();

        for (ServerVersion value : ServerVersion.values()) {

            if (!version.startsWith(value.getVersionString())) continue;

            return value;

        }

        return ServerVersion.NOT_FOUND;
    }

    public enum ServerVersion {
        v1_7(0, "1.7"),
        v1_8(1, "1.8"),
        v1_9(2, "1.9"),
        v1_10(3, "1.10"),
        v1_11(4, "1.11"),
        v1_12(5, "1.12"),
        v1_13(6, "1.13"),
        v1_14(7, "1.14"),
        v1_15(8, "1.15"),
        v1_16(9, "1.16"),
        v1_17(10, "1.17"),
        v1_18(11, "1.18"),
        v1_19(12, "1.19"),
        v1_20(13, "1.20"),
        NOT_FOUND(14, "-1");

        private final int weight;
        private final String versionString;

        private ServerVersion(int weight, String versionString) {
            this.weight = weight;
            this.versionString = versionString;
        }

        public int getWeight() {
            return this.weight;
        }

        public String getVersionString() {
            return this.versionString;
        }

        public boolean isOlderEqualThan(ServerVersion version) {
            return this.getWeight() <= version.getWeight();
        }

        public boolean isOlderThan(ServerVersion version) {
            return this.getWeight() < version.getWeight();
        }

        public boolean isNewerEqualThan(ServerVersion version) {
            return this.getWeight() >= version.getWeight();
        }

        public boolean isNewerThan(ServerVersion version) {
            return this.getWeight() > version.getWeight();
        }

        public boolean isEqualThan(ServerVersion version) {
            return this.getWeight() == version.getWeight();
        }

        public boolean isV1_8() {
            return this == v1_8;
        }

        public boolean isLegacy() {
            return this.getWeight() <= v1_12.getWeight();
        }

        public boolean isNewerEqualThanV1_13() {
            return this.getWeight() >= v1_13.getWeight();
        }

        public boolean isNewerEqualThanV1_14() {
            return this.getWeight() >= v1_14.getWeight();
        }

        public boolean isNewerThanV1_13() {
            return this.getWeight() > v1_13.getWeight();
        }

        public boolean isNewerThanV1_12() {
            return this.getWeight() > v1_12.getWeight();
        }

        public boolean isNewerEqualThanV1_11() {
            return this.getWeight() >= v1_11.getWeight();
        }

        public boolean isNewerEqualThanV1_16() {
            return this.getWeight() >= v1_16.getWeight();
        }

        public boolean isNewerEqualThanV1_17() {
            return this.getWeight() >= v1_17.getWeight();
        }

        public boolean isNewerThanV1_16() {
            return this.getWeight() > v1_16.getWeight();
        }

        public boolean isNewerThanV1_17() {
            return this.getWeight() > v1_17.getWeight();
        }

        public boolean isOlderThanEqualV1_19() { return this.getWeight() <= v1_19.getWeight(); }

        public boolean isNewerEqualThanV1_19() { return this.getWeight() >= v1_19.getWeight(); }

        public boolean isNewerThanV1_20() {
            return this.getWeight() > v1_20.getWeight();
        }

        public boolean isNewerEqualThanV1_20() {
            return this.getWeight() >= v1_20.getWeight();
        }
    }
}

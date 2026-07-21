package pl.olafcio.avoid.net.block.values;

import java.util.HashMap;

public record MapColor(int id, int col) {
    public static final MapColor NONE = new MapColor(0, 0);
    public static final MapColor GRASS = new MapColor(1, 0x7fb238);
    public static final MapColor SAND = new MapColor(2, 0xf7e9a3);
    public static final MapColor WOOL = new MapColor(3, 0xc7c7c7);
    public static final MapColor FIRE = new MapColor(4, 0xff0000);
    public static final MapColor ICE = new MapColor(5, 0xa0a0ff);
    public static final MapColor METAL = new MapColor(6, 0xa7a7a7);
    public static final MapColor PLANT = new MapColor(7, 0x7c00);
    public static final MapColor SNOW = new MapColor(8, 0xffffff);
    public static final MapColor CLAY = new MapColor(9, 0xa4a8b8);
    public static final MapColor DIRT = new MapColor(10, 0x976d4d);
    public static final MapColor STONE = new MapColor(11, 0x707070);
    public static final MapColor WATER = new MapColor(12, 0x4040ff);
    public static final MapColor WOOD = new MapColor(13, 0x8f7748);
    public static final MapColor QUARTZ = new MapColor(14, 0xfffcf5);
    public static final MapColor COLOR_ORANGE = new MapColor(15, 0xd87f33);
    public static final MapColor COLOR_MAGENTA = new MapColor(16, 0xb24cd8);
    public static final MapColor COLOR_LIGHT_BLUE = new MapColor(17, 0x6699d8);
    public static final MapColor COLOR_YELLOW = new MapColor(18, 0xe5e533);
    public static final MapColor COLOR_LIGHT_GREEN = new MapColor(19, 0x7fcc19);
    public static final MapColor COLOR_PINK = new MapColor(20, 0xf27fa5);
    public static final MapColor COLOR_GRAY = new MapColor(21, 0x4c4c4c);
    public static final MapColor COLOR_LIGHT_GRAY = new MapColor(22, 0x999999);
    public static final MapColor COLOR_CYAN = new MapColor(23, 0x4c7f99);
    public static final MapColor COLOR_PURPLE = new MapColor(24, 0x7f3fb2);
    public static final MapColor COLOR_BLUE = new MapColor(25, 0x334cb2);
    public static final MapColor COLOR_BROWN = new MapColor(26, 0x664c33);
    public static final MapColor COLOR_GREEN = new MapColor(27, 0x667f33);
    public static final MapColor COLOR_RED = new MapColor(28, 0x993333);
    public static final MapColor COLOR_BLACK = new MapColor(29, 0x191919);
    public static final MapColor GOLD = new MapColor(30, 0xfaee4d);
    public static final MapColor DIAMOND = new MapColor(31, 0x5cdbd5);
    public static final MapColor LAPIS = new MapColor(32, 0x4a80ff);
    public static final MapColor EMERALD = new MapColor(33, 0xd93a);
    public static final MapColor PODZOL = new MapColor(34, 0x815631);
    public static final MapColor NETHER = new MapColor(35, 0x700200);
    public static final MapColor TERRACOTTA_WHITE = new MapColor(36, 0xd1b1a1);
    public static final MapColor TERRACOTTA_ORANGE = new MapColor(37, 0x9f5224);
    public static final MapColor TERRACOTTA_MAGENTA = new MapColor(38, 0x95576c);
    public static final MapColor TERRACOTTA_LIGHT_BLUE = new MapColor(39, 0x706c8a);
    public static final MapColor TERRACOTTA_YELLOW = new MapColor(40, 0xba8524);
    public static final MapColor TERRACOTTA_LIGHT_GREEN = new MapColor(41, 0x677535);
    public static final MapColor TERRACOTTA_PINK = new MapColor(42, 0xa04d4e);
    public static final MapColor TERRACOTTA_GRAY = new MapColor(43, 0x392923);
    public static final MapColor TERRACOTTA_LIGHT_GRAY = new MapColor(44, 0x876b62);
    public static final MapColor TERRACOTTA_CYAN = new MapColor(45, 0x575c5c);
    public static final MapColor TERRACOTTA_PURPLE = new MapColor(46, 0x7a4958);
    public static final MapColor TERRACOTTA_BLUE = new MapColor(47, 0x4c3e5c);
    public static final MapColor TERRACOTTA_BROWN = new MapColor(48, 0x4c3223);
    public static final MapColor TERRACOTTA_GREEN = new MapColor(49, 0x4c522a);
    public static final MapColor TERRACOTTA_RED = new MapColor(50, 0x8e3c2e);
    public static final MapColor TERRACOTTA_BLACK = new MapColor(51, 0x251610);
    public static final MapColor CRIMSON_NYLIUM = new MapColor(52, 0xbd3031);
    public static final MapColor CRIMSON_STEM = new MapColor(53, 0x943f61);
    public static final MapColor CRIMSON_HYPHAE = new MapColor(54, 0x5c191d);
    public static final MapColor WARPED_NYLIUM = new MapColor(55, 0x167e86);
    public static final MapColor WARPED_STEM = new MapColor(56, 0x3a8e8c);
    public static final MapColor WARPED_HYPHAE = new MapColor(57, 0x562c3e);
    public static final MapColor WARPED_WART_BLOCK = new MapColor(58, 0x14b485);
    public static final MapColor DEEPSLATE = new MapColor(59, 0x646464);
    public static final MapColor RAW_IRON = new MapColor(60, 0xd8af93);
    public static final MapColor GLOW_LICHEN = new MapColor(61, 0x7fa796);

    public MapColor(int id, int col) {
        this.id = id;
        this.col = col;
    }
}

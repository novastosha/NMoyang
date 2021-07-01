package dev.nova.nmoyang.api.stats;

public enum SaleStatisticsType {

    MINECRAFT("item_sold_minecraft"),
    MINECRAFT_GIFTCODES("prepaid_card_redeemed_minecraft");

    private final String typeName;

    SaleStatisticsType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}

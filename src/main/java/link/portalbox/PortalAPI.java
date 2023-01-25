package link.portalbox;

import link.portalbox.manager.MarketplaceManager;

public class PortalAPI {

    private static MarketplaceManager marketplaceManager;

    public static void main(String[] args) {
        marketplaceManager = new MarketplaceManager();
    }

    public MarketplaceManager getMarketplaceManager() { return marketplaceManager; }

}
package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic;

import java.io.IOException;
import java.util.List;

public interface IDeliveryServiceProcessing {
    /**
     * @pre file exists
     * @post List contains all created objects
     * @return
     * @throws IOException
     */
    List<BaseProduct> importItems() throws IOException;

    /**
     * @pre !newMenu.getContainedProducts().isEmpty()
     * @post menuItems.contains(newMenu)
     * @param newMenu
     */
    void addMenu(CompositeProduct newMenu);

    boolean isWellFormed(int calories);

    /**
     * @pre !(newOrder != null) && !orderedItems.isEmpty()
     * @post orders.contains()
     * @param newOrder
     */
    void addOrder(Order newOrder, List<MenuItem> orderedItems);

    void generateReport();
}

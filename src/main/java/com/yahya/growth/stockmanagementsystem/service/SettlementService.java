package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Settlement;

public interface SettlementService extends BasicServiceSkeleton<Settlement> {
    Settlement saveNewSettlement(Settlement settlement);
}

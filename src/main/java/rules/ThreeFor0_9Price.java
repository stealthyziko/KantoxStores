package rules;

import products.Item;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ThreeFor0_9Price extends DiscountWithQuantityRule{

    public ThreeFor0_9Price(){
        this.ruleName = "ThreeFor0_9Price";
        this.discountMultiplier = 0.9f;
        this.minimumCount = 3;
    }

    @Override
    public Map<Item, Integer> applyRule(Map<Item, Integer> itemMap, Set<String> articleCodes) {
        for (Entry<Item, Integer> entry: itemMap.entrySet()
             ) {
            if(articleCodes.contains(entry.getKey().getArticle().getCode()) && entry.getValue() > 2){
                entry.getKey().setPriceAfterRules(entry.getKey().getPriceAfterRules() * discountMultiplier);
            }
        }
        return itemMap;
    }
}

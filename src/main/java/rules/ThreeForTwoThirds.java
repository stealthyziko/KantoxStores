package rules;

import products.Item;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ThreeForTwoThirds extends DiscountWithQuantityRule{

    public ThreeForTwoThirds(){
        this.ruleName = "ThreeForTwoThirds";
        this.discountMultiplier = 2.0f/3;
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

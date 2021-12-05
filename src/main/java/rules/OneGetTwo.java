package rules;

import products.Item;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// DiscountWithQuantityRule doesn't apply because with 3 or more they are taken in pairs
public class OneGetTwo extends Rule{

    public OneGetTwo(){
        this.ruleName = "OneGetTwo";
    }

    @Override
    public Map<Item, Integer> applyRule(Map<Item, Integer> itemMap, Set<String> articleCodes) {
//        Map<String, Integer> codeMap = toCodeMap(itemMap);
//        for (String code: articleCodes
//             ) {
//            Integer count = codeMap.get(code);
//            if(count == null || count < 1){
//                continue;
//            }
//            for (Item item: itemMap.keySet()
//                 ) {
//                item.setPriceAfterRules(getNewPerItemPrice(item.getPriceAfterRules(), count));
//            }
//        }
//        return itemMap;
        for (Entry<Item, Integer> entry: itemMap.entrySet()
             ) {
            if(articleCodes.contains(entry.getKey().getArticle().getCode())){
                entry.getKey().setPriceAfterRules(getNewPerItemPrice(entry.getKey().getPriceAfterRules(), entry.getValue()));
            }
        }
        return itemMap;
    }

//    private Map<String, Integer> toCodeMap(Map<Item, Integer> itemMap){
//        Map<String, Integer> codeMap = new HashMap<>();
//        for (Entry<Item, Integer> entry: itemMap.entrySet()
//             ) {
//            codeMap.put(entry.getKey().getArticle().getCode(), entry.getValue());
//        }
//        return codeMap;
//    }

    private float getNewPerItemPrice(float originalPrice, int count){
        if(count % 2 == 0){
            return originalPrice / 2;
        } else {
            return ((originalPrice/2) * (count - 1) + originalPrice) / count;
        }
    }
}

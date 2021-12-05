package rules;

import lombok.Getter;
import products.Item;

import java.util.Map;
import java.util.Set;

@Getter
public abstract class Rule {
    String ruleName;

    public abstract Map<Item, Integer> applyRule(Map<Item, Integer> itemMap, Set<String> articleCodes);
}

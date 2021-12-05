package billing;

import data.DataHandler;
import data.Article;
import products.Item;
import rules.Rule;

import java.util.*;
import java.util.Map.Entry;

public class Bill {
//    List<Article> articleList; // to be used while displaying the list
    Map<Article, Integer> originalArticleMap; // to be used for calculations
    Map<Item, Integer> itemMap;
    DataHandler dataHandler;
    float sum;

    public Bill(DataHandler dataHandler){
//        articleList = new ArrayList<Article>();
        originalArticleMap = new HashMap<Article,Integer>();
        this.dataHandler = dataHandler;
    }
    public void add(String code){
        Article article = dataHandler.getByCode(code);
//        articleList.add(article);
        if(originalArticleMap.containsKey(article)){
            Integer count = originalArticleMap.get(article);
            originalArticleMap.replace(article, count, count + 1);
        } else {
            originalArticleMap.put(article, 1);
        }
    }

    public boolean remove(String code){
        Article article = dataHandler.getByCode(code);
        Integer count = originalArticleMap.get(article);
        if(count != null && count > 0){
//            articleList.remove(article);
            originalArticleMap.replace(article, count, count-1);
            if(originalArticleMap.get(article) == 0){
                originalArticleMap.remove(article, 0);
            }
            return true;
        } else {
            return false;
        }
    }

    private void generateItemMap(){
        itemMap = new HashMap<Item, Integer>();
        for (Entry<Article, Integer> entry: originalArticleMap.entrySet()
             ) {
            Item item = new Item(entry.getKey(), "");
            itemMap.put(item, entry.getValue());
        }
    }

    public float sum(){
        generateItemMap();
        Map<Rule, Set<String>> rulesToApply = new HashMap<Rule, Set<String>>();
        Map<String, Rule> rulesToApplyNames = new HashMap<String, Rule>();
        for (Article article: originalArticleMap.keySet()
             ) {
            if(article.getRules() == null){
                continue;
            }
            for (data.Rule ruleData: article.getRules().getRule()
                 ) {
                String ruleName = ruleData.getName();
                if (rulesToApplyNames.containsKey(ruleName)){
                    rulesToApply.get(rulesToApplyNames.get(ruleName)).add(article.getCode());
                } else {
                    Rule rule = dataHandler.getRuleMap().get(ruleName);
                    rulesToApplyNames.put(ruleName, rule);
                    Set<String> codeSet = new HashSet<String>();
                    codeSet.add(article.getCode());
                    rulesToApply.put(rule, codeSet);
                }
            }
        }
        for (Entry<Rule, Set<String>> entry: rulesToApply.entrySet()
             ) {
            itemMap = entry.getKey().applyRule(itemMap, entry.getValue());
        }
        for (Entry<Item, Integer> entry: itemMap.entrySet()
             ) {
            sum += entry.getKey().getPriceAfterRules() * entry.getValue();
        }
        return sum;
    }
}

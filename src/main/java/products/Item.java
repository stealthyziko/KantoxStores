package products;

import data.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Item {
    Article article;
    String serial;
    float priceAfterRules;
    List<Class> rulesApplied;
    public Item(Article article, String serial){
        this.article = article;
        this.serial = serial;
        priceAfterRules = article.getPrice();
        rulesApplied = new ArrayList<Class>();
    }
}

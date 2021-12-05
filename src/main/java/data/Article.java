package data;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "article")
@Getter @Setter
public class Article {
    @Element(name = "code")
    private String code;
    @Element(name = "name")
    private String name;
    @Element(name = "price")
    private float price;
    @Element(name = "rules")
    private Rules rules;
}

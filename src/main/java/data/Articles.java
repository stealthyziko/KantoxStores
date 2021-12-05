package data;

import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "articles")
@Getter
public class Articles {

    @Attribute(name = "schemaLocation")
    @Namespace(reference = "https://www.w3schools.com", prefix = "xsi")
    private String mSchemaLocation;

    @ElementList(inline = true, required = false)
    private List<Article> articles;
}

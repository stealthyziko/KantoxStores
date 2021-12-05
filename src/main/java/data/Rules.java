package data;

import lombok.Getter;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rules")
@Getter
public class Rules {
    @ElementList (inline = true, required = false)
    List<Rule> rule;
}

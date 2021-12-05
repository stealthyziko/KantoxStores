package data;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "rule")
@Getter
@Setter
public class Rule {
    @Text
    String name;
}

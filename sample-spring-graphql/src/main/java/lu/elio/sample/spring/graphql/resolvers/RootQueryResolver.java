package lu.elio.sample.spring.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lu.elio.sample.spring.graphql.models.TestModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RootQueryResolver implements GraphQLQueryResolver {

    public List<TestModel> getTestModel(int count, int offset) {
        return Arrays.asList(new TestModel(){{
            this.setId(1);
            this.setTestField1("TEST_FIELD_1.1");
            this.setTestField2("TEST_FIELD_1.2");
        }}, new TestModel() {{
            this.setId(2);
            this.setTestField1("TEST_FIELD_2.1");
            this.setTestField2("TEST_FIELD_2.2");
        }});
    }

}

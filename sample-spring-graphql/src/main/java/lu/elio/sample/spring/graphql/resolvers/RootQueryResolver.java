package lu.elio.sample.spring.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lu.elio.sample.spring.graphql.generated.Query;
import lu.elio.sample.spring.graphql.generated.TestModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RootQueryResolver implements GraphQLQueryResolver, Query {

    private static final List<TestModel> TEST_MODELS = new ArrayList<TestModel>() {{
        this.add(new TestModel(){{
            this.setId(1);
            this.setTestField1("TEST_FIELD_1.1");
            this.setTestField2("TEST_FIELD_1.2");
        }});
        this.add(new TestModel(){{
            this.setId(2);
            this.setTestField1("TEST_FIELD_2.1");
            this.setTestField2("TEST_FIELD_2.2");
        }});
        this.add(new TestModel(){{
            this.setId(3);
            this.setTestField1("TEST_FIELD_3.1");
            this.setTestField2("TEST_FIELD_3.2");
        }});
    }};

    @Override
    public List<TestModel> getTestModels(Integer count, Integer offset) {
        int startIndex = Math.min(offset, TEST_MODELS.size());
        int endIndex = Math.min(offset + count, TEST_MODELS.size());
        return TEST_MODELS.subList(startIndex, endIndex);
    }

    @Override
    public TestModel getTestModel(Integer index) {
        int id = Math.min(index, TEST_MODELS.size());
        return TEST_MODELS.get(id);
    }
}
